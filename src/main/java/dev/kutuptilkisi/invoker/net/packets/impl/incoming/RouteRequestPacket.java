package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RouteRequestPacket implements Packet {

    private UUID requestUUID;
    private String routeName;
    private final List<Object> args = new ArrayList<>();

    @Override
    public int packetID() {
        return 0x05;
    }

    public UUID getRequestUUID() {
        return requestUUID;
    }

    public String getRouteName() {
        return routeName;
    }

    public Object[] getArgs(){
        return args.toArray();
    }

    @Override
    public void read(ByteBuf byteBuf){
        requestUUID = ByteBufUtil.readUUID(byteBuf);
        routeName = ByteBufUtil.readString(byteBuf);
        String argsCount = ByteBufUtil.readString(byteBuf);
        for(char c : argsCount.toCharArray()){
            Types type = Types.fromCharacter(c);
            if(type == null) continue;
            this.args.add(type.getTypeIO().read(byteBuf));
        }
    }
}
