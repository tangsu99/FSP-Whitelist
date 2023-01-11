package cn.fsp.fspwhitelist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.event.query.ProxyQueryEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import net.kyori.adventure.text.Component;
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

    public Config config;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("FSP-whitelist v" + BuildConstants.VERSION);
        logger.info("by tangsu99");
        logger.info("https://github.com/tangsu99/FSP-Whitelist");
        config = new Config();
        logger.info("Load config done.");
        if (!server.getConfiguration().isOnlineMode()) {
            logger.error("velocity 为离线模式");
            config.reviseEnable(false);
            logger.error("插件加载失败");
            return;
        }
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
    public void onLoginEvent(LoginEvent event) {
        logger.info("LoginEvent");
        logger.info("+> " + event.getPlayer().getUsername());
        if (!config.getEnable()) {
            return;
        }
        aPlayer player = new aPlayer();
        player.main(event.getPlayer().getUsername());
        if (!whitelist.playerInsideWhitelist(player)) {
            event.getPlayer().disconnect(Component.text(config.getKickMsg()));
        }
    }

    @Subscribe
    public void onPreLoginEvent(PreLoginEvent event) {
        logger.info("PreLoginEvent");
    }

    @Subscribe
    public void onGameProfileRequestEvent(GameProfileRequestEvent event) {
        logger.info("GameProfileRequestEvent");
        logger.info(event.getGameProfile().getId().toString());
    }
    @Subscribe
    public void onDisconnectEvent(DisconnectEvent event) {
        logger.info("-> " + event.getPlayer().getUsername());
        logger.info(event.getPlayer().getCurrentServer().toString());
    }

    // 大饼
    // 跨服聊天
//    @Subscribe
//    public void onPlayerChatEvent(PlayerChatEvent event) {
//        String currentServerName = event.getPlayer().getCurrentServer().orElseThrow().getServer().getServerInfo().getName();
//        String playerName = event.getPlayer().getUsername();
//        String message = event.getMessage();
//        for (RegisteredServer server1 : server.getAllServers()) {
//            if (!server1.getServerInfo().getName().equals(currentServerName)) {
//                for (Player player : server1.getPlayersConnected()) {
//                    player.sendMessage(Component.text("[" + currentServerName + "]<" + playerName + ">: " + message));
//                }
//            }
//        }
//    }

    // 大饼
    // ping相关
    @Subscribe
    public void onProxyPingEvent(ProxyPingEvent event) {
        logger.info(event.toString());
        logger.info(event.getPing().toString());
        logger.info(event.getConnection().toString());
        logger.info(event.getConnection().getRemoteAddress().getAddress().toString());
        logger.info(event.getConnection().getRemoteAddress().getHostName());
        logger.info(String.valueOf(event.getConnection().getVirtualHost()));
    }
}
