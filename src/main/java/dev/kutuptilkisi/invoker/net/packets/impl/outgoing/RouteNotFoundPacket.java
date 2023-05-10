package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class RouteNotFoundPacket implements Packet {
    private String routeName;

    @Override
    public int packetID() {
        return 0x07;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(routeName);
    }
}
