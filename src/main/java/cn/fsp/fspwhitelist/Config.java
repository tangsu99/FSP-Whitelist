package cn.fsp.fspwhitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private config cfg;
    private Path path = Paths.get("./plugins/fsp-whitelist/");
    private Path filePath = Paths.get("./plugins/fsp-whitelist/config.json");

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public Config() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
        }
        try {
            Files.createFile(filePath);
            config conf = new config();
            Files.write(filePath, gson.toJson(conf).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
        loadFile();
    }

    public boolean getEnable() {
        return cfg.getEnable();
    }

    public String getKickMsg() {
        return cfg.kickMsg;
    }

    public void reviseEnable(boolean tf) {
        cfg.setEnable(tf);
        saveFile();
    }
    public void reLoadConfig(){
        loadFile();
    }
    private void loadFile() {
        String configFile;
        try {
            configFile = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cfg = gson.fromJson(configFile, config.class);
    }

    private void saveFile() {
        try {
            Files.write(filePath, gson.toJson(cfg).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private class config {
        private boolean enable = true;
        private String kickMsg = "You are not on whitelist!";

        public boolean getEnable() {
            return enable;
        }

        public void setEnable(boolean enable) {
            this.enable = enable;
        }

        public String getKickMsg() {
            return kickMsg;
        }
    }
}
