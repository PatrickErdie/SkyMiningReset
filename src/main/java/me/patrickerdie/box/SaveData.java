package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Map;

public class SaveData {

    public SaveData(Mine mine) {
        FileConfiguration config = Box.instance.getConfig();
        Location[] locations = mine.getLocations();
        config.set("boxes." + mine.getName() + ".location1", locations[0]);
        config.set("boxes." + mine.getName() + ".location2", locations[1]);
        config.set("boxes." + mine.getName() + ".timer", mine.getResetTime() * 20);
        Map<Material, Integer> materialIntegerMap = mine.getMaterials();
        for (Map.Entry map : materialIntegerMap.entrySet()) {
            config.set("boxes." + mine.getName() + ".materials." + map.getKey(), map.getValue());
        }
        Box.instance.saveConfig();
    }

}
