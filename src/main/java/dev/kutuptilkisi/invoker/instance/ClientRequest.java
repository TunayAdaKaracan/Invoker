package dev.kutuptilkisi.invoker.instance;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

public class ClientRequest {
    private final ChannelHandlerContext channelHandlerContext;
    private final ByteBuf byteBuf;

    public ClientRequest(ChannelHandlerContext channelHandlerContext, ByteBuf buf){
        this.channelHandlerContext = channelHandlerContext;
        this.byteBuf = buf;
    }

    public ByteBuf getByteBuf() {
        return byteBuf;
    }

    public ChannelHandlerContext getChannelHandlerContext() {
        return channelHandlerContext;
    }

    public void send(Packet packet){
        packet.send(this);
    }
}
