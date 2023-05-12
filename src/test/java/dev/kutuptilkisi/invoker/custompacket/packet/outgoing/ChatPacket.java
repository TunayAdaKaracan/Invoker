package dev.kutuptilkisi.invoker.custompacket.packet.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class ChatPacket implements Packet {

    private String userName;
    private String message;

    @Override
    public int packetID() {
        return 0x09; // Must be an unique packet ID
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(userName);
        dos.writeUTF(message);
    }
}
