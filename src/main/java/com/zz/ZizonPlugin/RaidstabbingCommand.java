package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RaidstabbingCommand implements CommandExecutor {
    static ArrayList<UUID> playerAttr = new ArrayList<>();
    static ArrayList<UUID> flying_player = new ArrayList<>();
    final CooldownManager cooldownManager = new CooldownManager(1);
    final Plugin plugin;

    public RaidstabbingCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {//플래이어
            Player p = (Player) sender;
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds) {
                p.sendMessage("zl존 급습 찌르기의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 100, 250));
            p.setVelocity(p.getVelocity().setY(2.5));
            flying_player.add(p.getUniqueId());//날아올라

            cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());
            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 급습 찌르기를 시전하셨습니다!");


        } else if (sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender) sender;
            c.sendMessage("콘솔에서는 급습찌르기를 사용할 수 없습니다.");
        }
        return true;
    }
}

