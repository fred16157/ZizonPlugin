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

import java.util.concurrent.TimeUnit;

public class GanpaCommand implements CommandExecutor {
    final CooldownManager cooldownManager = new CooldownManager(5);
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
        } else if (sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender) sender;
            c.sendMessage("콘솔에서는 공간파베기를 사용할 수 없습니다.");
        }
        return true;
    }
}