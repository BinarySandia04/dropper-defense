package com.aranroig.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

public class TntExplode implements Listener {
    @EventHandler
    public void OnTntBreakBlock(EntityExplodeEvent event){
        List<Block> blockList = new ArrayList<>(event.blockList());
        event.blockList().clear();
        for(Block b : blockList){
            if(b.getType() == Material.TNT){
                event.blockList().add(b);
            }
        }
    }
}
