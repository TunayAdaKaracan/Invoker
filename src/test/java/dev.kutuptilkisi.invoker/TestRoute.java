package java.dev.kutuptilkisi.invoker;

import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.router.Routable;
import dev.kutuptilkisi.invoker.router.Route;
import org.bukkit.Bukkit;

public class TestRoute implements Routable {
    @Override
    public String getIdentifier() {
        return "kutup";
    }

    @Route(route = "isActive")
    public boolean isActive(Client client, String username){
        return Bukkit.getPlayer(username) != null;
    }
}
