package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

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
    public void write(ByteBuf byteBuf) {
        ByteBufUtil.writeUUID(byteBuf, responseUUID);
        ByteBufUtil.writeString(byteBuf, routeName);
    }
}
