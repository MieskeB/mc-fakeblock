package nl.mieskeb.mc;

import org.bukkit.plugin.java.JavaPlugin;

public class FakeBlockApplication extends JavaPlugin {
    private SettingsManager settingsManager;

    @Override
    public void onEnable() {
        System.out.println("Fake block initializing...");

        this.settingsManager = new SettingsManager(this);
        this.settingsManager.getConfig();
        this.settingsManager.getBlocksConfig();

        this.getCommand("fakeblock").setTabCompleter(new FakeBlockTabComplete(this));
        this.getCommand("fakeblock").setExecutor(new FakeBlockCommand(this));

        this.getServer().getPluginManager().registerEvents(new onSneak(this), this);

        System.out.println("Fake block successfully initialized!");
    }

    @Override
    public void onDisable() {
        System.out.println("Fake Block disabling...");
        System.out.println("Fake Block successfully disabled!");
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }
}
