package dev.kutuptilkisi.invoker.net.packets;


import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.EnableCustomPacketPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.RouteRequestPacket;

import java.util.HashMap;
import java.util.function.Supplier;

public class PacketFactory {
    private static final HashMap<Integer, Supplier<Packet>> packetFactory = new HashMap<>();

    static {
        registerPacket(new AuthorizationPacket().packetID(), AuthorizationPacket::new);
        registerPacket(new RouteRequestPacket().packetID(), RouteRequestPacket::new);
        registerPacket(new EnableCustomPacketPacket().packetID(), EnableCustomPacketPacket::new);
    }

    public static void registerPacket(int packetID, Supplier<Packet> packetSupplier){
        packetFactory.put(packetID, packetSupplier);
    }

    public static  <T extends Packet> T fromId(int id){
        if(!packetFactory.containsKey(id)) return null;
        return (T) packetFactory.get(id).get();
    }
}
