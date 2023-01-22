package cn.fsp.fspwhitelist;

import com.velocitypowered.api.util.UuidUtils;

import java.util.UUID;

public class aPlayer {
    private UUID uuid;
    private String name;

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

    public boolean main(String name) {
        UuidAPI uuidAPI = new UuidAPI(name);
        if (!uuidAPI.isOnline()) {
            return false;
        }
        this.uuid = uuidAPI.getUUID();
        this.name = name;
        return true;
    }

    public void main(bPlayer pl) {
        this.uuid = UuidUtils.fromUndashed(pl.getId());
        this.name = pl.getName();
    }

    public void main(String name, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
    }
}
