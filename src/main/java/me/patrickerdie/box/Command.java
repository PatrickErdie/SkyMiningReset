package me.patrickerdie.box;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        Player player = (Player) commandSender;
        Locations locations = Box.locationsMap.get(player);

        switch (strings[0]) {
            case "TEST":
                player.sendMessage("INFO: " + locations.getLocation1() + " " + locations.getLocation2());
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
                    Material material = Material.matchMaterial(strings[i]);
                    int percent = Integer.parseInt(strings[i + 1]);
                    mine.addMaterial(material, percent);
                }
                SaveData saveData = new SaveData(mine);
                break;
        }
        return true;
    }

}
