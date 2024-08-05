package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.*;

public class Mine {

    private String name;
    private int resetTime;
    private Map<Material, Integer> materials;
    private Location[] locations;

    public Mine(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
        materials = new HashMap<>();
        locations = new Location[2];
    }


    public void setBounds(Location[] locations) {
        if (locations == null || locations.length < 2) {
            throw new IllegalArgumentException("Invalid locations array. Must contain at least two locations.");
        }
        this.locations[0] = locations[0];
        this.locations[1] = locations[1];
    }


    public void addMaterial(Material material, int percentage) {
        if (material == null || percentage < 0) {
            throw new IllegalArgumentException("Invalid material or percentage.");
        }
        materials.put(material, percentage);
    }

    public void setResetTime(int resetTime) {
        if (resetTime < 0) {
            throw new IllegalArgumentException("Reset time cannot be negative.");
        }
        this.resetTime = resetTime;
    }


    public void reset() {
        if (locations == null || locations[0] == null || locations[1] == null) {
            throw new IllegalStateException("Locations are not properly set.");
        }

        List<Material> materialPool = new ArrayList<>();
        for (Map.Entry<Material, Integer> entry : materials.entrySet()) {
            int count = entry.getValue();
            for (int i = 0; i < count; i++) {
                materialPool.add(entry.getKey());
            }
        }

        int startX = Math.min(locations[0].getBlockX(), locations[1].getBlockX());
        int endX = Math.max(locations[0].getBlockX(), locations[1].getBlockX());
        int startY = Math.min(locations[0].getBlockY(), locations[1].getBlockY());
        int endY = Math.max(locations[0].getBlockY(), locations[1].getBlockY());
        int startZ = Math.min(locations[0].getBlockZ(), locations[1].getBlockZ());
        int endZ = Math.max(locations[0].getBlockZ(), locations[1].getBlockZ());

        Random rand = new Random();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    int randomIndex = rand.nextInt(materialPool.size());
                    Material randomMaterial = materialPool.get(randomIndex);
                    Location loc = new Location(locations[0].getWorld(), x, y, z);
                    loc.getBlock().setType(randomMaterial);
                }
            }
        }
    }


    public String getName() {
        return name;
    }

    public int getResetTime() {
        return resetTime;
    }

    public Location[] getLocations() {
        return locations;
    }

    public Map<Material, Integer> getMaterials() {
        return materials;
    }

}
