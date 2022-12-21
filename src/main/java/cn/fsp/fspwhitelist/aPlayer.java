package cn.fsp.fspwhitelist;

import java.util.UUID;

public class aPlayer {

    private UUID uuid;

    private String id;

    private String name;

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public UUID getUuid(){
        return this.uuid;
    }
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public boolean playerInside(String player){
        if (name.equals(player)) {
            return true;
        }
        return false;
    }
}
