package cn.fsp.fspwhitelist;

import com.google.inject.Inject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
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
    private ProxyServer server;

    public ProxyServer getServer(){
        return this.server;
    }

    @Inject
    private Logger logger;
    public Whitelist whitelist;

    @Inject
    public FspWhitelist(ProxyServer server, Logger logger) throws IOException, InterruptedException {
        this.server = server;
        this.logger = logger;
        whitelist = new Whitelist(logger);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws IOException, InterruptedException {
        UuidAPI uuidAPI = new UuidAPI(this, "tangsu99");
        logger.info(uuidAPI.getPlayerName());
        logger.info(uuidAPI.getUUID().toString());
        final CmdHandler handler = new CmdHandler(this);
        LiteralCommandNode<CommandSource> rootNode = LiteralArgumentBuilder.<CommandSource>literal("fwhitelist").build();

        //Register commands
        CommandBuilder.register(this);

//        final CmdHandler handler = new CmdHandler(this, wl);
//        server.getCommandManager().register(server.getCommandManager().metaBuilder("fwhitelist").build(),
//                new BrigadierCommand(LiteralArgumentBuilder
//                        .<CommandSource>literal("fwhitelist")
//                        .requires(source -> source.hasPermission("fwhitelist.cmds"))
//                        .executes(handler::help)
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("list")
//                                .requires(source -> source.hasPermission("fwhitelist.cmds.list"))
//                                .executes(handler::list)
//                        ).build()
//                )
//        );
    }

    @Subscribe
    public void onLoginEvent(LoginEvent event) {
        if (!whitelist.playerInsideWhitelist(event.getPlayer().getUsername().toString())) {
            event.getPlayer().disconnect(Component.text("You are not whitelisted!"));
        }else {
            logger.info("+++" + event.getPlayer().getUsername());
        }
    }
}
