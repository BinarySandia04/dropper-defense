package com.aranroig.logic;

import com.aranroig.DropperDefense;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Wave implements Runnable {

    public int difficulty;
    public int numWave;

    private int spawnedMonsters;

    private ArrayList<Location> spawners;
    public ArrayList<Mob> monsters;
    private Game game;

    private Random random = new Random();

    public Wave(ArrayList<Location> spawners, int numWave, int difficulty){
        this.numWave = numWave;
        this.spawners = spawners;
        this.difficulty = difficulty;
        this.monsters = new ArrayList<Mob>();
        this.game = DropperDefense.instance.currentGame;

        this.spawnedMonsters = 0;
    }

    private Mob SpawnMonster(Location loc){
        Map<Integer, EntityType> monsterDict = new HashMap<Integer, EntityType>(){
            {
                put(1, EntityType.ZOMBIE);
                put(2, EntityType.HUSK);
                put(4, EntityType.DROWNED);
                put(6, EntityType.SKELETON);
                put(8, EntityType.SPIDER);
                put(10, EntityType.CREEPER);
                put(12, EntityType.BLAZE);
                put(15, EntityType.WITHER_SKELETON);
                put(18, EntityType.ENDERMAN);
                put(20, EntityType.ZOGLIN);
                put(22, EntityType.HOGLIN);
                put(25, EntityType.VINDICATOR);
                put(30, EntityType.RAVAGER);
                put(35, EntityType.WARDEN);
            }
        };

        Map<EntityType, Integer> monsterCost = new HashMap<EntityType, Integer>(){
            {
                put(EntityType.ZOMBIE, 1);
                put(EntityType.HUSK, 1);
                put(EntityType.DROWNED, 1);
                put(EntityType.SPIDER, 2);
                put(EntityType.CREEPER, 3);
                put(EntityType.SKELETON, 2);
                put(EntityType.BLAZE, 4);
                put(EntityType.WITHER_SKELETON, 5);
                put(EntityType.ENDERMAN, 7);
                put(EntityType.ZOGLIN, 4);
                put(EntityType.HOGLIN, 8);
                put(EntityType.VINDICATOR, 8);
                put(EntityType.RAVAGER, 6);
                put(EntityType.WARDEN, 12);
            }
        };

        Map<EntityType, String> monsterNames = new HashMap<EntityType, String>(){
            {
                put(EntityType.ZOMBIE, "Zombie informàtic");
                put(EntityType.HUSK, "Zombie virus");
                put(EntityType.DROWNED, "Zombie blau");
                put(EntityType.SPIDER, "Rastreador web");
                put(EntityType.CREEPER, "Creeper verd");
                put(EntityType.SKELETON, "Esquelet gris fosc");
                put(EntityType.BLAZE, "Blaze hackejat");
                put(EntityType.WITHER_SKELETON, "Esquelet blanc");
                put(EntityType.ENDERMAN, "Starterman");
                put(EntityType.ZOGLIN, "Hoglin");
                put(EntityType.HOGLIN, "Zoglin");
                put(EntityType.VINDICATOR, "Revindicador");
                put(EntityType.RAVAGER, "Warden");
                put(EntityType.WARDEN, "Ravager");
            }
        };

        ArrayList<Integer> validKeys = new ArrayList<>();
        int chosen = (int) Math.exp(random.nextDouble(Math.log1p(numWave)));

        for(int i : monsterDict.keySet()){
            if(i <= chosen) validKeys.add(i);
        }

        Mob result = null;
        boolean spawned = false;
        String name = "Zombie normal";
        while(validKeys.size() > 0){
            int spawnTry = validKeys.get(validKeys.size() - 1);
            if(monsterCost.get(monsterDict.get(spawnTry)) + spawnedMonsters < difficulty) {
                result = (Mob) loc.getWorld().spawnEntity(loc, monsterDict.get(spawnTry));
                name = monsterNames.get(monsterDict.get(spawnTry));
                spawned = true;
                spawnedMonsters += monsterCost.get(monsterDict.get(spawnTry));
                break;
            }
            validKeys.remove(validKeys.size() - 1);
        }
        if(!spawned){
            result = (Mob) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
            spawnedMonsters += 1;
        }

        result.setCustomName(ChatColor.RED + "" + ChatColor.BOLD + name);
        result.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(500);

        double newMaxHealth = result.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * (1.0f + (numWave / 15.0));
        result.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(newMaxHealth);
        result.setHealth(newMaxHealth);
        result.setCustomNameVisible(true);
        result.setTarget(game.villager);

        return result;
    }

    private void AddPoints(Mob mob){
        Map<EntityType, Integer> monsterPoints = new HashMap<EntityType, Integer>(){
            {
                put(EntityType.ZOMBIE, 10);
                put(EntityType.HUSK, 15);
                put(EntityType.DROWNED, 20);
                put(EntityType.SPIDER, 30);
                put(EntityType.CREEPER, 40);
                put(EntityType.SKELETON, 20);
                put(EntityType.BLAZE, 50);
                put(EntityType.WITHER_SKELETON, 75);
                put(EntityType.ENDERMAN, 100);
                put(EntityType.ZOGLIN, 70);
                put(EntityType.HOGLIN, 250);
                put(EntityType.VINDICATOR, 150);
                put(EntityType.RAVAGER, 250);
                put(EntityType.WARDEN, 1000);
            }
        };

        game.points += monsterPoints.get(mob.getType());


    }

    @Override
    public void run() {
        game.broadcast(ChatColor.YELLOW + "Ronda " + numWave);

        new BukkitRunnable(){

            int ticksElapsed = 0;

            @Override
            public void run() {

                if(ticksElapsed % ( (400 + numWave * 20) / difficulty) == 0) {
                    if (spawnedMonsters <= difficulty) {
                        Location loc = spawners.get(random.nextInt(spawners.size()));
                        monsters.add(SpawnMonster(loc));
                        game.remainingMonsters = monsters.size();
                        game.SendStats();
                    }
                }

                for(Mob e : monsters){
                    if(e.getHealth() <= 0){
                        monsters.remove(e);
                        AddPoints(e);

                        game.remainingMonsters = monsters.size();
                        game.SendStats();
                    }
                    e.setTarget(game.villager);
                }

                game.villager.teleport(game.villagerLocation);

                if(monsters.size() == 0 && spawnedMonsters > difficulty) {
                    cancel();
                    game.StartNextWave();
                    game.SendStats();
                }

                if(!game.started){
                    cancel();
                    for(Entity e : monsters){
                        e.remove();
                    }
                }
                ticksElapsed++;
            }
        }.runTaskTimer(DropperDefense.instance, 60, 1);

    }


}
