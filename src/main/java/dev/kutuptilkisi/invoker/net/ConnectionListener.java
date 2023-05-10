package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.UnauthorizedClientConnectPacket;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.net.Socket;

public class ConnectionListener extends Thread {
    @Override
    public void run() {
        NetHandler handler = Invoker.getInstance().getNetHandler();
        while(handler.isRunning()){
            try {
                Socket socket = handler.getServer().accept();
                Client client = new Client(socket);

                // TODO: Broadcast?
                UnauthorizedClientConnectPacket event = new UnauthorizedClientConnectPacket(client.getClientID());
                Bukkit.getPluginManager().callEvent(event);

                PacketReader reader = new PacketReader(client);
                reader.setDaemon(true);
                reader.start();
            } catch (IOException e) {
                continue;
            }
        }
    }
}
