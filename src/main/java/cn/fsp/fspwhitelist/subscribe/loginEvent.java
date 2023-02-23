package cn.fsp.fspwhitelist.subscribe;

import cn.fsp.fspwhitelist.usercache.UserCache;
import cn.fsp.fspwhitelist.FspWhitelist;
import cn.fsp.fspwhitelist.whitelist.WhiteList;
import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.LoginEvent;
import com.velocitypowered.api.event.connection.PreLoginEvent;
import com.velocitypowered.api.event.player.GameProfileRequestEvent;
import com.velocitypowered.api.proxy.ProxyServer;

public class loginEvent {
    private ProxyServer server;
    private UserCache userCache;
    private WhiteList whiteList;
    public loginEvent(FspWhitelist plugin){
        this.server = plugin.server;
        this.userCache = plugin.userCache;
        this.whiteList = plugin.whitelist;
    }
    @Subscribe(order = PostOrder.FIRST)
    public void onPreLoginEvent(PreLoginEvent event) {
//        // 判断有无玩家的数据缓存
//        if (userCache.playerInsideUserCache(event.getUsername())) {
//            // 不在白名单
//            if (!whitelist.playerInsideWhitelist(userCache.getPlayerCache(event.getUsername()).getUuid())) {
//                logger.info("非白名单玩家 " + event.getUsername() + " 尝试加入");
//                event.setResult(PreLoginEvent.PreLoginComponentResult.denied(Component.text(config.getKickMsg()).color(NamedTextColor.RED)));
//            }
//        }
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onGameProfileRequestEvent(GameProfileRequestEvent event) {
//        if (userCache.playerInsideUserCache(event.getUsername())) {
//            return;
//        }
//        // 写入玩家缓存
//        userCache.add(event.getGameProfile().getName(), event.getGameProfile().getId());
    }

    @Subscribe(order = PostOrder.FIRST)
    public void onLoginEvent(LoginEvent event) {
        // 不在白名单
//        if (!whitelist.playerInsideWhitelist(userCache.getPlayerCache(event.getPlayer().getUsername()).getUuid())) {
//            logger.info("非白名单玩家 " + event.getPlayer().getUsername() + " 尝试加入");
//            event.setResult(ResultedEvent.ComponentResult.denied(Component.text(config.getKickMsg()).color(NamedTextColor.RED)));
////            event.getPlayer().disconnect(Component.text(config.getKickMsg()).color(NamedTextColor.RED));
//        }
    }
}
