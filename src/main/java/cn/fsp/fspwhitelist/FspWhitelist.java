package cn.fsp.fspwhitelist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.io.IOException;

@Plugin(
        id = "fsp-whitelist",
        name = "Fsp Whitelist",
        version = BuildConstants.VERSION,
        url = "http://tangsu99.cn",
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

    @Inject
    public FspWhitelist(ProxyServer server, Logger logger) throws IOException {
        this.server = server;
        this.logger = logger;
        whitelist = new Whitelist(logger);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        // todo 整理一遍代码
        commandManager.register(injector.getInstance(CommandBuilder.class).register(this));
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (!whitelist.playerInsideWhitelist(event.getPlayer().getUsername())) {
            event.getPlayer().disconnect(Component.text("You are not whitelisted!"));
        }else {
            logger.info("+++> " + event.getPlayer().getUsername());
        }
    }
}
