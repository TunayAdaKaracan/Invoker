package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;

public class RouteResponsePacket implements Packet {

    private int responseID;
    private String routeName;
    private Types argType;
    private Object arg;

    @Override
    public int packetID() {
        return 0x06;
    }

    public void setResponseID(int responseID){
        this.responseID = responseID;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setArgType(Types argType) {
        this.argType = argType;
    }

    public void setArg(Object arg) {
        this.arg = arg;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeInt(responseID);
        dos.writeUTF(routeName);
        dos.writeUTF(argType != null ? String.valueOf(argType.getRepresentation()) : "v");
        if(argType != null){
            argType.getTypeIO().write(dos, arg);
        }
    }
}
