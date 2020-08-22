package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
        import org.bukkit.Material;
        import org.bukkit.Server;
        import org.bukkit.World;
        import org.bukkit.block.Block;
        import org.bukkit.command.Command;
        import org.bukkit.command.CommandExecutor;
        import org.bukkit.command.CommandSender;
        import org.bukkit.command.ConsoleCommandSender;
        import org.bukkit.entity.Entity;
        import org.bukkit.entity.Player;
        import org.bukkit.inventory.ItemStack;
        import org.bukkit.util.BoundingBox;

public class LightningCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {//플래이어
            Player p = (Player)sender;
            Block b = p.getTargetBlock(null, 50);
            if(b.isEmpty())
            {
                p.sendMessage("50미터 이상 떨어진 곳은 우르르쾅쾅할 수 없습니다.");
            }
            else {
                Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 개쩌는 우르르쾅쾅을 시전하셨습니다!");
                for(int i = 0; i < 10; i++)
                {
                    b.getWorld().createExplosion(b.getLocation(), 10, false, false);
                    b.getWorld().strikeLightning(b.getLocation());
                }
            }
        }
        else if(sender instanceof ConsoleCommandSender) {//콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 번개를 우르르쾅쾅할 수 없습니다.");
        }
        return true;
    }
}
