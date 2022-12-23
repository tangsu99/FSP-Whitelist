package cn.fsp.fspwhitelist;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

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

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        // todo 整理一遍代码
        whitelist = new Whitelist(logger);
        commandManager.register(injector.getInstance(CmdBuilder.class).register(this));
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        aPlayer player = new aPlayer();
        player.main(event.getPlayer().getUsername());
        if (!whitelist.playerInsideWhitelist(player)) {
            event.getPlayer().disconnect(Component.text("You are not whitelisted!"));
            return;
        }
        logger.info("+> " + event.getPlayer().getUsername());
    }
}
