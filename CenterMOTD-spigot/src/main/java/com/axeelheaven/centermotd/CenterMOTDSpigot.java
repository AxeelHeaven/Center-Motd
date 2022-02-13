package com.axeelheaven.centermotd;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class CenterMOTDSpigot extends JavaPlugin implements Listener {

    private boolean oneMore,
            maintenanceEnabled;
    private String motd, maintenance;
    private FileConfiguration settings;

    @Override
    public void onEnable() {
        final IConfig iConfig = IConfig.getInstance();
        iConfig.save("settings.yml");
        iConfig.load("settings.yml");
        this.settings = iConfig.get("settings.yml");

        this.oneMore = this.settings.getBoolean("default.one_more");
        this.maintenanceEnabled = this.settings.getBoolean("maintenance.enabled");

        final String line1 = CenterMessage.centerMotD(this.settings.getString("default.motd.1")),
                line2 = CenterMessage.centerMotD(this.settings.getString("default.motd.2"));
        this.motd = line1 + line2;

        final String mLine1 = CenterMessage.centerMotD(this.settings.getString("maintenance.motd.1")),
                mLine2 = CenterMessage.centerMotD(this.settings.getString("maintenance.motd.2"));
        this.maintenance = mLine1 + mLine2;

        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPing(final ServerListPingEvent event) {
        if (this.maintenanceEnabled)
            event.setMotd(this.maintenance);
        else
            event.setMotd(this.motd);

        if (this.oneMore)
            event.setMaxPlayers(Bukkit.getOnlinePlayers().size() + 1);
    }


}
