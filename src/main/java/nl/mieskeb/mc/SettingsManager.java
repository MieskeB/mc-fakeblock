package nl.mieskeb.mc;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class SettingsManager {
    private FakeBlockApplication plugin;

    private FileConfiguration config = null;
    private File file = null;

    private FileConfiguration blocksConfig = null;
    private File blockFile = null;

    public SettingsManager(FakeBlockApplication plugin) {
        this.plugin = plugin;
    }

    public FileConfiguration getConfig() {
        if (this.config != null) {
            return this.config;
        }

        if (!this.plugin.getDataFolder().exists()) {
            this.plugin.getDataFolder().mkdir();
        }

        this.file = new File(this.plugin.getDataFolder(), "config.yml");

        if (!file.exists()) {
            this.plugin.saveResource("config.yml", true);
        }

        this.config = YamlConfiguration.loadConfiguration(this.file);
        return this.config;
    }

    public File getFile() {
        return file;
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
