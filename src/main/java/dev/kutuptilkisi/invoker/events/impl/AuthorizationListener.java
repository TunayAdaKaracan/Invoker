package dev.kutuptilkisi.invoker.events.impl;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.PacketHandler;
import dev.kutuptilkisi.invoker.events.PacketListener;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.InformationPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ServerRejectClientPacket;
import dev.kutuptilkisi.invoker.util.AuthUtil;
import dev.kutuptilkisi.invoker.util.Logger;

public class AuthorizationListener implements PacketListener {

    @PacketHandler
    public void onAuthorizationPacket(ClientRequest client, AuthorizationPacket packet){
        Logger.info("Authorization Packet Event for Client: "+client.getChannelHandlerContext().channel().id());
        if(Invoker.invokerAPI.getNetHandler().getAuthKey().equals(packet.getAuthKey())){
            Logger.info("Authorization code matches...");
            AuthUtil.setAuthorized(client.getChannelHandlerContext().channel().id(), true);
            Logger.info("Sending information packet.");
            InformationPacket informationPacket = new InformationPacket();
            informationPacket.setClientID(client.getChannelHandlerContext().channel().id());
            client.send(informationPacket);

            Invoker.invokerAPI.getNetHandler().addClient(client.getChannelHandlerContext().channel());
            return;
        }
        Logger.info("Authorization code doesn't match...");
        ServerRejectClientPacket rejectClientPacket = new ServerRejectClientPacket();
        client.send(rejectClientPacket);
    }
}
