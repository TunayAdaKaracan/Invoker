package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ClientConnectPacket implements Packet {

    /*
        EVENTS
     */
    private boolean isCancelled;
    /*
        PACKET
     */
    private UUID clientUUID;
    public ClientConnectPacket(UUID clientUUID){
        setClientUUID(clientUUID);
    }

    @Override
    public int packetID() {
        return 0x01;
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
