package com.aranroig.commands.executors.redstonewar;

import com.aranroig.DropperDefense;
import com.aranroig.commands.SubCommand;
import com.aranroig.logic.Game;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StopCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){
            Game g = DropperDefense.instance.currentGame;
            Player p = (Player) commandSender;

            g.Terminate();
            p.sendMessage("Game stopped");
            return true;
        }
        return false;
    }
}
