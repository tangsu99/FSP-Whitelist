package cn.fsp.fspwhitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
    private Path path = Paths.get("./plugins/fsp-whitelist/");
    private Path filePath = Paths.get("./plugins/fsp-whitelist/usercache.json");

    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public UserCache() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
        }
        try {
            Files.createFile(filePath);
            Files.write(filePath, "[]".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
        loadFile();
    }

    public HashMap<String, UserCacheFile> getUserCacheFileHashMap() {
        return userCacheFileHashMap;
    }

    public void add(String name, UUID uuid) {
        UserCacheFile userCacheFile1 = new UserCacheFile(name, uuid);
        userCacheFileHashMap.put(name, userCacheFile1);
        int len = userCacheFile.length;
        userCacheFile = Arrays.copyOf(userCacheFile, len + 1);
        userCacheFile[len] = userCacheFile1;
        saveFile();
    }

    public void reLoadUserCache(){
        loadFile();
    }

    private void loadFile() {
        String configFile;
        try {
            configFile = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userCacheFile = gson.fromJson(configFile, UserCacheFile[].class);
        for (UserCacheFile userCacheFile1 : userCacheFile) {
            userCacheFileHashMap.put(userCacheFile1.getName(), userCacheFile1);
        }
    }

    private void saveFile() {
        try {
            Files.write(filePath, gson.toJson(userCacheFile).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
