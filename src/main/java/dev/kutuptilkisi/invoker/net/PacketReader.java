package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.EventRegistry;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.PacketFactory;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.util.Logger;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.io.DataInputStream;
import java.io.IOException;

public class PacketReader extends Thread{
    private final Client client;
    private final DataInputStream dataInputStream;
    public PacketReader(Client client) throws IOException {
        this.client = client;
        this.dataInputStream = new DataInputStream(client.getSocket().getInputStream());
    }

    @Override
    public void run() {
        NetHandler handler = Invoker.getInstance().getNetHandler();
        while(handler.isRunning() && client.isConnected()){
            try {
                Logger.info("Waiting packet for Client: "+client.getClientID());
                int packetID = dataInputStream.readInt();
                Packet packet = PacketFactory.fromId(packetID);
                Logger.info("Found new packet with id of "+packetID+" for Client: "+client.getClientID());

                if(packet != null) {
                    packet.read(dataInputStream);

                    if(!client.isAuthorized() && !(packet instanceof AuthorizationPacket)){
                        Logger.warning("Client is not authorized and packet is not AuthorizationPacket. Client ID: "+client.getClientID());
                        dataInputStream.reset();
                        continue;
                    }

                    EventRegistry.fireEvent(client, packet);
                    if(packet instanceof Event bukkitEvent) {
                        Bukkit.getPluginManager().callEvent(bukkitEvent);
                    }
                } else {
                    Logger.warning("Packet is unknown. Resetting datastream. Client ID: "+client.getClientID());
                    dataInputStream.reset();
                }
            } catch (IOException e) {
                Logger.warning("Error on PacketReader: "+client.getClientID());
                this.client.close();
            }
        }
    }
}
