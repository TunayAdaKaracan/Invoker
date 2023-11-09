package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import io.netty.buffer.ByteBuf;

public class EnableCustomPacketPacket implements Packet {
    private int packetID;

    @Override
    public int packetID() {
        return 0x0A;
    }

    @Override
    public void read(ByteBuf byteBuf){
        packetID = byteBuf.readInt();
    }

    public int getCustomPacketID() {
        return packetID;
    }
}
