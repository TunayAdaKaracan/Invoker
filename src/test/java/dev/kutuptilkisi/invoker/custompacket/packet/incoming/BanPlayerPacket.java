package dev.kutuptilkisi.invoker.custompacket.packet.incoming;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;

// You can also extend Bukkit Event class.
// All packets which extends event classes are called automatically
// You must make events async as they are called on another thread
public class BanPlayerPacket implements Packet {

    private String userName;

    @Override
    public int packetID() {
        return 0x10; // Must be an unique packet ID
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        userName = dis.readUTF();
    }
}
