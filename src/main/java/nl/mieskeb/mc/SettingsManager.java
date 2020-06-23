package nl.mieskeb.mc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SettingsManager {
    private FakeBlockApplication plugin;

    private FileConfiguration blocksConfig = null;
    private File blockFile = null;

    public SettingsManager(FakeBlockApplication plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getBlocksConfig() {
        if (this.blocksConfig != null) {
            return this.blocksConfig;
        }

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        this.blockFile = new File(this.plugin.getDataFolder(), "blocks.yml");

        if (!blockFile.exists()) {
            this.plugin.saveResource("blocks.yml", true);
        }

        this.blocksConfig = YamlConfiguration.loadConfiguration(blockFile);
        return blocksConfig;
    }

    public File getBlockFile() {
        return blockFile;
    }
}
