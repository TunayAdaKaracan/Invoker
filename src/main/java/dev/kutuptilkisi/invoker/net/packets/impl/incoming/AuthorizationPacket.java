package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;

public class AuthorizationPacket implements Packet {

    private String authKey;

    @Override
    public int packetID() {
        return 0x04;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        Packet.super.read(dis);
        authKey = dis.readUTF();
    }

    public String getAuthKey() {
        return authKey;
    }
}
