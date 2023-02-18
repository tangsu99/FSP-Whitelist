package cn.fsp.fspwhitelist;

import cn.fsp.fspwhitelist.subscribe.loginEvent;
import cn.fsp.fspwhitelist.whitelist.WhiteList;
import cn.fsp.fspwhitelist.usercache.UserCache;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.ResultedEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

@Plugin(
        id = "fsp-whitelist",
        name = "Fsp Whitelist",
        version = BuildConstants.VERSION,
        url = "https://github.com/tangsu99/FSP-Whitelist",
        authors = {"tangsu99"}
)
public class aFspWhitelist {
    @Inject
    public ProxyServer server;
    @Inject
    public CommandManager commandManager;
    @Inject
    public Injector injector;
    @Inject
    public Logger logger;
    public WhiteList whitelist;
    public UserCache userCache;
    public Config config;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        init();
        server.getEventManager().register(this, new loginEvent(this));
    }

    private void init() {
        this.config = new Config(logger);
        this.userCache = new UserCache();
        this.whitelist = new WhiteList(this);
    }

    @Subscribe
    public void onProxyReload(ProxyReloadEvent event) {
        reload();
    }

    public void reload() {

    }
}
