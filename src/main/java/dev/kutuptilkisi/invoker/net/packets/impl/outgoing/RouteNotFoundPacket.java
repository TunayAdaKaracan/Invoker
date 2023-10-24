package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

public class RouteNotFoundPacket implements Packet {
    private UUID responseUUID;
    private String routeName;

    @Override
    public int packetID() {
        return 0x07;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setResponseID(UUID responseID){
        this.responseUUID = responseID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(responseUUID.toString());
        dos.writeUTF(routeName);
    }
}
