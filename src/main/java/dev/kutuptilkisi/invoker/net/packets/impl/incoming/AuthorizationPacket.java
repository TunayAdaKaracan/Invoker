package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

public class AuthorizationPacket implements Packet {

    private String authKey;

    @Override
    public int packetID() {
        return 0x04;
    }

    @Override
    public void read(ByteBuf byteBuffer) {
        authKey = ByteBufUtil.readString(byteBuffer);
    }

    public String getAuthKey() {
        return authKey;
    }
}
