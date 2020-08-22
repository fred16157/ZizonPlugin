package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChammoaCommand implements CommandExecutor {

    Plugin plugin;
    final CooldownManager cooldownManager = new CooldownManager(5);
    LivingEntity target;
    PlayerEventHandler playerEventHandler;

    public ChammoaCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            Location attackPos = p.getLocation();

            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds) {
                p.sendMessage("zl존 제작자의 로켓 참모아베기의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }
            cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());

            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 모아베기를 시전하였습니다!");

            p.setVelocity(p.getLocation().getDirection().multiply(-2).setY(0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 250));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 250));

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    p.setVelocity(p.getLocation().getDirection().multiply(1).setY(0));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 2));
                }
            }, 20);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    attackPos.getWorld().createExplosion(attackPos, 0, false, false);
                }
            }, 28);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    p.setVelocity(p.getLocation().getDirection().multiply(1).setY(0.5));
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 15, 4));
                }
            }, 40);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    attackPos.getWorld().createExplosion(attackPos, 0, false, false);
                }
            }, 55);

        }
        else if(sender instanceof ConsoleCommandSender) { //콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 zl존 제작자의 로켓 참모아베기를 사용 할 수 없습니다.");
        }
        return true;
    }
}
