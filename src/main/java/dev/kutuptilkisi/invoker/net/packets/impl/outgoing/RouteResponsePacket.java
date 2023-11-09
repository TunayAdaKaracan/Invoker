package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class RouteResponsePacket implements Packet {

    private UUID responseUUID;
    private String routeName;
    private Types argType;
    private Object arg;

    @Override
    public int packetID() {
        return 0x06;
    }

    public void setResponseUUID(UUID responseID){
        this.responseUUID = responseID;
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
    public void write(ByteBuf byteBuf) {
        ByteBufUtil.writeUUID(byteBuf, responseUUID);
        ByteBufUtil.writeString(byteBuf, routeName);
        ByteBufUtil.writeString(byteBuf, argType != null ? String.valueOf(argType.getRepresentation()) : "V");
        if(argType != null){
            argType.getTypeIO().write(byteBuf, arg);
        }
    }
}
