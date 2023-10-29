package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ClientDisconnectPacket implements Packet {
    private UUID clientUUID;

    public ClientDisconnectPacket(UUID clientUUID){
        setClientUUID(clientUUID);
    }

    @Override
    public int packetID() {
        return 0x02;
    }

    public void setClientUUID(UUID clientUUID) {
        this.clientUUID = clientUUID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(clientUUID.toString());
    }
}
