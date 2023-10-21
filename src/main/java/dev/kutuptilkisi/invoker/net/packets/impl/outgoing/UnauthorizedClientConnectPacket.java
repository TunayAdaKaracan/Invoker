package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class UnauthorizedClientConnectPacket implements Packet{

    private int clientID;
    public UnauthorizedClientConnectPacket(int clientID){
        setClientID(clientID);
    }

    @Override
    public int packetID() {
        return 0x00;
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
