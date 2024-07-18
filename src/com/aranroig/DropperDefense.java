package com.aranroig;

import com.aranroig.commands.MainCommand;
import com.aranroig.commands.completers.MainCommandCompleter;
import com.aranroig.listeners.TntExplode;
import com.aranroig.listeners.VillagerKill;
import com.aranroig.logic.Game;
import org.bukkit.plugin.java.JavaPlugin;

public class DropperDefense extends JavaPlugin {

    public static DropperDefense instance;

    public Game currentGame = new Game();

    @Override
    public void onEnable() {
        instance = this;

        getCommand("dd").setExecutor(new MainCommand());
        getCommand("dd").setTabCompleter(new MainCommandCompleter());

        getServer().getPluginManager().registerEvents(new VillagerKill(), this);
        getServer().getPluginManager().registerEvents(new TntExplode(), this);

        System.out.println("Dropper defense enabled!");
    }

    @Override
    public void onDisable() {
        currentGame.Terminate();

        System.out.println("Dropper defense disabled!");
    }
}
