package cn.fsp.fspwhitelist.util;

import java.util.Date;
import java.util.UUID;

public class UserCacheEntry {
    private String name;
    private UUID uuid;
    private Date expiresOn;

    public UserCacheEntry(String name, UUID uuid, Date expiresOn) {
        this.name = name;
        this.uuid = uuid;
        this.expiresOn = expiresOn;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }
}
