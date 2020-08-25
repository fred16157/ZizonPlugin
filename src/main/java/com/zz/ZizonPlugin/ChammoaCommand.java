package com.zz.ZizonPlugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BoundingBox;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ChammoaCommand implements CommandExecutor {

    Plugin plugin;
    final CooldownManager cooldownManager = new CooldownManager(5);
    LivingEntity target;
    PlayerEventHandler playerEventHandler;

    public ChammoaCommand(Plugin plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) { // 플레이어
            Player p = (Player)sender;
            Location attackPos = p.getLocation();

            long timeLeft = System.currentTimeMillis() - cooldownManager.getCooldown(p.getUniqueId());
            if(TimeUnit.MILLISECONDS.toSeconds(timeLeft) < cooldownManager.cooldownSeconds) {
                p.sendMessage("zl존 제작자의 로켓 참모아베기의 쿨타임이 " + Math.abs(TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldownManager.cooldownSeconds) + "초 남았습니다.");
                return true;
            }
            cooldownManager.setCooldown(p.getUniqueId(), System.currentTimeMillis());

            Bukkit.broadcastMessage(p.getDisplayName() + "님이 zl존 제작자의 로켓 참모아베기를 시전하였습니다!");

            // 시전 후 백스텝
            p.setVelocity(p.getLocation().getDirection().multiply(-2).setY(0));
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 250));
            p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 60, 250));

            // 참 모아베기
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                public void run() { // 백스텝 후 전방 스텝  delay : 20
                    p.setVelocity(p.getLocation().getDirection().multiply(1).setY(0));
                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() { // 파티클 소환  delay : 10
                            attackPos.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation(), 1);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                @Override
                                public void run() { // 데미지  delay : 5
                                    BoundingBox bb = BoundingBox.of(p.getEyeLocation(), 4, 5, 7);
                                    ArrayList<Entity> entities = (ArrayList) p.getWorld().getNearbyEntities(bb);
                                    for(Entity entity : entities){
                                        if(entity.equals(p)) return;
                                        else if(entity instanceof LivingEntity){
                                            LivingEntity le = (LivingEntity) entity;
                                            le.damage(5);
                                        }
                                    }
                                }
                            }, 5);
                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                public void run() { // 전방 점프  delay : 20
                                    p.setVelocity(p.getLocation().getDirection().multiply(1).setY(0.5));
                                    Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                        public void run() { // 파티클 소환  delay : 10
                                            attackPos.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation(), 1);
                                            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                                                @Override
                                                public void run() { // 데미지  delay : 5
                                                    BoundingBox bb = BoundingBox.of(p.getEyeLocation(), 4, 5, 7);
                                                    ArrayList<Entity> entities = (ArrayList) p.getWorld().getNearbyEntities(bb);
                                                    for(Entity entity : entities){
                                                        if(entity.equals(p)) return;
                                                        else if(entity instanceof LivingEntity){
                                                            LivingEntity le = (LivingEntity) entity;
                                                            le.damage(30);
                                                        }
                                                    }
                                                }
                                            }, 5);
                                        }
                                    }, 10);
                                }
                            }, 20);
                        }
                    }, 10);
                }
            }, 20);
        }
        else if(sender instanceof ConsoleCommandSender) { //콘솔
            ConsoleCommandSender c = (ConsoleCommandSender)sender;
            c.sendMessage("콘솔에서는 zl존 제작자의 로켓 참모아베기를 사용 할 수 없습니다.");
        }
        return true;
    }
}
