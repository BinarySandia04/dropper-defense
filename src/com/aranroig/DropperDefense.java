package com.aranroig;

import com.aranroig.commands.DropperDefenserMainCommand;
import com.aranroig.commands.RedstoneWarCommand;
import com.aranroig.commands.completers.DropperDefenseCommandCompleter;
import com.aranroig.commands.completers.RedstoneWarCommandCompleter;
import com.aranroig.listeners.TntExplode;
import com.aranroig.listeners.VillagerKill;
import com.aranroig.logic.Game;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
import org.bukkit.plugin.java.JavaPlugin;

public class DropperDefense extends JavaPlugin {

    public static DropperDefense instance;

    public Game currentGame = new Game();

    @Override
    public void onEnable() {
        instance = this;

        getCommand("dd").setExecutor(new DropperDefenserMainCommand());
        getCommand("dd").setTabCompleter(new DropperDefenseCommandCompleter());

        getCommand("rw").setExecutor(new RedstoneWarCommand());
        getCommand("rw").setTabCompleter(new RedstoneWarCommandCompleter());

        getServer().getPluginManager().registerEvents(new VillagerKill(), this);
        getServer().getPluginManager().registerEvents(new TntExplode(), this);

        DropperDefenseGame.instance = new DropperDefenseGame();
        RedstoneWar.instance = new RedstoneWar();

        System.out.println("Dropper defense enabled!");
    }

    @Override
    public void onDisable() {
        currentGame.Terminate();

        System.out.println("Dropper defense disabled!");
    }

    public boolean swapGame(Game g){
        if(currentGame == g) return true;
        currentGame = g;
        return false;
    }
}
