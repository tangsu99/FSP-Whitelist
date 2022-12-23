package cn.fsp.fspwhitelist;

import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class CmdHandler {
    private Whitelist wl;

    public CmdHandler(FspWhitelist fspWhitelist) {
        this.wl = fspWhitelist.whitelist;
    }

    public int help(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("FSP-whitelist help"));
        source.sendMessage(Component.text("/fwhitelist on/off/list/add/remove"));
        source.sendMessage(Component.text("on/off: "));
        source.sendMessage(Component.text("list: "));
        source.sendMessage(Component.text("add/remove: "));
        return 1;
    }

    public int list(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("白名单中共有" + wl.getLength() + "位玩家"));
        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }

    public int on(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("TODO"));
        return 1;
    }

    public int off(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("TODO"));
        return 1;
    }

    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("TODO"));
        return 1;
    }
    @SneakyThrows
    public int add(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        aPlayer player = new aPlayer();
        if(!player.main(name)){     // 不是正版玩家
            source.sendMessage(Component.text("Add failed " + name).color(NamedTextColor.RED));
            return 1;
        }
        if (wl.playerInsideWhitelist(player)){       // 已经在白名单
            source.sendMessage(Component.text(name + " in the whitelist.").color(NamedTextColor.GREEN));
            return 1;
        }
        wl.add(player);
        source.sendMessage(Component.text("Whitelist added " + name + ".").color(NamedTextColor.GREEN));
//        source.sendMessage(Component.text(wl.listWhitelist()));
        return 1;
    }
    public int remove(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        UuidAPI uuidAPI = new UuidAPI(name);
        if(!uuidAPI.isOnline()){        // 不是正版玩家
            source.sendMessage(Component.text(name + " not in whitelist.").color(NamedTextColor.RED));
        }
        if (!wl.playerInsideWhitelist(uuidAPI.getAplayer())){       // 不在白名单
            source.sendMessage(Component.text(name + " not in whitelist.").color(NamedTextColor.RED));
            return 1;
        }
        wl.remove(uuidAPI.getUUID());
        source.sendMessage(Component.text("Whitelist removed " + name + ".").color(NamedTextColor.RED));
        return 1;
    }
}