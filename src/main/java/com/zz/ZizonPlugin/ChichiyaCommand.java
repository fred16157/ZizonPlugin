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

public class ChichiyaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {//플래이어
            Player p = (Player)sender;
            p.sendMessage(p.getDisplayName() + "가 zl존 개쩌는 곽일천천시를 시전하셨습니다");
            for(int i = -15; i <= 15; i ++)
            {
                Projectile pr = p.launchProjectile(Arrow.class, p.getLocation().getDirection().rotateAroundY(i));
            }
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 곽일천천시를 시전할 수 없습니다.");
        }
        return true;
    }
}