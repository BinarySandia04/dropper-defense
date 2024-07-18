package com.aranroig.listeners;

import com.aranroig.DropperDefense;
import com.aranroig.logic.Game;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
import org.bukkit.ChatColor;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class VillagerKill implements Listener {

    @EventHandler
    public void OnVillagerDeath(EntityDeathEvent event){
        Game game = DropperDefense.instance.currentGame;
        if(game instanceof DropperDefenseGame) {
            DropperDefenseGame g = (DropperDefenseGame) game;
            if (event.getEntity() == g.villager) {
                g.broadcast(ChatColor.RED + "El villager ha mort!");
                g.villagerBossbar.setProgress(0);

                g.Terminate();
            }
        }
    }

    @EventHandler
    public void OnVillagerHurt(EntityDamageEvent event){

        Game game = DropperDefense.instance.currentGame;
        if(game instanceof DropperDefenseGame) {
            DropperDefenseGame g = (DropperDefenseGame) game;
            if (event.getEntity() == g.villager) {
                LivingEntity villagerLiving = (LivingEntity) event.getEntity();
                g.villagerBossbar.setProgress((float) villagerLiving.getHealth() / g.villagerMaxHealth);
            }
        }
    }

    @EventHandler
    public void PlayerHit(EntityDamageByEntityEvent event){
        Game game = DropperDefense.instance.currentGame;
        if(game instanceof DropperDefenseGame) {
            DropperDefenseGame g = (DropperDefenseGame) game;
            if (event.getDamager() instanceof Player) {
                if (g.currentWave.monsters.contains(event.getEntity())) {
                    event.setDamage(3);
                }
            }

            if (event.getDamager() instanceof IronGolem) {
                if (g.currentWave.monsters.contains(event.getEntity())) {
                    event.setDamage(1);
                }
            }
        }

        if(game instanceof RedstoneWar){
            RedstoneWar g = (RedstoneWar) game;
            if(event.getDamager() instanceof Player){
                Player attacker = (Player) event.getDamager();
                if(event.getEntity() instanceof Player){
                    Player victim = (Player) event.getEntity();
                    if( (g.redTeam.contains(attacker) && g.redTeam.contains(victim))
                            || (g.blueTeam.contains(attacker) && g.blueTeam.contains(victim))){
                        event.setDamage(0);
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    @EventHandler
    public void PlayerDeath(PlayerDeathEvent event){
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        Game game = DropperDefense.instance.currentGame;
        if(game instanceof RedstoneWar) {
            RedstoneWar g = (RedstoneWar) game;

            if(g.redTeam.contains(killer) && g.blueTeam.contains(victim)){
                g.redTeamKills += 1;
            }

            if(g.blueTeam.contains(killer) && g.redTeam.contains(victim)){
                g.blueTeamKills += 1;
            }
        }
    }
}
