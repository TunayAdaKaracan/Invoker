package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class RouteInvokeErrorPacket implements Packet {
    private int requestID;
    private String message;
    @Override
    public int packetID() {
        return 0x0B;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeInt(requestID);
        dos.writeUTF(message);
    }
}
