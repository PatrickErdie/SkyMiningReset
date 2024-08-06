package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;


public final class Box extends JavaPlugin {

    public static Box instance;
    public static Map<Player, Locations> locationsMap = new HashMap<>();

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {

        saveDefaultConfig();

        new Reset();
        getCommand("skyminereset").setExecutor(new Command());
        getCommand("skyminereset").setTabCompleter(new TabCompletion());
        getServer().getPluginManager().registerEvents(new Wand(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static void setLocation1(Player player, Location location) {
        Locations locations = locationsMap.computeIfAbsent(player, k -> new Locations());
        locations.setLocation1(location);
        locationsMap.put(player, locations);
    }

    public static void setLocation2(Player player, Location location) {
        Locations locations = locationsMap.computeIfAbsent(player, k -> new Locations());
        locations.setLocation2(location);
        locationsMap.put(player, locations);
    }


}
