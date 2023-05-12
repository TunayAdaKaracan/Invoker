package dev.kutuptilkisi.invoker.simpleexample;

import dev.kutuptilkisi.invoker.Invoker;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        // Add routable to route manager
        Invoker.getInstance().getRouteManager().addRoute(new TestRoute());
    }
}
