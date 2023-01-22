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
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.util.GameProfile;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.slf4j.Logger;

import java.util.HashMap;

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
    public HashMap<String, UserCacheFile> cache;

    public Config config;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("FSP-whitelist v" + BuildConstants.VERSION);
        logger.info("by tangsu99");
        logger.info("https://github.com/tangsu99/FSP-Whitelist");
        config = new Config();
        logger.info("Load config done.");
//        if (!server.getConfiguration().isOnlineMode()) {
//            logger.error("velocity 为离线模式");
//            config.reviseEnable(false);
//            logger.error("插件加载失败");
//            return;
//        }
        if (server.getConfiguration().isOnlineMode()) {
            logger.info("velocity 为在线模式");
        }else {
            logger.info("velocity 为离线模式");
        }
        userCache = new UserCache();
        cache = userCache.getUserCacheFileHashMap();
        whitelist = new Whitelist(logger);
        logger.info("Load whitelist done.");
        commandManager.register(injector.getInstance(CmdBuilder.class).register(this));
        logger.info("插件加载成功");
        if (config.getEnable()) {
            logger.info("白名单已启用");
            return;
        }
        logger.info("白名单已禁用");
    }

    @Subscribe
    public void onPreLoginEvent(PreLoginEvent event) {
        logger.info("PreLoginEvent +> " + event.getUsername());
        if (!config.getEnable()) {
            return;
        }
        // 判断有无玩家的数据缓存
        if (cache.containsKey(event.getUsername())) {
            // 不在白名单
            if (!whitelist.playerInsideWhitelist(cache.get(event.getUsername()).getUuid())) {
                logger.info("非白名单玩家 " + event.getUsername() + " 尝试加入");
                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Component.text(config.getKickMsg()).color(NamedTextColor.RED)));
            }
        }
    }

    @Subscribe
    public void onGameProfileRequestEvent(GameProfileRequestEvent event) {
        logger.info("GameProfileRequestEvent +> " + event.getUsername());
        if (!cache.containsKey(event.getUsername())) {
            // 写入玩家缓存
            userCache.add(event.getGameProfile().getName(), event.getGameProfile().getId());
        }
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        logger.info("LoginEvent > " + event.getPlayer().getUsername());
        if (!config.getEnable()) {
            return;
        }
        // 不在白名单
        if (!whitelist.playerInsideWhitelist(cache.get(event.getPlayer().getUsername()).getUuid())) {
            logger.info("非白名单玩家 " + event.getPlayer().getUsername() + " 尝试加入");
            event.getPlayer().disconnect(Component.text(config.getKickMsg()).color(NamedTextColor.RED));
        }
    }

    @Subscribe
    public void onDisconnectEvent(DisconnectEvent event) {
        logger.info("DisconnectEvent -> " + event.getPlayer().getUsername());
    }

    // 大饼
    // ping相关
//    @Subscribe
//    public void onProxyPingEvent(ProxyPingEvent event) {
//        logger.info(event.toString());
//        logger.info(event.getPing().toString());
//        logger.info(event.getConnection().toString());
//        logger.info(event.getConnection().getRemoteAddress().getAddress().toString());
//        logger.info(event.getConnection().getRemoteAddress().getHostName());
//        logger.info(String.valueOf(event.getConnection().getVirtualHost()));
//    }
}
