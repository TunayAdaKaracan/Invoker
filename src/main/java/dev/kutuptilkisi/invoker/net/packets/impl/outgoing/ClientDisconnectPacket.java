package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.io.DataOutputStream;
import java.io.IOException;

public class ClientDisconnectPacket extends Event implements Packet {
    private static final HandlerList HANDLERS = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    private int clientID;

    public ClientDisconnectPacket(int clientID){
        super(true);
        setClientID(clientID);
    }

    @Override
    public int packetID() {
        return 0x02;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    @Override
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeInt(clientID);
    }
}
