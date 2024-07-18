package com.aranroig.commands.completers;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class RedstoneWarCommandCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String label, String[] strings) {
        List<String> allExecutors = new ArrayList<String>() {
            {
                add("start");
                add("stop");
                add("place");
            }
        };

        List<String> placeExecutors = new ArrayList<String>() {
            {
                add("redteam");
                add("blueteam");
                add("redcore");
                add("bluecore");
                add("redinv");
                add("blueinv");
            }
        };

        if(strings.length == 1) {
            return filtered(strings[0], allExecutors);
        }

        if(strings.length == 2){
            if(strings[0].startsWith("place")){
                return filtered(strings[1], placeExecutors);
            }
        }

        return new ArrayList<String>();
    }

    private List<String> filtered(String query, List<String> all){
        List<String> filter = new ArrayList<String>();
        for (String s : all) {
            if (s.startsWith(query)) filter.add(s);
        }
        return filter;
    }
}
