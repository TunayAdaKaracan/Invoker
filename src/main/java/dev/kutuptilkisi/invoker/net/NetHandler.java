package dev.kutuptilkisi.invoker.net;

import com.google.gson.Gson;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientConnectPacket;
import dev.kutuptilkisi.invoker.net.packets.impl.outgoing.ClientDisconnectPacket;
import dev.kutuptilkisi.invoker.util.AuthUtil;
import dev.kutuptilkisi.invoker.util.IntentsUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.io.IOException;

public class NetHandler {
    private final String authKey;
    private final int PORT;

    private final ChannelGroup clients;
    private final Gson gson;

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;


    public NetHandler(int port, String authKey) throws IOException {
        this.PORT = port;
        this.authKey = authKey;

        this.clients = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.gson = new Gson();
    }

    public void start(){
        new Thread(() -> {
            bossGroup = new NioEventLoopGroup();
            workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap b = new ServerBootstrap();
                b.group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch)
                                    throws Exception {
                                ch.pipeline().addLast(new PacketDecoder(),
                                        new PacketEncoder(),
                                        new PacketProcessor());
                            }
                        }).option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture f = b.bind(PORT).sync().channel().closeFuture().sync();
            } catch (InterruptedException e) {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            } finally {
                workerGroup.shutdownGracefully();
                bossGroup.shutdownGracefully();
            }
        }).start();
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
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
        workerGroup = null;
        bossGroup = null;
        IntentsUtil.clear();
        AuthUtil.clear();
        clients.clear();
    }
}
