package net.twidev.itemsCreator.command;

import net.twidev.itemsCreator.Creator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ItemsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Creator creator = Creator.getInstance();

        if(args.length > 0) {
            switch (args[0]) {
                case "list":
                    StringBuilder stringBuilder = new StringBuilder();

                    creator.getItemsManager().getItems().forEach((s, itemsBuilder) -> stringBuilder.append(s));

                    sender.sendMessage(stringBuilder.toString());
                    break;
                case "reload":
                    Creator.getInstance().saveConfig();
                    Creator.getInstance().reloadConfig();
                    break;
            }
        }

        if(sender instanceof Player) {
            if(args.length > 1) {

                switch (args[0]) {
                    case "give":
                        if(creator.getItemsManager().exitsItem(args[1])) {
                            ((Player) sender).getInventory().addItem(creator.getItemsManager().getItem(args[1]));

                            sender.sendMessage("Give " + args[1] + " item");
                        }else{
                            sender.sendMessage("Error " + args[1] + " item doesn't exist");
                        }
                        break;
                }
            }
        }

        return false;
    }
}
