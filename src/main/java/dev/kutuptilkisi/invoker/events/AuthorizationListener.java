package dev.kutuptilkisi.invoker.events;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;

public class AuthorizationListener {

    public void onAuthorizationPacket(Client client, AuthorizationPacket packet){
        if(Invoker.getInstance().getNetHandler().getAuthKey().equals(packet.getAuthKey())){
            client.setAuthorized();
            Invoker.getInstance().getNetHandler().addClient(client);
        }
    }
}
