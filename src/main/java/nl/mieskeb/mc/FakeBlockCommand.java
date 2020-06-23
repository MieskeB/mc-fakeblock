package nl.mieskeb.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class FakeBlockCommand implements CommandExecutor {
    private FakeBlockApplication plugin;

    public FakeBlockCommand(FakeBlockApplication plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("FakeBlock plugin v1.0 by MieskeB");
            sender.sendMessage("There are three possible commands you can send:");
            sender.sendMessage("- /fb create <x> <y> <z>");
            sender.sendMessage("- /fb delete <x> <y> <z>");
            sender.sendMessage("- /fb delete <id>");
            sender.sendMessage("Pressing shift on any of the specified blocks will result into you falling through the block");
            return true;
        }

        if (!(args.length == 2 || args.length == 4)) {
            sender.sendMessage("Incorrect command usage");
            return false;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players");
            return false;
        }

        Player player = (Player) sender;
        if (args[0].equals("create")) {
            return this.createBlock(player, args);
        } else if (args[0].equals("delete")) {
            return this.deleteBlock(player, args);
        } else {
            sender.sendMessage("Incorrect command usage");
            return false;
        }
    }


    private boolean createBlock(Player sender, String[] args) {
        try {
            FileConfiguration blockConfig = this.plugin.getSettingsManager().getBlocksConfig();

            int id = this.getFreeKey();
            String key = "blocks." + id + ".";

            blockConfig.set(key + "world", sender.getWorld().getName());
            blockConfig.set(key + "x", Integer.parseInt(args[1]));
            blockConfig.set(key + "y", Integer.parseInt(args[2]));
            blockConfig.set(key + "z", Integer.parseInt(args[3]));
            blockConfig.save(this.plugin.getSettingsManager().getBlockFile());

            sender.sendMessage("Block with id " + id + " successfully created!");
            return true;
        } catch (IOException | NullPointerException e) {
            sender.sendMessage("An internal error occurred. A full stacktrace can be found in the logs.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean deleteBlock(Player sender, String[] args) {
        if (args.length == 2) {
            return this.deleteBlock(sender, args[1]);
        } else if (args.length == 4) {
            return this.deleteBlock(sender, Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]));
        }
        return false;
    }

    private boolean deleteBlock(Player sender, String id) {
        try {
            FileConfiguration blockConfig = this.plugin.getSettingsManager().getBlocksConfig();
            blockConfig.set("blocks." + id, null);
            blockConfig.save(this.plugin.getSettingsManager().getBlockFile());
            sender.sendMessage("Block with id " + id + " successfully deleted!");
            return true;
        } catch (IOException e) {
            sender.sendMessage("An internal error occurred. A full stacktrace can be found in the logs.");
            e.printStackTrace();
            return false;
        }
    }

    private boolean deleteBlock(Player sender, int x, int y, int z) {
        FileConfiguration blockConfig = this.plugin.getSettingsManager().getBlocksConfig();
        Set<String> blockConfigList = blockConfig.getConfigurationSection("blocks").getKeys(false);
        for (String id : blockConfigList) {
            String key = "blocks." + id + ".";

            int ix = blockConfig.getInt(key + "x");
            int iy = blockConfig.getInt(key + "y");
            int iz = blockConfig.getInt(key + "z");

            if (ix == x && iy == y && iz == z) {
                try {
                    blockConfig.set("blocks." + id, null);
                    blockConfig.save(this.plugin.getSettingsManager().getBlockFile());
                    sender.sendMessage("Block with id " + id + " successfully deleted!");
                    return true;
                } catch (IOException e) {
                    sender.sendMessage("An internal error occurred. A full stacktrace can be found in the logs.");
                    e.printStackTrace();
                    return false;
                }
            }
        }
        sender.sendMessage("Block not found");
        return false;
    }


    private int getFreeKey() {
        FileConfiguration blockConfig = this.plugin.getSettingsManager().getBlocksConfig();
        Set<String> blockConfigList = blockConfig.getConfigurationSection("blocks").getKeys(false);
        for (int i = 0; i < blockConfigList.size(); i++) {
            if (!this.contains(blockConfigList, i)) {
                return i;
            }
        }
        return blockConfigList.size();
    }

    private boolean contains(Set<String> items, int id) {
        for (String item : items) {
            int i = Integer.parseInt(item);
            if (id == i) {
                return true;
            }
        }
        return false;
    }
}
