package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClientConnectPacket implements Packet {

    /*
        EVENTS
     */
    private boolean isCancelled;
    /*
        PACKET
     */
    private int clientID;
    public ClientConnectPacket(int clientID){
        setClientID(clientID);
    }

    @Override
    public int packetID() {
        return 0x01;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeInt(clientID);
    }
}
