package dev.kutuptilkisi.invoker.custompacket.event;

import dev.kutuptilkisi.invoker.custompacket.TestPlugin;
import dev.kutuptilkisi.invoker.custompacket.packet.incoming.BanPlayerPacket;
import dev.kutuptilkisi.invoker.instance.Client;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BanPacketListener {

    private final TestPlugin testPlugin;
    public BanPacketListener(TestPlugin testPlugin){
        this.testPlugin = testPlugin;
    }

    public void onBanPacket(Client client, BanPlayerPacket banPlayerPacket){
        // Events are called async. You need to use #runTask in order to use any operation that must be done in sync.
        Bukkit.getScheduler().runTask(testPlugin, () -> {
            Player player = Bukkit.getPlayer(banPlayerPacket.getUserName());
            if(player != null){
                Bukkit.getBanList(BanList.Type.NAME).addBan(banPlayerPacket.getUserName(), null, null, null);
                player.kickPlayer("You are banned from server");
            }
        });
    }
}
