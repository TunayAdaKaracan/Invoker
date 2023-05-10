package dev.kutuptilkisi.invoker.net.packets;


import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.RouteRequestPacket;

import java.util.HashMap;
import java.util.function.Supplier;

public class PacketFactory {
    private static final HashMap<Integer, Supplier<Packet>> packetFactory = new HashMap<>();

    static {
        packetFactory.put(new AuthorizationPacket().packetID(), AuthorizationPacket::new);
        packetFactory.put(new RouteRequestPacket().packetID(), RouteRequestPacket::new);
    }

    public static  <T extends Packet> T fromId(int id){
        if(!packetFactory.containsKey(id)) return null;
        return (T) packetFactory.get(id).get();
    }
}
