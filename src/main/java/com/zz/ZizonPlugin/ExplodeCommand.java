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

import java.util.concurrent.TimeUnit;

public class ExplodeCommand implements CommandExecutor {
    final Plugin plugin;

    public ExplodeCommand(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {//플래이어
            Player p = (Player) sender;
            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 김순상의 초고출력을 시전하셨습니다!");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 100));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 5, 250));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BcScheduler(p,8, plugin), 10);
                }
            }, 20L * 3);//20L = 1 sec
        } else if (sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender) sender;
            c.sendMessage("콘솔에서는 김순상의 초고출력을 사용할 수 없습니다.");
        }
        return true;
    }
}

class BcScheduler implements Runnable {
    Player p;
    int cnt;
    Plugin plugin;
    BcScheduler(Player p, int i, Plugin plugin){
         this.p = p;
         cnt = i;
         this.plugin = plugin;
    }
    @Override
    public void run() {
        p.getWorld().createExplosion(p.getLocation().add(p.getLocation().getDirection().multiply(cnt)), 10, true, true);
        if(cnt <= 19)
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BcScheduler(p,cnt+=2,plugin), 4);
    }
}


