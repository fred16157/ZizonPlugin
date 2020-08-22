package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import static com.zz.ZizonPlugin.GanpaCommand.playerAttr;

public class PlayerEventHandler implements Listener {
    final Plugin plugin;

    public PlayerEventHandler(Plugin plugin) {
        this.plugin = plugin;
    }

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
                    case BAMBOO:
                        player.performCommand("zizonplugin:chichiya");
                        break;
                    case WOODEN_SWORD:
                        player.performCommand("zizonplugin:ganpa");
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent phe) {
        Entity e = phe.getEntity();
        Entity target = phe.getHitEntity();
        if(e instanceof Arrow)
        {
            if(((Arrow)e).hasCustomEffect(PotionEffectType.GLOWING))    //천천시 화살
            {
                e.getWorld().createExplosion(e.getLocation(), 10, true, true);
                e.remove();
            }
            else if(((Arrow)e).hasCustomEffect(PotionEffectType.INVISIBILITY) && target instanceof LivingEntity) //투구깨기 판정 화살
            {
                Player p = (Player)((Arrow) e).getShooter();
                LivingEntity le = (LivingEntity)target;
                e.remove();
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 250));

            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof  Player) {
            Player p = (Player) e.getEntity();
            if(playerAttr.indexOf(p.getUniqueId()) != -1){
                Bukkit.broadcastMessage("★간★파★성★공★");
                p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 30, 250));
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 4, 2));
            }
        }
    }
}
