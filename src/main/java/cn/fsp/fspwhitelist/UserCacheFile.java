package cn.fsp.fspwhitelist;

import java.util.UUID;

public class UserCacheFile {
    private String name;
    private UUID uuid;
    // 未实现
    private String expiresOn = "2023-02-13 10:09:49 +0800";

    public UserCacheFile(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getExpiresOn() {
        return expiresOn;
    }
}