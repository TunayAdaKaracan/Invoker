package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelId;

public class ClientConnectPacket implements Packet {

    private ChannelId clientId;

    public ClientConnectPacket(ChannelId clientId){
        setClientUUID(clientId);
    }

    @Override
    public int packetID() {
        return 0x01;
    }

    public void setClientUUID(ChannelId clientId) {
        this.clientId = clientId;
    }

    @Override
    public void write(ByteBuf dos) {
        ByteBufUtil.writeString(dos, clientId.asShortText());
    }
}
