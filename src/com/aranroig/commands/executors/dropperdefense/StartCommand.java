package com.aranroig.commands.executors.dropperdefense;

import com.aranroig.DropperDefense;
import com.aranroig.commands.SubCommand;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements SubCommand {
    @Override
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player){

            Player p = (Player) commandSender;
            if(DropperDefense.instance.swapGame(DropperDefenseGame.instance)){
                p.sendMessage("Can't change game if one is already active!");
                return true;
            }
            DropperDefenseGame currentGame = DropperDefenseGame.instance;

            currentGame.Terminate();
            currentGame.SetHost(p);
            currentGame.Start();


            return true;
        }

        return false;
    }
}
