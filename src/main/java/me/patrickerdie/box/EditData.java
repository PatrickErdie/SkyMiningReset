package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EditData {

    private String name;
    private Location[] locations;
    private FileConfiguration config = Box.instance.getConfig();
    private int resetTimer;
    private HashMap<Material, Integer> hashMap;
    private Mine mine;
    private boolean extra = false;

    public EditData(String name) {
        this.name = name;
        hashMap = new HashMap<>();
        for (String key : config.getConfigurationSection("boxes").getKeys(false)) {
            if (key.equalsIgnoreCase(name)) {
                locations = new Location[2];
                locations[0] = config.getLocation("boxes." + key + ".location1");
                locations[1] = config.getLocation("boxes." + key + ".location2");
                if (locations[0] == null || locations[1] == null) {
                    Box.instance.getLogger().warning("Invalid locations for box: " + key);
                    continue;
                }
                resetTimer = config.getInt("boxes." + key + ".timer");
                Mine mine = new Mine(key);
                mine.setBounds(locations);
                mine.setResetTime(resetTimer);
                if (config.getConfigurationSection("boxes." + key + ".materials") != null) {
                    for (String materialKey : config.getConfigurationSection("boxes." + key + ".materials").getKeys(false)) {
                        Material material = Material.matchMaterial(materialKey);
                        int percentage = config.getInt("boxes." + key + ".materials." + materialKey);
                        if (material != null) {
                            hashMap.put(material, percentage);
                        } else {
                            Box.instance.getLogger().warning("Invalid material: " + materialKey);
                        }
                    }
                }
            }
        }
    }

    public void setName(String newName) {
        DeleteData deleteData = new DeleteData(this.name);
        this.name = newName;
    }

    public void setLocations(Location[] locations) {
        this.locations = locations;
    }

    public void setResetTimer(int resetTime) {
        this.resetTimer = resetTime;
        extra = true;
    }

    public void setMaterials(HashMap<Material, Integer> hashMap) {
        this.hashMap = hashMap;
    }

    public void addMaterial(Material material, int percent) {
        hashMap.put(material, percent);
    }

    public void deleteMaterial(Material material) {
        if (hashMap.containsKey(material)) {
            hashMap.remove(material);
        }
    }




    public void save() {
        mine = new Mine(name);
        mine.setBounds(locations);
        mine.setResetTime(resetTimer);
        for (Map.Entry<Material, Integer> map : hashMap.entrySet()) {
            Material material = map.getKey();
            int percent = map.getValue();
            mine.addMaterial(material, percent);
        }
        SaveData saveData = new SaveData(mine, extra);
    }








}
