package com.aranroig.commands;

import com.aranroig.commands.executors.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {

//        if(commandSender instanceof Player){
//            Player p = (Player) commandSender;
//            p.sendMessage(String.join(", ", args));
//        }

        if (args.length < 1) return false;
        else {
            switch(args[0]){
                case "place": {
                    PlaceCommand cmd = new PlaceCommand();
                    return cmd.execute(commandSender, command, label, args);
                }
                case "listplaces": {
                    ListPlacesCommand cmd = new ListPlacesCommand();
                    return cmd.execute(commandSender, command, label, args);
                }
                case "clearplaces": {
                    ClearPlacesCommand cmd = new ClearPlacesCommand();
                    return cmd.execute(commandSender, command, label, args);
                }
                case "start": {
                    StartCommand cmd = new StartCommand();
                    return cmd.execute(commandSender, command, label, args);
                }
                case "spawnwave": {
                    SpawnWaveCommand cmd = new SpawnWaveCommand();
                    return cmd.execute(commandSender, command, label, args);
                }
                case "stop":
                    StopCommand cmd = new StopCommand();
                    return cmd.execute(commandSender, command, label, args);
                default:
                    break;
            }
        }
        return false;
    }
}
