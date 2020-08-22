package com.zz.ZizonPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    final int cooldownSeconds;
    public CooldownManager(int seconds) {
        cooldownSeconds = seconds;
    }

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public void setCooldown(UUID player, long time){
        cooldowns.put(player, time);
    }

    public Long getCooldown(UUID player){
        return cooldowns.getOrDefault(player, 0L);
    }
}