package dev.kutuptilkisi.invoker.net.packets;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

//TODO: Maybe add bukkit events to all packets
public interface Packet {
    int packetID();

    default void send(Client client){
        Invoker.invokerAPI.getNetHandler().sendPacket(client, this);
    }
    default void broadcast(){
        Invoker.invokerAPI.getNetHandler().broadcastPacket(this);
    }

    default void write(DataOutputStream dos) throws IOException {
        dos.writeInt(packetID());
    }

    default void read(DataInputStream dis) throws IOException {

    }
}
