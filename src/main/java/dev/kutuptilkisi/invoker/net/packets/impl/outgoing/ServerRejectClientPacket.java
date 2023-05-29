package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;

public class ServerRejectClientPacket implements Packet {
    @Override
    public int packetID() {
        return 0x09;
    }

    @Override
    public void send(Client client) {
        Packet.super.send(client);
    }
}
