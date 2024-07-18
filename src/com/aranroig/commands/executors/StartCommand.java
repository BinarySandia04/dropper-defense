package com.aranroig.commands.executors;

import com.aranroig.DropperDefense;
import com.aranroig.commands.SubCommand;
import com.aranroig.logic.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){

            Game currentGame = DropperDefense.instance.currentGame;
            Player p = (Player) commandSender;

            currentGame.Terminate();
            currentGame.SetHost(p);
            currentGame.Start();


            return true;
        }

        return false;
    }
}
