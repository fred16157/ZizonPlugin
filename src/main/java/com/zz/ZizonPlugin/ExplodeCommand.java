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
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 3, 100));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 20 * 3, 250));
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() {
                    Vector player_direction = p.getLocation().getDirection();
                    p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 1, 250));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BcScheduler(p,8, player_direction, plugin),0);
                }
            }, 20L * 2);//20L = 1 sec
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
    Vector player_direction;
    Vector save_pdir;
    BcScheduler(Player p, int cnt,Vector player_direction, Plugin plugin){
         this.p = p;
         this.cnt = cnt;
         this.plugin = plugin;
         this.player_direction = player_direction;
         save_pdir = player_direction.clone();
    }
    @Override
    public void run() {
        Bukkit.broadcastMessage(cnt + " " + player_direction);//멀티플이 곱해버린다고
        p.getWorld().createExplosion(p.getLocation().add(save_pdir.multiply(cnt)), 7, true, true);
        if(cnt <= 14)
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new BcScheduler(p,cnt+=2,player_direction,plugin), 4);
    }
}