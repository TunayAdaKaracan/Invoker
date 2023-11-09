package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelId;


public class InformationPacket implements Packet {

    private ChannelId channelId;

    @Override
    public int packetID() {
        return 0x08;
    }

    public void setClientID(ChannelId channelId) {
        this.channelId = channelId;
    }

    @Override
    public void write(ByteBuf byteBuf) {
        ByteBufUtil.writeString(byteBuf, channelId.asShortText());
    }
}
