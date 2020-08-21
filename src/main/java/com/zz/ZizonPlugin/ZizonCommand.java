
package com.zz.ZizonPlugin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ZizonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ItemStack diamond = new ItemStack(Material.DIAMOND);

        if(sender instanceof Player) {//플래이어
            Player p = (Player)sender;
            p.sendMessage("player가 zl존 개쩌는 명령어를 실행하셨습니다");
            diamond.setAmount(1000);
            p.getInventory().addItem(diamond);
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("console이 zl존 개쩌는 명령어를 실행하셨습니다");
        }
        return true;
    }
}