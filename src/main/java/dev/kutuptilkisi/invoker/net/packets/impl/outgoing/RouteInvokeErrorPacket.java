package dev.kutuptilkisi.invoker.net.packets.impl.outgoing;

import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
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
    public void write(DataOutputStream dos) throws IOException {
        Packet.super.write(dos);
        dos.writeUTF(requestUUID.toString());
        dos.writeUTF(routeName);
        dos.writeUTF(message);
    }
}
