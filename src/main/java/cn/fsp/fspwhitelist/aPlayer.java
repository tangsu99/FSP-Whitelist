package cn.fsp.fspwhitelist;

import java.util.UUID;

public class aPlayer {
    private UUID uuid;
    private String name;

    public aPlayer(String name, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public boolean playerInside(String player) {
        return this.name.equals(player);
    }

    public boolean playerInside(UUID uuid) {
        return this.uuid.equals(uuid);
    }
}
