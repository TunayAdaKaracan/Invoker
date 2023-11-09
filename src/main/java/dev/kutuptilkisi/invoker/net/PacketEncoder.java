package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.IntentsUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        if(IntentsUtil.getIntents(channelHandlerContext.channel().id()).isEnabled(packet.packetID())){
            byteBuf.writeInt(packet.packetID());
            packet.write(byteBuf);
        }
    }
}
