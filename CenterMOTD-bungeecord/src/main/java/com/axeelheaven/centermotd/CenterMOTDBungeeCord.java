package com.axeelheaven.centermotd;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;
import net.md_5.bungee.event.EventHandler;

import java.io.*;

public final class CenterMOTDBungeeCord extends Plugin implements Listener {

    private Configuration configuration;
    private String motd;

    @Override
    public void onEnable() {
        this.configuration = this.loadDefaultConfig("config");

        final String line1 = CenterMessage.centerMotD(this.configuration.getString("line-1")),
                line2 = CenterMessage.centerMotD(this.configuration.getString("line-2"));
        this.motd = line1 + line2;

        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();
        pluginManager.registerListener(this, this);
    }

    @EventHandler
    public void onEvent(final ProxyPingEvent event) {
        final ServerPing statusResponse = event.getResponse();
        statusResponse.setDescription(motd);
    }

    private Configuration loadDefaultConfig(final String name) {
        try {
            return ConfigurationProvider.getProvider(YamlConfiguration.class).load(new FileConfig(this).loadConfiguration(name + ".yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
