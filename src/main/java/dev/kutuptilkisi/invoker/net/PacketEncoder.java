package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.IntentsUtil;
import dev.kutuptilkisi.invoker.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        Logger.info("Sending packet "+packet.packetID()+" to client: "+channelHandlerContext.channel().id());
        if(IntentsUtil.getIntents(channelHandlerContext.channel().id()).isEnabled(packet.packetID())){
            byteBuf.writeInt(packet.packetID());
            packet.write(byteBuf);
        } else {
            Logger.info("Skipping packet as it is not in client's intents");
        }
    }
}
