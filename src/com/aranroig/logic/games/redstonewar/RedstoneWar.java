package com.aranroig.logic.games.redstonewar;

import com.aranroig.DropperDefense;
import com.aranroig.logic.Game;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class RedstoneWar extends Game {
    public static RedstoneWar instance;

    public Location redTeamSpawn = null;
    public Location blueTeamSpawn = null;
    public Block redCoreLocation = null;
    public Block blueCoreLocation = null;

    public ItemStack[] redInventory = null;
    public ItemStack[] blueInventory = null;

    public List<Player> redTeam;
    public List<Player> blueTeam;

    private Objective objective;

    public int redTeamKills = 0;
    public int blueTeamKills = 0;

    public void Start(){
        broadcast("Game started");

        redTeam = new ArrayList<Player>();
        blueTeam = new ArrayList<Player>();

        AssignTeams();
        // Checks

        if(redTeamSpawn == null){
            host.sendMessage(ChatColor.RED + "Can't start game: redTeamSpawn is null");
            DropperDefense.instance.currentGame = null;
            return;
        }
        if(blueTeamSpawn == null){
            host.sendMessage(ChatColor.RED + "Can't start game: blueTeamSpawn is null");
            DropperDefense.instance.currentGame = null;
            return;
        }
        if(redCoreLocation == null){
            host.sendMessage(ChatColor.RED + "Can't start game: redCoreLocation is null");
            DropperDefense.instance.currentGame = null;
            return;
        }
        if(blueCoreLocation == null){
            host.sendMessage(ChatColor.RED + "Can't start game: blueCoreLocation is null");
            DropperDefense.instance.currentGame = null;
            return;
        }
        if(redInventory == null){
            host.sendMessage(ChatColor.RED + "Can't start game: redInventory is null");
            DropperDefense.instance.currentGame = null;
            return;
        }
        if(blueInventory == null){
            host.sendMessage(ChatColor.RED + "Can't start game: blueInventory is null");
            DropperDefense.instance.currentGame = null;
            return;
        }


        super.Start();

        PreparePlayers();
        PrepareCores();

        new BukkitRunnable(){

            int ticksElapsed = 0;

            RedstoneWar game = RedstoneWar.instance;

            @Override
            public void run() {
                ticksElapsed++;

                if(!game.started){
                    cancel();
                    return;
                }

                for(Player p : Bukkit.getOnlinePlayers()){
                    SendStats(p);
                }
            }
        }.runTaskTimer(DropperDefense.instance, 60, 1);
    }

    public void Terminate(){
        broadcast("Game terminated");
        if(!started) return;

        redTeam = new ArrayList<Player>();
        blueTeam = new ArrayList<Player>();

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setDisplayName(p.getName());
            p.setGameMode(GameMode.CREATIVE);

            ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard board = scoreboardManager.getNewScoreboard();
            p.setScoreboard(board);
        }

        super.Terminate();
    }

    private void AssignTeams(){
        Random random = new Random();
        int maxTeamSize = (Bukkit.getOnlinePlayers().size() / 2) + 1;
        for(Player p : Bukkit.getOnlinePlayers()){
            int putTo = random.nextInt(2);
            if(putTo == 0){
                if(redTeam.size() < maxTeamSize) redTeam.add(p);
                else blueTeam.add(p);
            } else {
                if(blueTeam.size() < maxTeamSize) blueTeam.add(p);
                else redTeam.add(p);
            }
        }
    }

    private void SendStats(Player player){
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard board = scoreboardManager.getNewScoreboard();
        objective = board.registerNewObjective("dropperDefense", "dummy", ChatColor.RED + "" + ChatColor.BOLD + "Redstone Wars");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        if(blueTeam.contains(player)) {
            objective.getScore("Ets " + ChatColor.BLUE + "blau").setScore(6);
        }
        if(redTeam.contains(player)) {
            objective.getScore("Ets " + ChatColor.RED + "vermell").setScore(6);
        }

        objective.getScore(" ").setScore(3);
        objective.getScore(ChatColor.BOLD + "" + ChatColor.BLUE + "Equip blau").setScore(5);
        objective.getScore("Punts: " + ChatColor.BLUE + blueTeamKills * 10).setScore(4);
        objective.getScore("  ").setScore(3);
        objective.getScore(ChatColor.BOLD + "" + ChatColor.RED + "Equip vermell").setScore(2);
        objective.getScore("Punts: " + ChatColor.RED + redTeamKills * 10).setScore(1);

        player.setScoreboard(board);
    }

    private void PreparePlayers(){
        for(Player p : redTeam){
            p.teleport(redTeamSpawn);
            p.setGameMode(GameMode.SURVIVAL);
            SendInventory(p, redInventory);
            p.setDisplayName(ChatColor.RED + p.getName());
        }

        for(Player p : blueTeam){
            p.teleport(blueTeamSpawn);
            p.setGameMode(GameMode.SURVIVAL);
            SendInventory(p, blueInventory);
            p.setDisplayName(ChatColor.BLUE + p.getName());
        }
    }

    private void PrepareCores(){
        redCoreLocation.setType(Material.RED_WOOL);
        blueCoreLocation.setType(Material.BLUE_WOOL);
    }

    private void SendInventory(Player p, ItemStack[] inv){
        p.getInventory().clear();
        p.getInventory().setContents(inv);
    }

    public void RedWins(){
        if(!started) return;

        broadcast(ChatColor.RED + "Ha guanyat l'equip vermell!");
        Terminate();
    }

    public void BlueWins(){
        if(!started) return;

        broadcast(ChatColor.BLUE + "Ha guanyat l'equip blau!");
        Terminate();
    }

}
