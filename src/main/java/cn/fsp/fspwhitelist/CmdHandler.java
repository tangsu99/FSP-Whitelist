package cn.fsp.fspwhitelist;

import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import net.kyori.adventure.text.Component;

public class CmdHandler {
    private Whitelist wl;
    public CmdHandler(FspWhitelist fspWhitelist){
        this.wl = fspWhitelist.whitelist;
    }
    public int help(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("/fwhitelist"));
        return 1;
    }
    public int list(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("/fwhitelist list"));
        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }
}
