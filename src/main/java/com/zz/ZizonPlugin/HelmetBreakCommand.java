package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class HelmetBreakCommand implements CommandExecutor {
    static Map<UUID, LivingEntity> targets = new HashMap<>();
    final CooldownManager cooldownManager = new CooldownManager(1);

    final Plugin plugin;

    public HelmetBreakCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    public Vector getVectorFromLocation(Location l) {
        return new Vector(l.getX(), l.getY(), l.getZ());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds)
            {
                p.sendMessage("zl존 개쩌는 투구깨기의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }
            p.sendMessage(p.getDisplayName() + "가 zl존 개쩌는 투구깨기를 시전하셨습니다");
            cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());
            Arrow a = p.launchProjectile(Arrow.class, p.getEyeLocation().getDirection().multiply(3));
            a.setGravity(false);
            a.setDamage(0);
            a.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 3600, 1), true);
        }
        else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 투구깨기를 시전할 수 없습니다.");
        }
        return true;
    }
}

class HelmetBreakScheduler implements Runnable {
    LivingEntity target;
    int cnt;
    Plugin plugin;
    HelmetBreakScheduler(LivingEntity target, int cnt, Plugin plugin){
        this.target = target;
        this.cnt = cnt;
        this.plugin = plugin;
    }
    @Override
    public void run() {
        if(target.isDead()) return;
        if(cnt <= 10)
            target.damage(5);
            target.getWorld().createExplosion(target.getEyeLocation(), 0, false, false);
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new HelmetBreakScheduler(target,++cnt,plugin), 1);
    }
}