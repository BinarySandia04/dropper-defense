package com.aranroig.logic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Game {

    public boolean started;

    public Player host = null;

    public Game(){
        started = false;
    }

    public void Start() {
        started = true;
    }

    public void Terminate() {
        started = false;
    }


    public void SetHost(Player host){
        this.host = host;
    }

    protected ArrayList<Player> GetOtherPlayers(){
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
        if(host != null) host.sendMessage(s);
    }

}
