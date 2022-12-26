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
    public boolean on = true;
    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        if (!server.getConfiguration().isOnlineMode()){
            logger.error("onlineMode=false");
            on = false;
            return;
        }
        whitelist = new Whitelist(logger);
        commandManager.register(injector.getInstance(CmdBuilder.class).register(this));
    }
    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (!on){
            return;
        }
        aPlayer player = new aPlayer();
        player.main(event.getPlayer().getUsername());
        if (!whitelist.playerInsideWhitelist(player)) {
            event.getPlayer().disconnect(Component.text("你不在白名单中!"));
            return;
        }
        logger.info("+> " + event.getPlayer().getUsername());
    }
}
