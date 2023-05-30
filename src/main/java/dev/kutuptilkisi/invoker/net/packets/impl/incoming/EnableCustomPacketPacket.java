package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;

public class EnableCustomPacketPacket implements Packet {
    private int packetID;

    @Override
    public int packetID() {
        return 0x0A;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        packetID = dis.readInt();
    }

    public int getCustomPacketID() {
        return packetID;
    }
}
