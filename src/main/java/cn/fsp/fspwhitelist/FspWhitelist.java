package cn.fsp.fspwhitelist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
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
public class FspWhitelist {
    @Inject
    public ProxyServer server;
    @Inject
    public CommandManager commandManager;
    @Inject
    public Injector injector;
    @Inject
    public Logger logger;
    public Whitelist whitelist;
    public UserCache userCache;
    public Config config;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        init();
    }

    @Subscribe
    public void onProxyReload(ProxyReloadEvent event) {
        reload();
    }

    public void init() {
        show();
        config = new Config(logger);
        whitelist = new Whitelist(logger);
        userCache = whitelist.getUserCache();
        commandManager.register(injector.getInstance(CmdBuilder.class).register(this));
        logger.info("done!");
        configEnable();
    }

    public void reload() {
        config = new Config(logger);
        whitelist = new Whitelist(logger);
        userCache = whitelist.getUserCache();
        commandManager.register(injector.getInstance(CmdBuilder.class).register(this));
        logger.info("Plugin reload done!");
    }

    @Subscribe
    public void onPreLoginEvent(PreLoginEvent event) {
        if (!config.getEnable()) {
            return;
        }
        // 判断有无玩家的数据缓存
        if (userCache.playerInsideUserCache(event.getUsername())) {
            // 不在白名单
            if (!whitelist.playerInsideWhitelist(userCache.getPlayerCache(event.getUsername()).getUuid())) {
                logger.info("非白名单玩家 " + event.getUsername() + " 尝试加入");
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Component.text(config.getKickMsg()).color(NamedTextColor.RED)));
            }
        }
    }

    @Subscribe
    public void onGameProfileRequestEvent(GameProfileRequestEvent event) {
        if (userCache.playerInsideUserCache(event.getUsername())) {
            return;
        }
        // 写入玩家缓存
        userCache.add(event.getGameProfile().getName(), event.getGameProfile().getId());
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (!config.getEnable()) {
            return;
        }
        // 不在白名单
        if (!whitelist.playerInsideWhitelist(userCache.getPlayerCache(event.getPlayer().getUsername()).getUuid())) {
            logger.info("非白名单玩家 " + event.getPlayer().getUsername() + " 尝试加入");
            event.getPlayer().disconnect(Component.text(config.getKickMsg()).color(NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onDisconnectEvent(DisconnectEvent event) {
    }

    private void show() {
        logger.info("FSP-whitelist v" + BuildConstants.VERSION);
        logger.info("by tangsu99");
        logger.info("https://github.com/tangsu99/FSP-Whitelist");
    }

    private void configEnable() {
        if (config.getEnable()) {
            logger.info("白名单已启用");
        }else {
            logger.info("白名单已禁用");
        }
    }
}
