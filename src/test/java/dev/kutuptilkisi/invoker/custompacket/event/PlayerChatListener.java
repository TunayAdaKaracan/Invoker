package dev.kutuptilkisi.invoker.custompacket.event;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.custompacket.packet.outgoing.ChatPacket;
import dev.kutuptilkisi.invoker.instance.Client;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent playerChatEvent){
        ChatPacket chatPacket = new ChatPacket();
        chatPacket.setUserName(playerChatEvent.getPlayer().getDisplayName());
        chatPacket.setMessage(playerChatEvent.getMessage());

        // There are some ways to send a packet to client(s)

        // Packet#broadcast
        chatPacket.broadcast();

        // Nethandler#broadcast
        Invoker.getInstance().getNetHandler().broadcastPacket(chatPacket);

        // Send to a client
        Client client = Invoker.getInstance().getNetHandler().getClient(0); // Get first connected client
        if(client != null){
            // Client#send
            client.send(chatPacket);

            // Packet#send
            chatPacket.send(client);

            // NetHandler#sendPacket
            Invoker.getInstance().getNetHandler().sendPacket(client, chatPacket);
        }
    }
}
