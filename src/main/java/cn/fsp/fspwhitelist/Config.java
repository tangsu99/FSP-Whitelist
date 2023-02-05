package cn.fsp.fspwhitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.slf4j.Logger;

public class Config {
    private ConfigStorage cfg;
    private Path path = Paths.get("./plugins/fsp-whitelist/");
    private Path filePath = Paths.get("./plugins/fsp-whitelist/config.json");
    private Logger logger;
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public Config(Logger logger) {
        this.logger = logger;
        createFile();
        loadFile();
        logger.info("Load config done.");
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
        createFile();
        String configFile;
        try {
            configFile = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cfg = gson.fromJson(configFile, ConfigStorage.class);
    }

    private void saveFile() {
        createFile();
        try {
            Files.write(filePath, gson.toJson(cfg).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createFile() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
        }
        try {
            Files.createFile(filePath);
            ConfigStorage conf = new ConfigStorage();
            Files.write(filePath, gson.toJson(conf).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
    }
}
