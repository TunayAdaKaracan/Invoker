package dev.kutuptilkisi.invoker.net;

import dev.kutuptilkisi.invoker.Invoker;
import dev.kutuptilkisi.invoker.events.EventRegistry;
import dev.kutuptilkisi.invoker.instance.ClientRequest;
import dev.kutuptilkisi.invoker.net.packets.Packet;
import dev.kutuptilkisi.invoker.net.packets.impl.incoming.AuthorizationPacket;
import dev.kutuptilkisi.invoker.util.AuthUtil;
import dev.kutuptilkisi.invoker.util.IntentsUtil;
import dev.kutuptilkisi.invoker.util.Logger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class PacketProcessor extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        IntentsUtil.addIntent(ctx.channel().id());
        AuthUtil.setAuthorized(ctx.channel().id(), false);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        Invoker.invokerAPI.getNetHandler().removeClient(ctx.channel());
        AuthUtil.remove(ctx.channel().id());
        IntentsUtil.remove(ctx.channel().id());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Packet packet = (Packet) msg;
        if(!(packet instanceof AuthorizationPacket) && !AuthUtil.isAuthorized(ctx.channel().id())){
            Logger.warning("Skipped event trigger as client is not authorized.");
            return;
        }
        ByteBuf buf = ctx.channel().alloc().buffer();
        EventRegistry.fireEvent(new ClientRequest(ctx, buf), packet);
        buf.release();
    }
}
