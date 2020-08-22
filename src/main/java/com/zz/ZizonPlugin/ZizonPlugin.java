package com.zz.ZizonPlugin;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class ZizonPlugin extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("zl존개쩌는 플러그인이 로드되었습니다!");
        this.getCommand("zizon").setExecutor(new ZizonCommand());
        this.getCommand("lightning").setExecutor(new LightningCommand());
        this.getCommand("explode").setExecutor(new ExplodeCommand(this));
        this.getCommand("chichiya").setExecutor(new ChichiyaCommand());
        this.getCommand("helmetbreak").setExecutor(new HelmetBreakCommand(this));
        this.getCommand("ganpa").setExecutor(new GanpaCommand(this));
        this.getCommand("chammoa").setExecutor(new ChammoaCommand(this));
        Listener eventListener = new PlayerEventHandler(this);
        getServer().getPluginManager().registerEvents(eventListener, this);
    }

    @Override
    public void onDisable() {
        getLogger().info("zl존개쩌는 플러그인이 종료되었습니다!");
    }
}
