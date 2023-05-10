package java.dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.Invoker;
import org.bukkit.plugin.java.JavaPlugin;

public final class TestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        Invoker.getInstance().getRouteManager().addRoute(new TestRoute());
    }
}
