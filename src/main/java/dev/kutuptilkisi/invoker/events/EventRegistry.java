package dev.kutuptilkisi.invoker.events;

import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.net.packets.Packet;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventRegistry {
    public static HashMap<Class<? extends Packet>, Map.Entry<Object, List<Method>>> eventCallbacks = new HashMap<>();

    static {
        registerEvent(new AuthorizationListener());
        registerEvent(new RouterListener());
    }

    public static void registerEvent(Object o){
        for(Method method : o.getClass().getMethods()){
            if(method.getParameterCount() == 2){
                Class<?>[] types = method.getParameterTypes();
                if(types[0] == Client.class && isImplementingPaketInterface(types[1])){
                    Class<? extends Packet> packetClass = (Class<? extends Packet>) types[1];

                    if(!eventCallbacks.containsKey(packetClass)){
                        eventCallbacks.put(packetClass, Map.entry(o, new ArrayList<>()));
                    }

                    eventCallbacks.get(packetClass).getValue().add(method);
                }
            }
        }
    }

    public static void fireEvent(Client client, Packet packet){
        if(eventCallbacks.containsKey(packet.getClass())){
            for(Method method : eventCallbacks.get(packet.getClass()).getValue()){
                try {
                    method.invoke(eventCallbacks.get(packet.getClass()).getKey(), client, packet);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void clearEventsOf(Class<? extends Packet> packetClazz){
        eventCallbacks.remove(packetClazz);
    }

    private static boolean isImplementingPaketInterface(Class<?> clazz){
        for(Class<?> clazzz : clazz.getInterfaces()){
            if(clazzz == Packet.class){
                return true;
            }
        }
        return false;
    }
}