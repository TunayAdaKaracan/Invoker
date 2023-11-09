package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.PacketFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class PacketDecoder extends ReplayingDecoder<Packet> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        Packet packet = PacketFactory.fromId(byteBuf.readInt());
        if(packet != null){
            packet.read(byteBuf);
            list.add(packet);
        } else {
            byteBuf.resetReaderIndex();
        }
    }
}
