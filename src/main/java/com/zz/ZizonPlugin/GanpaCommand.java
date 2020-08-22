package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class GanpaCommand implements CommandExecutor {
    static ArrayList<UUID> playerAttr = new ArrayList<>();
    final CooldownManager cooldownManager = new CooldownManager(3);
    final Plugin plugin;

    public GanpaCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {//플래이어
            Player p = (Player) sender;
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds) {
                p.sendMessage("zl존 개쩌는 공간파베기의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }

            cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());
            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 공간파베기를 시전하셨습니다!");
            p.setVelocity(p.getLocation().getDirection().multiply(-5).setY(0));

            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 1, 250));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 1, 250));

            p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 15, 250));
            playerAttr.add(p.getUniqueId());

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {public void run() {playerAttr.remove(p.getUniqueId());}}, 15L);

            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    p.setVelocity(p.getLocation().getDirection().multiply(5).setY(0));
                }
            }, 20L * 1);


        } else if (sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender) sender;
            c.sendMessage("콘솔에서는 공간파베기를 사용할 수 없습니다.");
        }
        return true;
    }
}
