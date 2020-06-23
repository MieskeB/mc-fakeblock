package nl.mieskeb.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FakeBlockTabComplete implements TabCompleter {
    private FakeBlockApplication plugin;

    public FakeBlockTabComplete(FakeBlockApplication plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 1) {
            List<String> tab = new ArrayList<>();
            tab.add("create");
            tab.add("delete");
            return tab;
        } else if (args.length == 2) {
            List<String> tab = new ArrayList<>();
            tab.add(((Player) commandSender).getTargetBlock(null, 200).getLocation().getBlockX() + "");
            return tab;
        } else if (args.length == 3) {
            List<String> tab = new ArrayList<>();
            tab.add(((Player) commandSender).getTargetBlock(null, 200).getLocation().getBlockY() + "");
            return tab;
        } else if (args.length == 4) {
            List<String> tab = new ArrayList<>();
            tab.add(((Player) commandSender).getTargetBlock(null, 200).getLocation().getBlockZ() + "");
            return tab;
        }
        return null;
    }
}
