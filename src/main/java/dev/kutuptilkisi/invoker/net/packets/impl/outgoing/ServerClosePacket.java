package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;


public class ServerClosePacket implements Packet {
    @Override
    public int packetID() {
        return 0x03;
    }
}
