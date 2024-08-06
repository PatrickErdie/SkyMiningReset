package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;

public class DeleteData {

    public DeleteData(String boxName) {
        FileConfiguration config = Box.instance.getConfig();
        for (String key : config.getConfigurationSection("boxes").getKeys(false)) {
            if (key.equalsIgnoreCase(boxName)) {
                config.set("boxes." + key, null);
            }
        }
        Box.instance.saveConfig();
    }

}
