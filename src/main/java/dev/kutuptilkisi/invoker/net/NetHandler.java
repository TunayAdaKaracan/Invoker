package dev.kutuptilkisi.invoker.net;

import com.google.gson.Gson;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientConnectPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientDisconnectPacket;
import dev.kutuptilkisi.invoker.util.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;

public class NetHandler {
    private final int PORT;

    private final ServerSocket server;
    private ConcurrentHashMap<Client, List<Packet>> packetQueue;
    private final Object packetQueueLock;
    private volatile CopyOnWriteArrayList<Client> clients;

    private final String authKey;

    private volatile boolean isRunning;

    private volatile Gson gson;


    public NetHandler(int port, String authKey) throws IOException {
        this.PORT = port;
        this.authKey = authKey;

        this.server = new ServerSocket(this.PORT);

        this.packetQueue = new ConcurrentHashMap<>();
        this.clients = new CopyOnWriteArrayList<>();
        this.isRunning = false;

        this.gson = new Gson();
        this.packetQueueLock = new Object();
    }

    public void start(){
        this.isRunning = true;
        ConnectionListener connectionListener = new ConnectionListener();
        connectionListener.setDaemon(true);
        Logger.info("Starting connection listener...");
        connectionListener.start();
        Logger.info("Connection Listener Started");

        PacketSender sender = new PacketSender();
        Logger.info("Starting packet sender...");
        sender.setDaemon(true);
        sender.start();
        Logger.info("Started packet sender...");
    }

    public ConcurrentHashMap<Client, List<Packet>> getPacketQueue() {
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

    public Gson getGson(){
        return gson;
    }

    public Object getPacketQueueLock() {
        return packetQueueLock;
    }

    public Client getClient(UUID clientID){
        for(Client client : clients){
            if(client.getClientUUID() == clientID){
                return client;
            }
        }
        return null;
    }

    public void addClient(Client client){
        this.clients.add(client);
        broadcastPacket(new ClientConnectPacket(client.getClientUUID()));
    }

    public void removeClient(Client client){
        if (clients.contains(client)) {
            this.clients.remove(client);
            broadcastPacket(new ClientDisconnectPacket(client.getClientUUID()));
        }
    }

    public void sendPacket(Client client, Packet packet){
        synchronized (packetQueueLock){
            packetQueue.computeIfAbsent(client, v -> new ArrayList<>());
            packetQueue.get(client).add(packet);
        }
    }

    public void broadcastPacket(Packet packet){
        for(Client client : clients){
            sendPacket(client, packet);
        }
    }

    public void close(){
        this.isRunning = false;
        for(Client client : clients){
            client.close();
        }
        try {
            this.server.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
