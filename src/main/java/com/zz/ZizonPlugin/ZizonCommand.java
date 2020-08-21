package com.zz.ZizonPlugin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ZizonCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player)sender;
            p.sendMessage("zl존 개쩌는 명령어를 실행하셨습니다");
        }
        else if(sender instanceof ConsoleCommandSender) {
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("zl존 개쩌는 명령어를 실행하셨습니다");
        }
        return true;
    }
}
