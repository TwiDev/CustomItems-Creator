package net.twidev.itemsCreator;

import net.twidev.itemsCreator.command.ItemsCommand;
import net.twidev.itemsCreator.items.ItemsManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Creator extends JavaPlugin {

    private static Creator instance;

    private ItemsManager itemsManager;

    public void log(String message) {
        this.getLogger().info(message);
    }

    @Override
    public void onEnable() {

        instance = this;

        if(!setupAPI()) {
            log("Error, no custom items api found the plugin is disable, please download the api plugin at");
            log("https://www.spigotmc.org/resources/customitems-api-alpha.90363/");

            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        itemsManager = new ItemsManager(this);
        itemsManager.loadConfigItems();

        getCommand("items").setExecutor(new ItemsCommand());

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public boolean setupAPI() {
        return getServer().getPluginManager().getPlugin("CustomItems") != null;
    }

    public static Creator getInstance() {
        return instance;
    }

    public ItemsManager getItemsManager() {
        return itemsManager;
    }
}
