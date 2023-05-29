package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class RouteNotFoundPacket implements Packet {
    private int responseID;
    private String routeName;

    @Override
    public int packetID() {
        return 0x07;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setResponseID(int responseID){
        this.responseID = responseID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeInt(responseID);
        dos.writeUTF(routeName);
    }
}
