package com.aranroig.listeners;

import com.aranroig.DropperDefense;
import com.aranroig.logic.Game;
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

public class VillagerKill implements Listener {

    @EventHandler
    public void OnVillagerDeath(EntityDeathEvent event){
        if(event.getEntity() == DropperDefense.instance.currentGame.villager){
            Game g = DropperDefense.instance.currentGame;
            g.broadcast(ChatColor.RED + "El villager ha mort!");
            g.villagerBossbar.setProgress(0);

            g.Terminate();
        }
    }

    @EventHandler
    public void OnVillagerHurt(EntityDamageEvent event){
        if(event.getEntity() == DropperDefense.instance.currentGame.villager){
            Game g = DropperDefense.instance.currentGame;
            LivingEntity villagerLiving = (LivingEntity) event.getEntity();
            g.villagerBossbar.setProgress((float) villagerLiving.getHealth() / g.villagerMaxHealth);
        }
    }

    @EventHandler
    public void PlayerHit(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            if(DropperDefense.instance.currentGame.currentWave.monsters.contains(event.getEntity())){
                event.setDamage(3);
            }
        }

        if(event.getDamager() instanceof IronGolem){
            if(DropperDefense.instance.currentGame.currentWave.monsters.contains(event.getEntity())){
                event.setDamage(1);
            }
        }
    }
}
