package dev.kutuptilkisi.invoker.events.impl;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.PacketHandler;
import dev.kutuptilkisi.invoker.events.PacketListener;
import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.instance.Types;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.RouteRequestPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.RouteNotFoundPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.RouteResponsePacket;
import dev.kutuptilkisi.invoker.router.impl.RouteData;
import dev.kutuptilkisi.invoker.util.Pair;

import java.lang.reflect.InvocationTargetException;

public class RouterListener implements PacketListener {

    @PacketHandler
    public void onRouteRequest(Client client, RouteRequestPacket packet){
        RouteData routeData = Invoker.getInstance().getRouteManager().getRouteOrNull(packet.getRouteName());
        if(routeData == null){
            RouteNotFoundPacket routeNotFoundPacket = new RouteNotFoundPacket();
            routeNotFoundPacket.setRouteName(packet.getRouteName());
            routeNotFoundPacket.setResponseID(client.getRouteRequestCounter().getCount());
            client.send(routeNotFoundPacket);
            client.getRouteRequestCounter().increase();
            return;
        }

        Object[] argsToPass = new Object[packet.getArgs().length+1];
        argsToPass[0] = client;
        for(int i=0; i<packet.getArgs().length; i++){
            argsToPass[i+1] = packet.getArgs()[i];
        }

        Pair<Types, Object> ret;

        try {
            ret = routeData.executeRoute(argsToPass);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        RouteResponsePacket responsePacket = new RouteResponsePacket();
        responsePacket.setResponseID(client.getRouteRequestCounter().getCount());
        responsePacket.setArgType(ret.getKey());
        responsePacket.setRouteName(packet.getRouteName());
        responsePacket.setArg(ret.getValue());
        client.send(responsePacket);
        client.getRouteRequestCounter().increase();
    }
}
