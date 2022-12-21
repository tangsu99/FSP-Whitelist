package cn.fsp.fspwhitelist;

import java.util.UUID;

public class bPlayer {
    private String id;
    private String name;

    public String getName(){
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getId(){
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
}
