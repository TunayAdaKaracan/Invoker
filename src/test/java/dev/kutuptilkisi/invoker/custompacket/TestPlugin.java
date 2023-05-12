package dev.kutuptilkisi.invoker.custompacket;

import dev.kutuptilkisi.invoker.custompacket.event.BanPacketListener;
import dev.kutuptilkisi.invoker.custompacket.event.PlayerChatListener;
import dev.kutuptilkisi.invoker.custompacket.packet.incoming.BanPlayerPacket;
import dev.kutuptilkisi.invoker.events.EventRegistry;
import dev.kutuptilkisi.invoker.net.packets.PacketFactory;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Lets register our packet(s) first
        PacketFactory.registerPacket(new BanPlayerPacket().packetID(), BanPlayerPacket::new); // Incoming packets must be registered to packet factory

        // Lets register our events now
        Bukkit.getPluginManager().registerEvents(new PlayerChatListener(), this);

        // Now this is the unusual part. Invoker uses an internal packet event system. You must register your event to EventRegistry
        EventRegistry.registerEvent(new BanPacketListener(this));

        // Now everything is complete!
        // Yes! This is just what you need to do.
        // Custom Packets with Invoker is really easy to implement.
        // We are aiming to improve our extendability more!
    }
}
