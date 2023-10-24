package dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.net.NetHandler;
import dev.kutuptilkisi.invoker.router.RouteManager;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;

public class InvokerBungee extends Plugin implements Invoker.InvokerAPI {

    private NetHandler netHandler;
    private RouteManager routeManager;

    private Configuration config;

    @Override
    public void onEnable() {
        Invoker.invokerAPI = this;
        getLogger().info("Invoker working on Bungee Server...");

        try{
            setupConfigFile();
        } catch (IOException e) {
            getLogger().severe("Couldn't initialize config file. Stopping invoker...");
            return;
        }

        routeManager = new RouteManager();
        try {
            netHandler = new NetHandler(config.getInt("network.port"), config.getString("network.auth-key"));
            netHandler.start();
        } catch (IOException e) {
            getLogger().severe("Can't create a socket server!");
            throw new RuntimeException(e);
        }
    }

    private void setupConfigFile() throws IOException {
        if(!getDataFolder().exists()){
            getDataFolder().mkdir();
        }
        File file = new File(getDataFolder(), "config.yml");

        if(!file.exists()){
            Files.copy(getResourceAsStream("config.yml"), file.toPath());
        }
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    @Override
    public void onDisable() {
        netHandler.close();
    }

    @Override
    public boolean getBoolean(String path) {
        return config.getBoolean(path);
    }

    @Override
    public String getString(String path) {
        return config.getString(path);
    }

    @Override
    public int getInteger(String path) {
        return config.getInt(path);
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

    @Override
    public void runOnScheduler(Callable callable) {
        getProxy().getScheduler().schedule(this, callable::call, 1L, TimeUnit.MILLISECONDS);
    }

}
