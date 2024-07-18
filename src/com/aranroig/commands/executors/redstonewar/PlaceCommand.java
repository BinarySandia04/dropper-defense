package com.aranroig.commands.executors.redstonewar;

import com.aranroig.commands.SubCommand;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
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
                case "redcore":
                    RedstoneWar.instance.redCoreLocation = p.getLocation().getBlock();
                    p.sendMessage(ChatColor.GREEN + "Red core location placed at X: " + p.getLocation().getBlockX()
                            + " Y: " + p.getLocation().getBlockY() + " Z: " + p.getLocation().getBlockZ());
                    break;
                case "bluecore":
                    RedstoneWar.instance.blueCoreLocation = p.getLocation().getBlock();
                    p.sendMessage(ChatColor.GREEN + "Blue core location placed at X: " + p.getLocation().getBlockX()
                            + " Y: " + p.getLocation().getBlockY() + " Z: " + p.getLocation().getBlockZ());
                    break;
                case "redteam":
                    RedstoneWar.instance.redTeamSpawn = p.getLocation();
                    p.sendMessage("Red team spawn placed successfully!");
                    break;
                case "blueteam":
                    RedstoneWar.instance.blueTeamSpawn = p.getLocation();
                    p.sendMessage("Blue team spawn placed successfully!");
                    break;
                case "redinv":
                    RedstoneWar.instance.redInventory = p.getInventory().getContents();
                    p.sendMessage("Red team inventory updated successfully!");
                    break;
                case "blueinv":
                    RedstoneWar.instance.blueInventory = p.getInventory().getContents();
                    p.sendMessage("Blur team inventory updated successfully!");
                    break;
                default:
                    break;
            }

            return true;
        }
        return false;
    }
}
