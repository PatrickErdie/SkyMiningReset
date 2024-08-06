package me.patrickerdie.box;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        switch (args.length) {
            case 1:
                suggestions.addAll(Arrays.asList("CREATE", "WAND", "TEST", "DELETE", "EDIT", "LIST", "HELP"));
                break;
            case 2:
                if (args[0].equalsIgnoreCase("CREATE") || args[0].equalsIgnoreCase("DELETE") || args[0].equalsIgnoreCase("EDIT")) {
                    LoadData loadData = new LoadData();
                    Set<Mine> mines = loadData.getMines();
                    for (Mine mine : mines) {
                        suggestions.add(mine.getName());
                    }
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("CREATE")) {
                    suggestions.add("INTEGER");
                } else if (args[0].equalsIgnoreCase("EDIT")) {
                    suggestions.addAll(Arrays.asList("MATERIALS", "NAME", "TIMER", "LOCATIONS"));
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("CREATE")) {
                    suggestions.addAll(getMaterialNames());
                } else if (args[0].equalsIgnoreCase("EDIT") && args[2].equalsIgnoreCase("MATERIALS")) {
                    suggestions.addAll(Arrays.asList("ADD", "REMOVE", "SET"));
                }
                break;
            default:
                if (args[0].equalsIgnoreCase("CREATE") && args.length % 2 == 0) {
                    suggestions.addAll(getMaterialNames());
                }
                break;
        }

        if (args.length > 0) {
            String lastArg = args[args.length - 1].toLowerCase();
            suggestions = suggestions.stream()
                    .filter(option -> option.toLowerCase().startsWith(lastArg))
                    .collect(Collectors.toList());
        }

        return suggestions;
    }

    private List<String> getMaterialNames() {
        return Arrays.stream(Material.values())
                .map(Material::name)
                .collect(Collectors.toList());
    }
}
