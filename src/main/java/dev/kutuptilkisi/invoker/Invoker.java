package dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.net.NetHandler;
import dev.kutuptilkisi.invoker.router.RouteManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class Invoker extends JavaPlugin {

    private static Invoker instance;

    public static Invoker getInstance() {
        return instance;
    }

    private NetHandler netHandler;
    private RouteManager routeManager;

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        routeManager = new RouteManager();
        try {
            netHandler = new NetHandler(getConfig().getInt("network.port"), getConfig().getString("network.auth-key"));
            netHandler.start();
        } catch (IOException e) {
            getLogger().severe("Can't create a socket server!");
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        netHandler.close();
        // Plugin shutdown logic
    }


    public NetHandler getNetHandler() {
        return netHandler;
    }

    public RouteManager getRouteManager() {
        return routeManager;
    }
}
