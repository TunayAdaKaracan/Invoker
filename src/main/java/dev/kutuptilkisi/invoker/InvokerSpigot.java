package dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.net.NetHandler;
import dev.kutuptilkisi.invoker.router.RouteManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class InvokerSpigot extends JavaPlugin implements Invoker.InvokerAPI {

    private NetHandler netHandler;
    private RouteManager routeManager;

    @Override
    public void onEnable() {
        getLogger().info("Invoker working on Spigot Server...");
        Invoker.invokerAPI = this;

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
    }

    @Override
    public boolean getBoolean(String path) {
        return getConfig().getBoolean(path);
    }

    @Override
    public String getString(String path) {
        return getConfig().getString(path);
    }

    @Override
    public int getInteger(String path) {
        return getConfig().getInt(path);
    }

    @Override
    public NetHandler getNetHandler() {
        return netHandler;
    }

    @Override
    public RouteManager getRouteManager() {
        return routeManager;
    }

    @Override
    public void logInfo(String message) {
        getLogger().info(message);
    }

    @Override
    public void logWarn(String message) {
        getLogger().warning(message);
    }

    @Override
    public void logSevere(String message) {
        getLogger().severe(message);
    }
}
