package cn.fsp.fspwhitelist;

import com.velocitypowered.api.util.UuidUtils;

import java.util.UUID;

public class Profile {
    private UUID uuid;
    private String name;
    private boolean online;

    public Profile(String playerName) {
        Profile profile = ProfileAPI.getProfile(playerName);
        if (profile == null) {

        }
        this.name = profile.name;
        this.uuid = profile.uuid;
        this.online = profile.online;
    }

    public Profile(aPlayer aplayer, boolean online) {
        this.uuid = aplayer.getUuid();
        this.name = aplayer.getName();
        this.online = online;
    }

    public Profile(bPlayer bplayer, boolean online) {
        this.uuid = UuidUtils.fromUndashed(bplayer.getId());
        this.name = bplayer.getName();
        this.online = online;
    }

    public Profile(String name, UUID uuid, boolean online) {
        this.uuid = uuid;
        this.name = name;
        this.online = online;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean isOnline() {
        return this.online;
    }

    public boolean playerInside(String player) {
        return this.name.equals(player);
    }

    public boolean playerInside(UUID uuid) {
        return this.uuid.equals(uuid);
    }

    public aPlayer getAplayer() {
        return new aPlayer(this.name, this.uuid);
    }
}
