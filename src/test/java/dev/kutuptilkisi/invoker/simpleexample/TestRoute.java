package dev.kutuptilkisi.invoker.simpleexample;

import dev.kutuptilkisi.invoker.instance.Client;
import dev.kutuptilkisi.invoker.router.Routable;
import dev.kutuptilkisi.invoker.router.Route;
import org.bukkit.Bukkit;

public class TestRoute implements Routable {
    @Override
    public String getIdentifier() {
        // Identifier is like a prefix to your route names
        // To access routes you have to add this prefix
        // Example: "kutup_isActive"
        return "kutup";
    }

    @Route(route = "isActive") // Route name
    public boolean isActive(Client client, String username){
        return Bukkit.getPlayer(username) != null;
    }
}
