package com.aranroig.commands.executors;

import com.aranroig.commands.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearPlacesCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            p.sendMessage("Clear place command");

            return true;
        }
        return false;
    }
}
