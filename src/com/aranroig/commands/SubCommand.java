package com.aranroig.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface SubCommand {
    public boolean execute(CommandSender commandSender, Command command, String label, String[] args);
}
