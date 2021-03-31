package net.twidev.itemsCreator.items;

import net.twidev.CustomItems.ItemsBuilder;
import net.twidev.CustomItems.actions.InteractActions;
import net.twidev.CustomItems.recipe.CustomRecipe;
import net.twidev.itemsCreator.Creator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemsManager {

    private final Creator creator;

    private FileConfiguration fileConfiguration;

    private final HashMap<String, ItemsBuilder> items = new HashMap<>();

    public ItemsManager(Creator creator) {
        this.creator = creator;

        this.fileConfiguration = creator.getConfig();
    }

    public ItemsBuilder getItem(String s) {
        return items.get(s);
    }

    public boolean exitsItem(String s) {
        return items.containsKey(s);
    }

    public void loadConfigItems() {
        List<String> itemsConfig = new ArrayList<>(fileConfiguration.getConfigurationSection("items").getKeys(false));

        for(String s : itemsConfig) {
            String path =  "items." + s + ".";

            ItemsBuilder itemsBuilder = new ItemsBuilder(Material.valueOf(fileConfiguration.getString(path + "material")));

            itemsBuilder.setName(fileConfiguration.getString(path + "displayName"));

            if(fileConfiguration.contains(path + "lore"))
                itemsBuilder.setLore(fileConfiguration.getString("lore"));

            if(fileConfiguration.contains(path + "id")) {
                itemsBuilder.setCustomAmount(fileConfiguration.getInt(path + "id"));
            }else{
                itemsBuilder.setCustomAmount(1);
            }

            if(fileConfiguration.contains(path + "actions")) {
                itemsBuilder.setAction((player) -> {
                    for(String action : fileConfiguration.getStringList(path + "actions")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), action.replaceAll("%player%", player.getName()));
                    }
                });
            }

            if(fileConfiguration.contains(path + "interactActions")) {
                itemsBuilder.setInteractActions((player, action) -> {
                    for(String actionName : fileConfiguration.getStringList(path + "interactActions")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), actionName.replaceAll("%player%", player.getName()));
                    }
                });
            }

            if(fileConfiguration.contains(path + "customRecipe")) {
                CustomRecipe customRecipe = new CustomRecipe(itemsBuilder);

                for(String recipeName : fileConfiguration.getStringList(path + "customRecipe")) {
                    if(exitsItem(recipeName)) {
                        customRecipe.setItem(
                                Integer.parseInt(recipeName),
                                getItem(fileConfiguration.getString(path + "customRecipe." + recipeName))
                        );
                    }else{
                        customRecipe.setItem(
                                Integer.parseInt(recipeName),
                                new ItemsBuilder(Material.valueOf(fileConfiguration.getString(path + "customRecipe." + recipeName)))
                        );
                    }
                }

            }

            System.out.println("Add " + s + " custom items");

            items.put(s, itemsBuilder);
        }
    }

    public HashMap<String, ItemsBuilder> getItems() {
        return items;
    }

}
