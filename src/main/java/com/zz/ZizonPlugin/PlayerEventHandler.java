package com.zz.ZizonPlugin;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEventHandler implements Listener {
    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent pie) {
        Player player = pie.getPlayer();
        Action action = pie.getAction();
        ItemStack item = pie.getItem();
        if(action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK)) {
            if(item != null) {
                switch(item.getType()) {
                    case IRON_INGOT:
                        player.performCommand("zizonplugin:lightning");
                        break;
                    case FIRE_CHARGE:
                        player.performCommand("zizonplugin:explode");
                        break;
                }
            }
        }
    }
}