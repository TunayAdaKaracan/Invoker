package dev.kutuptilkisi.invoker.net;

import com.google.gson.Gson;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientConnectPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientDisconnectPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;

public class NetHandler {
    private final String authKey;
    private final int PORT;

    private final ChannelGroup clients;
    private final Gson gson;


    public NetHandler(int port, String authKey) throws IOException {
        this.PORT = port;
        this.authKey = authKey;

        this.clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.gson = new Gson();
    }

    public void start(){

    }

    public String getAuthKey() {
        return authKey;
    }

    public Gson getGson(){
        return gson;
    }

    public Channel getClient(ChannelId clientID){
        for(Channel client : clients){
            if(client.id() == clientID){
                return client;
            }
        }
        return null;
    }

    public void addClient(Channel client){
        this.clients.add(client);
        broadcastPacket(new ClientConnectPacket(client.id()));
    }

    public void removeClient(Channel client){
        if (clients.contains(client)) {
            this.clients.remove(client);
            broadcastPacket(new ClientDisconnectPacket(client.id()));
        }
    }

    public void sendPacket(ClientRequest clientRequest, Packet packet){
        clientRequest.getChannelHandlerContext().writeAndFlush(packet);
    }

    public void broadcastPacket(Packet packet){
        this.clients.writeAndFlush(packet);
    }

    public void close(){

    }
}
