package com.aranroig.commands.executors.dropperdefense;

import com.aranroig.commands.SubCommand;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
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
                    DropperDefenseGame.instance.villagerLocation = p.getLocation();
                    p.sendMessage(ChatColor.GREEN + "Villager placed successfully!");

                    break;
                case "spawner":
                    DropperDefenseGame.instance.spawnLocations.add(p.getLocation());
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
