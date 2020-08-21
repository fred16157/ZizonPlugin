package com.zz.ZizonPlugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.concurrent.TimeUnit;

public class ChichiyaCommand implements CommandExecutor {
    final CooldownManager cooldownManager = new CooldownManager(5);

    private Vector getDir(double yaw, double dirY, double angleAdd)
    {
        double dirX = Math.cos(Math.toRadians(yaw + 90 + angleAdd));
        double dirZ = Math.sin(Math.toRadians(yaw + 90 + angleAdd));
        return new Vector(dirX, dirY, dirZ);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {//플래이어
            Player p = (Player)sender;
            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds)
            {
                p.sendMessage("zl존 개쩌는 곽일천천시의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }
            p.sendMessage(p.getDisplayName() + "가 zl존 개쩌는 곽일천천시를 시전하셨습니다");
            for(int i = -30; i < 30; i += 2) {
                cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());
                Arrow pr = p.launchProjectile(Arrow.class, getDir(p.getLocation().getYaw(), p.getLocation().getDirection().getY(), i));
                pr.addCustomEffect(new PotionEffect(PotionEffectType.GLOWING, 60, 1), true);
            }
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 곽일천천시를 시전할 수 없습니다.");
        }
        return true;
    }
}