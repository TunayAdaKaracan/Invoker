package dev.kutuptilkisi.invoker.util;

import dev.kutuptilkisi.invoker.instance.intents.ClientIntents;
import io.netty.channel.ChannelId;

import java.util.HashMap;
import java.util.Map;

// TODO
public class IntentsUtil {
    private static final Map<ChannelId, ClientIntents> intentsMap = new HashMap<>();

    public static void addIntent(ChannelId channelId, ClientIntents clientIntents){
        intentsMap.put(channelId, clientIntents);
    }

    public static void addIntent(ChannelId channelId){
        addIntent(channelId, ClientIntents.defaultPackets());
    }

    public static ClientIntents getIntents(ChannelId channelId){
        return intentsMap.get(channelId);
    }

    public static void remove(ChannelId channelId){
        intentsMap.remove(channelId);
    }

    public static void clear(){
        intentsMap.clear();
    }
}
