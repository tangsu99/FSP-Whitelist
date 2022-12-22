package cn.fsp.fspwhitelist;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.ProxyServer;

public class CommandBuilder {
    private static ProxyServer server;

    public BrigadierCommand register(FspWhitelist fspWhitelist) {
        final CmdHandler handler = new CmdHandler(fspWhitelist);
        LiteralCommandNode<CommandSource> cmdNode = LiteralArgumentBuilder
                .<CommandSource>literal("fwhitelist").requires(sender -> sender.hasPermission("fwhitelist.admin")).executes(handler::help)
                .then(LiteralArgumentBuilder.<CommandSource>literal("list").executes(handler::list))
                .then(LiteralArgumentBuilder.<CommandSource>literal("on").executes(handler::on))
                .then(LiteralArgumentBuilder.<CommandSource>literal("off").executes(handler::off))
                .then(LiteralArgumentBuilder.<CommandSource>literal("add")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playername", StringArgumentType.string()).executes(handler::add)))
                .then(LiteralArgumentBuilder.<CommandSource>literal("remove")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playername", StringArgumentType.string()).executes(handler::remove)))
                .build();
        return new BrigadierCommand(cmdNode);
    }
}
