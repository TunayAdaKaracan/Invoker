package dev.kutuptilkisi.invoker.events.impl;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.PacketHandler;
import dev.kutuptilkisi.invoker.events.PacketListener;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.InformationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ServerRejectClientPacket;
import dev.kutuptilkisi.invoker.util.Logger;
import org.bukkit.Bukkit;

public class AuthorizationListener implements PacketListener {

    @PacketHandler
    public void onAuthorizationPacket(Client client, AuthorizationPacket packet){
        Logger.info("Authorization Packet Event for Client: "+client.getClientUUID());
        if(Invoker.invokerAPI.getNetHandler().getAuthKey().equals(packet.getAuthKey())){
            Logger.info("Authorization code matches...");
            client.setAuthorized();

            Logger.info("Sending information packet.");
            InformationPacket informationPacket = new InformationPacket();
            informationPacket.setClientID(client.getClientUUID());
            client.send(informationPacket);

            Invoker.invokerAPI.getNetHandler().addClient(client);
            return;
        }
        Logger.info("Authorization code doesn't match...");
        ServerRejectClientPacket rejectClientPacket = new ServerRejectClientPacket();
        client.send(rejectClientPacket);
        Invoker.invokerAPI.runOnScheduler(client::close);
    }
}
