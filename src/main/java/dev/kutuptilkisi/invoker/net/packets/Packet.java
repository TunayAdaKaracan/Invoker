package dev.kutuptilkisi.invoker.net.packets;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import io.netty.buffer.ByteBuf;

//TODO: Maybe add bukkit events to all packets
public interface Packet {
    int packetID();

    default void send(ClientRequest clientRequest){
        Invoker.invokerAPI.getNetHandler().sendPacket(clientRequest, this);
    }

    default void broadcast(){
        Invoker.invokerAPI.getNetHandler().broadcastPacket(this);
    }

    default void write(ByteBuf byteBuffer){}

    default void read(ByteBuf byteBuffer){}
}
