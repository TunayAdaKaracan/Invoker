package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.UnauthorizedClientConnectPacket;
import dev.kutuptilkisi.invoker.util.Logger;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.Socket;

public class ConnectionListener extends Thread {
    @Override
    public void run() {
        NetHandler handler = Invoker.getInstance().getNetHandler();
        while(handler.isRunning()){
            try {
                Logger.info("Waiting for a new client");
                Socket socket = handler.getServer().accept();
                Client client = new Client(socket);
                Logger.info("New client connected. Client ID: "+client.getClientID());

                // TODO: Broadcast?
                UnauthorizedClientConnectPacket event = new UnauthorizedClientConnectPacket(client.getClientID());
                Bukkit.getPluginManager().callEvent(event);

                Logger.info("Starting Packet Reader for Client: "+client.getClientID());
                PacketReader reader = new PacketReader(client);
                reader.setDaemon(true);
                reader.start();
                Logger.info("Started Packet Reader for Client: "+client.getClientID());
            } catch (IOException e) {
                continue;
            }
        }
    }
}
