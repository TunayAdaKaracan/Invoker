package dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.net.NetHandler;
import dev.kutuptilkisi.invoker.router.RouteManager;

public class Invoker {
    public interface InvokerAPI {
        // Config
        boolean getBoolean(String path);
        String getString(String path);
        int getInteger(String path);

        // Network
        NetHandler getNetHandler();
        RouteManager getRouteManager();

        // Logger
        void logInfo(String message);
        void logWarn(String message);
        void logSevere(String message);
    }

    public static InvokerAPI invokerAPI;
}
