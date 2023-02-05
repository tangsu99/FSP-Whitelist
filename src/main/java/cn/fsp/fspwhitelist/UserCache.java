package cn.fsp.fspwhitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class UserCache {
    private UserCacheFile userCacheFile[];
    private HashMap<String, UserCacheFile> userCacheFileHashMap;
    private final Path path = Paths.get("./plugins/fsp-whitelist/");
    private final Path filePath = Paths.get("./plugins/fsp-whitelist/usercache.json");
    private final Logger logger;
    private final Gson gson = new GsonBuilder()
            .create();

    public UserCache(Logger logger) {
        this.logger = logger;
        createFile();
        loadFile();
    }
    public aPlayer getPlayerCache(String name) {
        return new aPlayer(name, this.userCacheFileHashMap.get(name).getUuid());
    }

    public boolean playerInsideUserCache(String player) {
        if (this.userCacheFileHashMap.containsKey(player)) {
            return true;
        }
        return false;
    }

    public void add(String name, UUID uuid) {
        UserCacheFile userCacheFile1 = new UserCacheFile(name, uuid);
        userCacheFileHashMap.put(name, userCacheFile1);
        int len = userCacheFile.length;
        userCacheFile = Arrays.copyOf(userCacheFile, len + 1);
        userCacheFile[len] = userCacheFile1;
        saveFile();
    }

    private void setUserCacheFileHashMap() {
        userCacheFileHashMap = new HashMap<>();
        for (UserCacheFile userCacheFile1 : userCacheFile) {
            userCacheFileHashMap.put(userCacheFile1.getName(), userCacheFile1);
        }
    }

    public void reLoadUserCache(){
        loadFile();
        logger.info("User Cache reload done!");
    }

    private void loadFile() {
        createFile();
        String cacheFile;
        try {
            cacheFile = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userCacheFile = gson.fromJson(cacheFile, UserCacheFile[].class);
        setUserCacheFileHashMap();
    }

    private void saveFile() {
        createFile();
        try {
            Files.write(filePath, gson.toJson(userCacheFile).getBytes(StandardCharsets.UTF_8));
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
            Files.write(filePath, "[]".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
    }
}
