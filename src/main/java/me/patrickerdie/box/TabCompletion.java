package me.patrickerdie.box;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> suggestions = new ArrayList<>();

        switch (args.length) {
            case 1:
                suggestions.addAll(Arrays.asList("CREATE", "WAND", "TEST"));
                break;
            case 2:
                if (args[0].equalsIgnoreCase("CREATE")) {
                    suggestions.add("NAME");
                }
                break;
            case 3:
                if (args[0].equalsIgnoreCase("CREATE")) {
                    suggestions.add("INTEGER");
                }
                break;
            case 4:
                if (args[0].equalsIgnoreCase("CREATE")) {
                    suggestions.addAll(getMaterialNames());
                }
                break;
            default:
                if (args[0].equalsIgnoreCase("CREATE") && args.length % 2 == 0) {
                    suggestions.addAll(getMaterialNames());
                }
                break;
        }

        // Filter suggestions based on the last argument
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
