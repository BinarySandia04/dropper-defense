package com.aranroig.commands.executors;

import com.aranroig.DropperDefense;
import com.aranroig.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaceCommand implements SubCommand {

    @Override
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            switch(args[1]){
                case "villager":
                    DropperDefense.instance.currentGame.villagerLocation = p.getLocation();
                    p.sendMessage(ChatColor.GREEN + "Villager placed successfully!");

                    break;
                case "spawner":
                    DropperDefense.instance.currentGame.spawnLocations.add(p.getLocation());
                    p.sendMessage(ChatColor.GREEN + "Spawner placed successfully!");
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }
}
