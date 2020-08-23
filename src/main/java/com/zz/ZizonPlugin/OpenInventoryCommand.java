package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class OpenInventoryCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {//플래이어
            switch(args[0]) {
                case "tool":
                    OpenToolInventoryEvent otie = new OpenToolInventoryEvent((Player)sender);
                    Bukkit.getPluginManager().callEvent(otie);
                    break;
                default:
                    sender.sendMessage(args[0] + "는 유효한 인벤토리가 아닙니다.");
            }
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 인벤창을 열 수 없습니다.");
        }
        return true;
    }
}

class OpenToolInventoryEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    public HumanEntity ent;
    public OpenToolInventoryEvent(HumanEntity ent) {
        this.ent = ent;
    }

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}