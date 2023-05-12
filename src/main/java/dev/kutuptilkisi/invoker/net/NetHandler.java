package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientConnectPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientDisconnectPacket;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetHandler {
    private final int PORT;

    private final ServerSocket server;
    private volatile HashMap<Client, List<Packet>> packetQueue;
    private volatile List<Client> clients;

    private final String authKey;

    private volatile boolean isRunning;


    public NetHandler(int port, String authKey) throws IOException {
        this.PORT = port;
        this.authKey = authKey;

        this.server = new ServerSocket(this.PORT);

        this.packetQueue = new HashMap<>();
        this.clients = new ArrayList<>();
        this.isRunning = true;

        ConnectionListener connectionListener = new ConnectionListener();
        connectionListener.setDaemon(true);
        connectionListener.start();

        PacketSender sender = new PacketSender();
        sender.setDaemon(true);
        sender.start();
    }

    public HashMap<Client, List<Packet>> getPacketQueue() {
        return packetQueue;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public ServerSocket getServer() {
        return server;
    }

    public String getAuthKey() {
        return authKey;
    }

    public Client getClient(int clientID){
        for(Client client : clients){
            if(client.getClientID() == clientID){
                return client;
            }
        }
        return null;
    }

    public void addClient(Client client){
        ClientConnectPacket connectPacket = new ClientConnectPacket(client.getClientID());
        Bukkit.getPluginManager().callEvent(connectPacket);
        if(!connectPacket.isCancelled()){
            this.clients.add(client);
            broadcastPacket(new ClientConnectPacket(client.getClientID()));
        } else {
            client.close();
        }
    }

    public void removeClient(Client client){
        if (clients.contains(client)) {
            this.clients.remove(client);
            Bukkit.getPluginManager().callEvent(new ClientDisconnectPacket(client.getClientID()));
            broadcastPacket(new ClientDisconnectPacket(client.getClientID()));
        }
    }

    public void sendPacket(Client client, Packet packet){
        packetQueue.computeIfAbsent(client, v -> new ArrayList<>());
        packetQueue.get(client).add(packet);
    }

    public void broadcastPacket(Packet packet){
        for(Client client : clients){
            sendPacket(client, packet);
        }
    }

    public void close(){
        this.isRunning = true;
        for(Client client : clients){
            client.close();
        }
    }
}
