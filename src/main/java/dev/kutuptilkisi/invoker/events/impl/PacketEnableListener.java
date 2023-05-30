package dev.kutuptilkisi.invoker.events.impl;

import dev.kutuptilkisi.invoker.events.PacketHandler;
import dev.kutuptilkisi.invoker.events.PacketListener;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.EnableCustomPacketPacket;

public class PacketEnableListener implements PacketListener {
    @PacketHandler
    public void onPacket(Client client, EnableCustomPacketPacket packet){
        client.getClientIntents().enable(packet.getCustomPacketID());
    }
}
