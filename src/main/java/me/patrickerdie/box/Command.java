package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        Locations locations = Box.locationsMap.get(player);
        Material material;
        EditData data = null;

        switch (strings[0]) {
            case "TEST":
                player.sendMessage("INFO: " + locations.getLocation1() + " " + locations.getLocation2());
                break;
            case "DELETE":
                DeleteData deleteData = new DeleteData(strings[1]);
                player.sendMessage("Box deleted");
                break;
            case "EDIT":
                data = new EditData(strings[1]);
                switch (strings[2]) {
                    case "NAME":
                        data.setName(strings[3]);
                        break;
                    case "TIMER":
                        data.setResetTimer(Integer.parseInt(strings[3]));
                        break;
                    case "LOCATIONS":
                        Location[] location = new Location[2];
                        location[0] = locations.getLocation1();
                        location[1] = locations.getLocation2();
                        data.setLocations(location);
                        break;
                    case "MATERIALS":
                        switch (strings[3]) {
                            case "ADD":
                                data.addMaterial(Material.matchMaterial(strings[4]), Integer.parseInt(strings[5]));
                                break;
                            case "REMOVE":
                                material = Material.matchMaterial(strings[4]);
                                data.deleteMaterial(material);
                                break;
                            case "SET":
                                HashMap<Material, Integer> materialIntegerHashMap = new HashMap<>();
                                for (int i = 4; i <= strings.length; i += 2) {
                                    material = Material.matchMaterial(strings[i]);
                                    int percent = Integer.parseInt(strings[i++]);
                                    materialIntegerHashMap.put(material, percent);
                                }
                                data.setMaterials(materialIntegerHashMap);
                                break;
                        }
                        break;
                }
                break;
            case "WAND":
                Wand.getWand(player);
                player.sendMessage("WAND received");
                break;
            case "CREATE":
                if (locations == null) {
                    player.sendMessage("Locations are null");
                }
                String name = strings[1];
                Mine mine = new Mine(name);
                int resetTimer = Integer.parseInt(strings[2]);
                Location[] location = new Location[2];
                location[0] = locations.getLocation1();
                location[1] = locations.getLocation2();
                mine.setBounds(location);
                mine.setResetTime(resetTimer);
                for (int i = 3; i < strings.length; i += 2) {
                    material = Material.matchMaterial(strings[i]);
                    int percent = Integer.parseInt(strings[i + 1]);
                    mine.addMaterial(material, percent);
                }
                SaveData saveData = new SaveData(mine);
                break;
        }
        data.save();
        return true;
    }

}
