package dev.kutuptilkisi.invoker.util;

import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;

public class AuthUtil {
    private static final Map<ChannelId, Boolean> authorized = new HashMap<>();
    public static void setAuthorized(ChannelId channelId, boolean b){
        authorized.put(channelId, b);
    }

    public static boolean isAuthorized(ChannelId channelId){
        return authorized.getOrDefault(channelId, false);
    }

    public static void remove(ChannelId channelId){
        authorized.remove(channelId);
    }

    public static void clear(){
        authorized.clear();
    }
}
