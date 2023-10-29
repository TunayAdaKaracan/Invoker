package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class InformationPacket implements Packet {

    private UUID clientUUID;

    @Override
    public int packetID() {
        return 0x08;
    }

    public void setClientID(UUID clientUUID) {
        this.clientUUID = clientUUID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(clientUUID.toString());
    }
}
