package cn.fsp.fspwhitelist;

import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CmdHandler {
    private Whitelist wl;
    private UuidAPI uuidAPI;

    public CmdHandler(FspWhitelist fspWhitelist) {
        this.wl = fspWhitelist.whitelist;
    }

    public int help(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("/fwhitelist"));
        return 1;
    }

    public int list(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }

    public int on(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }

    public int off(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }

    @SneakyThrows
    public int add(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        UuidAPI uuidAPI1 = new UuidAPI(name);
        if(uuidAPI1.isOnline()){
            wl.add(name, uuidAPI1.getUUID());
            source.sendMessage(Component.text("Whitelist added " + name + ".").color(NamedTextColor.GREEN));
            source.sendMessage(Component.text(wl.listWhitelist()));
        }else {
            source.sendMessage(Component.text("Add failed " + name).color(NamedTextColor.RED));
            source.sendMessage(Component.text(wl.listWhitelist()));
        }
        return 1;
    }
    @SneakyThrows
    public int remove(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        UuidAPI uuidAPI1 = new UuidAPI(name);
        if(wl.playerInsideWhitelist(name)){
            source.sendMessage(Component.text("Whitelist removeed " + name + ". UUID: " + uuidAPI1.getUUID().toString()).color(NamedTextColor.RED));
            wl.remove(name);
            source.sendMessage(Component.text(wl.listWhitelist()));
        }else {
            source.sendMessage(Component.text(name + "Not on the whitelist.").color(NamedTextColor.RED));
            source.sendMessage(Component.text(wl.listWhitelist()));
        }
        return 1;
    }
}