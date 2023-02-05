package cn.fsp.fspwhitelist;

public class ConfigStorage {
    public boolean enable = true;
    public String kickMsg = "You are not on whitelist!";

    public boolean getEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
