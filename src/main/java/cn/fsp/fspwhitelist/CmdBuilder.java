package cn.fsp.fspwhitelist;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;

public class CmdBuilder {
    public BrigadierCommand register(FspWhitelist fspWhitelist) {
        final CmdHandler cmdHandler = new CmdHandler(fspWhitelist);
        LiteralCommandNode<CommandSource> cmdNode = LiteralArgumentBuilder
                .<CommandSource>literal("fwhitelist").requires(sender -> sender.hasPermission("fwhitelist.admin")).executes(cmdHandler::help)
                .then(LiteralArgumentBuilder.<CommandSource>literal("list").executes(cmdHandler::list))
                .then(LiteralArgumentBuilder.<CommandSource>literal("reload").executes(cmdHandler::reload))
                .then(LiteralArgumentBuilder.<CommandSource>literal("on").executes(cmdHandler::on))
                .then(LiteralArgumentBuilder.<CommandSource>literal("off").executes(cmdHandler::off))
                .then(LiteralArgumentBuilder.<CommandSource>literal("add")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playername", StringArgumentType.string()).executes(cmdHandler::add)))
                .then(LiteralArgumentBuilder.<CommandSource>literal("remove")
                        .then(RequiredArgumentBuilder.<CommandSource, String>argument("playername", StringArgumentType.string()).executes(cmdHandler::remove)))
                .build();
        return new BrigadierCommand(cmdNode);
    }
}
