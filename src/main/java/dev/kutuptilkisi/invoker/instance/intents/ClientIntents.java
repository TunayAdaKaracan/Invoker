package dev.kutuptilkisi.invoker.instance.intents;

import java.util.HashSet;
import java.util.Set;

public class ClientIntents {
    private final Set<Integer> packetIDs = new HashSet<>();

    public void enable(int id){
        this.packetIDs.add(id);
    }

    public void disable(int id){
        this.packetIDs.remove(id);
    }

    public boolean isEnabled(int id){
        return this.packetIDs.contains(id);
    }

    public static ClientIntents defaultPackets(){
        ClientIntents clientIntents = new ClientIntents();

        // Outgoing Packets
        clientIntents.enable(0x01);
        clientIntents.enable(0x02);
        clientIntents.enable(0x08);
        clientIntents.enable(0x07);
        clientIntents.enable(0x06);
        clientIntents.enable(0x03);
        clientIntents.enable(0x09);
        clientIntents.enable(0x0B);

        return clientIntents;
    }

    public static ClientIntents withPackets(int... ids){
        ClientIntents clientIntents = defaultPackets();
        for(int id : ids){
            clientIntents.enable(id);
        }
        return clientIntents;
    }
}
