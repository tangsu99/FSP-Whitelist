package cn.fsp.fspwhitelist.util;

import java.util.UUID;

public class Profile {
    private UUID uuid;
    private String name;
    private boolean online;

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
}
