package cn.fsp.fspwhitelist;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;

public class CommandBuilder {
    private static ProxyServer server;

    public static void register(FspWhitelist fspWhitelist) {
        server =  fspWhitelist.getServer();
        //Setup command flow
        final CmdHandler handler = new CmdHandler(fspWhitelist);
        server.getCommandManager().register(server.getCommandManager().metaBuilder("fwhitelist").build(), new BrigadierCommand(
                LiteralArgumentBuilder.<CommandSource>literal("fwhitelist").requires(sender -> sender.hasPermission("fwhitelist.admin")).executes(handler::help)
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("on").executes(handler::turnOn))
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("off").executes(handler::turnOff))
                        .then(LiteralArgumentBuilder.<CommandSource>literal("list").executes(handler::list))

//                        .then(LiteralArgumentBuilder.<CommandSource>literal("add").executes(handler::add))
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("add")
//                                .then(RequiredArgumentBuilder.<CommandSource, String>argument("username", StringArgumentType.word())
//                                        .suggests(CommandBuilder::allPlayers)
//                                        .executes(handler::add)))
//
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("remove").executes(handler::remove))
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("remove")
//                                .then(RequiredArgumentBuilder.<CommandSource, String>argument("username", StringArgumentType.word())
//                                        .suggests(CommandBuilder::allPlayers)
//                                        .executes(handler::remove)))
//
//                        .then(LiteralArgumentBuilder.<CommandSource>literal("reload").requires(source -> source.hasPermission("vgui.admin")).executes(handler::reload))
        ));
    }


//    private static CompletableFuture<Suggestions> allPlayers(CommandContext<CommandSource> context, SuggestionsBuilder builder) {
//        for (Player player : server.getAllPlayers()) {
//            String username = player.getUsername();
//            if (username.toLowerCase().startsWith(context.getInput().toLowerCase()) || username.equalsIgnoreCase(context.getInput())) {
//                builder.suggest(username);
//            }
//        }
//        return builder.buildFuture();
//    }
}
