package cn.fsp.fspwhitelist.config;

import com.google.gson.*;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private JsonObject cfg;
    private Path path = Paths.get("./plugins/fsp-whitelist/");
    private Path filePath = Paths.get("./plugins/fsp-whitelist/config.json");
    private Logger logger;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private boolean enable = true;
    private String kickMsg = "你不在白名单中！";

    public Config(Logger logger) {
        this.logger = logger;
        newConfigEntry();
        loadFile();
    }

    public boolean getEnable() {
        return cfg.get("enable").getAsBoolean();
    }

    public String getKickMsg() {
        return cfg.get("kickMsg").getAsString();
    }

    public void setEnable(boolean e) {
        if (e == enable) {
            return;
        }
        enable = e;
        newConfigEntry();
    }

    private void loadFile() {
        createFile();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            cfg = gson.fromJson(reader, JsonObject.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        enable = cfg.get("enable").getAsBoolean();
        kickMsg = cfg.get("kickMsg").getAsString();
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
            Files.write(filePath, gson.toJson(cfg).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
    }

    private void newConfigEntry() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("kickMsg", kickMsg);
        jsonObject.addProperty("enable", enable);
        cfg = jsonObject;
        saveFile();
    }
}
