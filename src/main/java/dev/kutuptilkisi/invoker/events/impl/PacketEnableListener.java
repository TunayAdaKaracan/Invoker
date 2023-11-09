package dev.kutuptilkisi.invoker.events.impl;

import dev.kutuptilkisi.invoker.events.PacketHandler;
import dev.kutuptilkisi.invoker.events.PacketListener;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.EnableCustomPacketPacket;
import dev.kutuptilkisi.invoker.util.IntentsUtil;

public class PacketEnableListener implements PacketListener {
    @PacketHandler
    public void onPacket(ClientRequest client, EnableCustomPacketPacket packet){
        IntentsUtil.getIntents(client.getChannelHandlerContext().channel().id()).enable(packet.getCustomPacketID());
    }
}
