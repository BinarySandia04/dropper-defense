package com.aranroig.logic;

import com.aranroig.DropperDefense;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;

public class Game {

    public Location villagerLocation = null;
    public ArrayList<Location> spawnLocations = new ArrayList<Location>();
    public Player host;
    public boolean started;

    public Villager villager;

    public Wave currentWave;
    public int waveNumber;

    public int villagerMaxHealth = 200;

    public BossBar villagerBossbar;

    public int points = 0;
    public int mobsLeft = 0;

    public int remainingMonsters = 0;

    private Objective objective;

    public Game(){
        this.started = false;
    }

    public void SetHost(Player host){
        this.host = host;
    }

    public void Start(){
        if(villagerLocation == null){
            host.sendMessage(ChatColor.RED + "Can't start game without villager pos setted!");
            return;
        }

        villager = (Villager) villagerLocation.getWorld().spawnEntity(villagerLocation, EntityType.VILLAGER);
        villager.setAI(false);
        villager.setCustomName(ChatColor.GREEN + "" + ChatColor.BOLD + "Agustí");
        villager.setCustomNameVisible(true);
        villager.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(villagerMaxHealth);
        villager.setHealth(villagerMaxHealth);
        ConfigureVillagerBossbar();

        host.sendMessage(ChatColor.GRAY + "Game started");
        playersSendMessage(ChatColor.YELLOW + "El dropper defense ha començat!");

        SendStats();

        started = true;

        waveNumber = 1;

        currentWave = new Wave(spawnLocations, waveNumber, 3);

        broadcast("La partida començarà en 10 segons!");

        Bukkit.getScheduler().runTaskLater(DropperDefense.instance, currentWave, 10 * 20);

        for(Player p: GetOtherPlayers()){
            p.setGameMode(GameMode.ADVENTURE);
            p.teleport(villagerLocation);
        }
    }

    public void Terminate(){
        if(!started) return;
        if(villager != null) villager.remove(); villager = null;
        if(host != null) host.sendMessage(ChatColor.GRAY + "Game ended");

        playersSendMessage(ChatColor.RED + "El dropper defense ha acabat!!");

        if(currentWave != null){
            for(Entity e : currentWave.monsters) e.remove();
        }

        villagerBossbar.removeAll();

        waveNumber = 0;

        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard board = scoreboardManager.getNewScoreboard();
        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(board);
        }

        started = false;
        points = 0;
    }

    public void StartNextWave(){
        waveNumber += 1;

        currentWave = new Wave(spawnLocations, waveNumber, 5 + waveNumber * 5);
        Bukkit.getScheduler().runTaskLater(DropperDefense.instance, currentWave, 50);
    }

    private ArrayList<Player> GetOtherPlayers(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : Bukkit.getOnlinePlayers()){
            if(p != host) players.add(p);
        }
        return players;
    }

    public void playersSendMessage(String s){
        for(Player p : GetOtherPlayers()){
            p.sendMessage(s);
        }
    }

    public void broadcast(String s){
        for(Player p : GetOtherPlayers()){
            p.sendMessage(s);
        }
        host.sendMessage(s);
    }

    private void ConfigureVillagerBossbar(){
        villagerBossbar = Bukkit.createBossBar(ChatColor.GREEN + "" + ChatColor.BOLD + "Agustí", BarColor.GREEN, BarStyle.SEGMENTED_20);
        for(Player p : Bukkit.getOnlinePlayers()){
            villagerBossbar.addPlayer(p);
        }
    }

    public void SendStats() {
        ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard board = scoreboardManager.getNewScoreboard();
        objective = board.registerNewObjective("dropperDefense", "dummy", ChatColor.GREEN + "" + ChatColor.BOLD + "Dropper Defense");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score sc1 = objective.getScore(GetCenter(22, "Ronda " + waveNumber) + "Ronda " + ChatColor.RED + waveNumber);
        sc1.setScore(3);
        Score sc2 = objective.getScore(GetCenter(22, "Punts: " + points) + "Punts: " + ChatColor.GOLD + points);
        sc2.setScore(2);

        for(Player p : Bukkit.getOnlinePlayers()){
            p.setScoreboard(board);
        }
    }

    private String GetCenter(int n, String s){
        return " ".repeat((n - s.length()) / 2);
    }

}
