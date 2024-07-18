package com.aranroig.listeners;

import com.aranroig.DropperDefense;
import com.aranroig.logic.games.dropperdefense.DropperDefenseGame;
import com.aranroig.logic.games.redstonewar.RedstoneWar;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class TntExplode implements Listener {
    @EventHandler
    public void OnTntBreakBlock(EntityExplodeEvent event){
        if(event.getLocation().getWorld() != RedstoneWar.instance.redCoreLocation.getWorld()) {
            List<Block> blockList = new ArrayList<>(event.blockList());
            event.blockList().clear();
            for (Block b : blockList) {
                if (b.getType() == Material.TNT) {
                    event.blockList().add(b);
                }
            }
        }
    }

    @EventHandler
    public void OnBlockBreak(BlockBreakEvent event){

        if(!(DropperDefense.instance.currentGame instanceof RedstoneWar)) return;

        World world = event.getBlock().getLocation().getWorld();

        int x = event.getBlock().getX();
        int y = event.getBlock().getY();
        int z = event.getBlock().getZ();
        World w = event.getBlock().getWorld();

        int bx = RedstoneWar.instance.blueCoreLocation.getX();
        int by = RedstoneWar.instance.blueCoreLocation.getY();
        int bz = RedstoneWar.instance.blueCoreLocation.getZ();

        int rx = RedstoneWar.instance.redCoreLocation.getX();
        int ry = RedstoneWar.instance.redCoreLocation.getY();
        int rz = RedstoneWar.instance.redCoreLocation.getZ();

        if(x == rx && y == ry && z == rz && w == world) RedstoneWar.instance.BlueWins();
        if(x == bx && y == by && z == bz && w == world) RedstoneWar.instance.RedWins();
    }
}
