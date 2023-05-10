package dev.kutuptilkisi.invoker.net.packets.impl.incoming;

import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RouteRequestPacket implements Packet {

    private String routeName;
    private String argsCount;
    private List<Object> args = new ArrayList<>();

    @Override
    public int packetID() {
        return 0x05;
    }

    public String getRouteName() {
        return routeName;
    }

    public Object[] getArgs(){
        return args.toArray();
    }

    @Override
    public void read(DataInputStream dis) throws IOException {
        routeName = dis.readUTF();
        argsCount = dis.readUTF();
        for(char c : argsCount.toCharArray()){
            Types type = Types.fromCharacter(c);
            if(type == null) continue;
            this.args.add(type.readFrom(dis));
        }
    }
}
