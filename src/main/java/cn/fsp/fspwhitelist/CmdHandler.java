package cn.fsp.fspwhitelist;

import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.slf4j.Logger;

public class CmdHandler {
    private Whitelist whitelist;
    private Config config;
    private Logger logger;

    public CmdHandler(FspWhitelist fspWhitelist) {
        this.whitelist = fspWhitelist.whitelist;
        this.config = fspWhitelist.config;
        this.logger = fspWhitelist.logger;
    }

    public int help(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("FSP-whitelist 命令帮助"));
        source.sendMessage(Component.text("/fwhitelist on/off/list/reload/add/remove"));
        source.sendMessage(Component.text("on/off: 开/关白名单"));
        source.sendMessage(Component.text("list: 列出白名单中的玩家"));
        source.sendMessage(Component.text("reload: 重载白名单"));
        source.sendMessage(Component.text("add/remove: 在白名单中添加/删除玩家"));
        return 1;
    }

    public int list(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        source.sendMessage(Component.text("白名单中共有" + whitelist.getLength() + "位玩家"));
        source.sendMessage(Component.text(whitelist.listWhitelist()));
        return 1;
    }

    public int on(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        config.reviseEnable(true);
        source.sendMessage(Component.text("白名单已启用"));
        return 1;
    }

    public int off(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        config.reviseEnable(false);
        source.sendMessage(Component.text("白名单已禁用"));
        return 1;
    }

    public int reload(CommandContext<CommandSource> commandSourceCommandContext) {
        CommandSource source = commandSourceCommandContext.getSource();
        whitelist.reLoadWhitelist();
        config.reLoadConfig();
        source.sendMessage(Component.text("插件已重载"));
        return 1;
    }
//    @SneakyThrows
    public int add(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        aPlayer player = new aPlayer();
        if(!player.main(name)){     // 不是正版玩家
            source.sendMessage(Component.text(name + "可能不是正版玩家").color(NamedTextColor.RED));
            return 1;
        }
        if (whitelist.playerInsideWhitelist(player)){       // 已经在白名单
            source.sendMessage(Component.text(name + " 已在白名单").color(NamedTextColor.GREEN));
            return 1;
        }
        whitelist.add(player);
        source.sendMessage(Component.text("已将 " + name + "添加至白名单").color(NamedTextColor.GREEN));
        return 1;
    }
    public int remove(CommandContext<CommandSource> commandSourceCommandContext){
        String name = commandSourceCommandContext.getArgument("playername", String.class);
        CommandSource source = commandSourceCommandContext.getSource();
        UuidAPI uuidAPI = new UuidAPI(name);
        if(!uuidAPI.isOnline()){        // 不是正版玩家
            source.sendMessage(Component.text(name + "可能不是正版玩家").color(NamedTextColor.RED));
            return 1;
        }
        if (!whitelist.playerInsideWhitelist(uuidAPI.getAplayer())){       // 不在白名单
            source.sendMessage(Component.text("白名单不存在此玩家").color(NamedTextColor.RED));
            return 1;
        }
        whitelist.remove(uuidAPI.getUUID());
        source.sendMessage(Component.text("已删除 " + name + "的白名单").color(NamedTextColor.RED));
        return 1;
    }
}