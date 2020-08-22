package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
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
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

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
                    case GOLDEN_SWORD:
                        player.performCommand("zizonplugin:helmetbreak");
                        break;
                    case DIAMOND_SWORD:
                        player.performCommand("zizonplugin:chammoa");
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
            else if(((Arrow)e).hasCustomEffect(PotionEffectType.SLOW) && target instanceof LivingEntity) //투구깨기 판정 화살
            {
                Player p = (Player)((Arrow) e).getShooter();
                LivingEntity le = (LivingEntity)target;
                e.remove();
                le.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 250));
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    p.sendMessage("기인찌르기 성공!");
                        p.teleport(le.getLocation());
                        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 250));
                        p.setVelocity(p.getVelocity().setY(2.0));
                        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                            HelmetBreakCommand.targets.put(p.getUniqueId(), le);
                        }, 2);
                }, 1);
            }
            else {
                e.remove();
            }
        }
    }

    @EventHandler
    public void onGround(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        Material m = p.getLocation().getBlock().getRelative(BlockFace.DOWN).getType();
        if(m != Material.AIR) {
            //땅에 있음
            if(HelmetBreakCommand.targets.containsKey(p.getUniqueId())) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new HelmetBreakScheduler(HelmetBreakCommand.targets.get(p.getUniqueId()), 0, plugin),4);
                HelmetBreakCommand.targets.remove(p.getUniqueId());
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
