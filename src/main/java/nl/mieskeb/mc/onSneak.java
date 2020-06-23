package nl.mieskeb.mc;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.Iterator;
import java.util.Set;

public class onSneak implements Listener {
    private FakeBlockApplication plugin;

    public onSneak(FakeBlockApplication plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        if (this.plugin.getSettingsManager().getConfig().getBoolean("fall-by-sneaking")) {
            Player player = event.getPlayer();
            if (player.isSneaking()) {
                Location location = event.getPlayer().getLocation();
                location.setY(location.getBlockY() - 1);
                if (!needsTeleport(location)) {
                    return;
                }
                location.setY(location.getBlockY() + 0.99);
                player.teleport(location);
            }
        }
    }

    private boolean needsTeleport(Location location) {
        FileConfiguration blockConfig = this.plugin.getSettingsManager().getBlocksConfig();
        Set<String> blockConfigList = blockConfig.getConfigurationSection("blocks").getKeys(false);
        for (Iterator<String> it = blockConfigList.iterator(); it.hasNext(); ) {
            String id = it.next();
            String key = "blocks." + id + ".";

            int ix = blockConfig.getInt(key + "x");
            int iy = blockConfig.getInt(key + "y");
            int iz = blockConfig.getInt(key + "z");

            if (ix == location.getBlockX() && iy == location.getBlockY() && iz == location.getBlockZ()) {
                return true;
            }
        }
        return false;
    }
}
