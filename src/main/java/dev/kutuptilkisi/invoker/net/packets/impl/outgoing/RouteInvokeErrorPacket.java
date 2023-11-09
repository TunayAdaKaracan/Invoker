package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

import java.util.UUID;

public class RouteInvokeErrorPacket implements Packet {
    private UUID requestUUID;
    private String routeName;
    private String message;
    @Override
    public int packetID() {
        return 0x0B;
    }

    public void setRequestUUID(UUID requestUUID) {
        this.requestUUID = requestUUID;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void write(ByteBuf byteBuf)  {
        ByteBufUtil.writeUUID(byteBuf, requestUUID);
        ByteBufUtil.writeString(byteBuf, routeName);
        ByteBufUtil.writeString(byteBuf, message);
    }
}
