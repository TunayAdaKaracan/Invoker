package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.EventRegistry;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.PacketFactory;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
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
                int packetID = dataInputStream.readInt();
                Packet packet = PacketFactory.fromId(packetID);

                if(packet != null) {
                    packet.read(dataInputStream);

                    if(!client.isAuthorized() && !(packet instanceof AuthorizationPacket)){
                        dataInputStream.reset();
                        continue;
                    }

                    EventRegistry.fireEvent(client, packet);
                    if(packet instanceof Event bukkitEvent) {
                        Bukkit.getPluginManager().callEvent(bukkitEvent);
                    }
                } else {
                    dataInputStream.reset();
                }
            } catch (IOException e) {
                this.client.close();
            }
        }
    }
}
