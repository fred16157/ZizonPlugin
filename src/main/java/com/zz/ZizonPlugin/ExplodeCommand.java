package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import java.util.concurrent.TimeUnit;
public class ExplodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {//플래이어
            Player p = (Player)sender;
            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 김순상의 초고출력을 시전하셨습니다!");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 5, 100));
            for(int i = 5; i < 15; i++) {
                p.getWorld().createExplosion(p.getLocation().add(p.getLocation().getDirection().multiply(i)), 10, true, true);
            }
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 김순상의 초고출력을 사용할 수 없습니다.");
        }
        return true;
    }
}
