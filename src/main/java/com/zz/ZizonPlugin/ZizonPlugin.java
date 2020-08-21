package com.zz.ZizonPlugin;

import org.bukkit.plugin.java.JavaPlugin;

public class ZizonPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("zl존개쩌는 플러그인이 로드되었습니다!");
        this.getCommand("zizon").setExecutor(new ZizonCommand());
        this.getCommand("lightning").setExecutor(new LightningCommand());
    }

    @Override
    public void onDisable() {
        getLogger().info("zl존개쩌는 플러그인이 종료되었습니다!");
    }
}
