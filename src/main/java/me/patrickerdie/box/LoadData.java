package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashSet;
import java.util.Set;

public class LoadData {

    private Set<Mine> mines;

    public LoadData() {
        FileConfiguration config = Box.instance.getConfig();
        mines = new HashSet<>();
        for (String key : config.getConfigurationSection("boxes").getKeys(false)) {
            Location[] locations = new Location[2];
            locations[0] = config.getLocation("boxes." + key + ".location1");
            locations[1] = config.getLocation("boxes." + key + ".location2");
            if (locations[0] == null || locations[1] == null) {
                Box.instance.getLogger().warning("Invalid locations for box: " + key);
                continue;
            }

            int resetTimer = config.getInt("boxes." + key + ".timer");
            Mine mine = new Mine(key);
            mine.setBounds(locations);
            mine.setResetTime(resetTimer);

            if (config.getConfigurationSection("boxes." + key + ".materials") != null) {
                for (String materialKey : config.getConfigurationSection("boxes." + key + ".materials").getKeys(false)) {
                    Material material = Material.matchMaterial(materialKey);
                    int percentage = config.getInt("boxes." + key + ".materials." + materialKey);
                    if (material != null) {
                        mine.addMaterial(material, percentage);
                    } else {
                        Box.instance.getLogger().warning("Invalid material: " + materialKey);
                    }
                }
            }

            mines.add(mine);
        }
    }

    public Set<Mine> getMines() {
        return mines;
    }

}
