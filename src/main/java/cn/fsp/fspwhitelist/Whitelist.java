package cn.fsp.fspwhitelist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.slf4j.Logger;

public class Whitelist {
    private aPlayer[] ps;
    private Logger logger;
    private UserCache userCache;
    private Path path = Paths.get("./plugins/fsp-whitelist/");
    private Path filePath = Paths.get("./plugins/fsp-whitelist/whitelist.json");
    private Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public Whitelist(Logger logger) {
        this.logger = logger;
        createFile();
        loadFile();
        userCache = new UserCache(logger);
        logger.info("Load whitelist done.");
    }

    public UserCache getUserCache() {
        return userCache;
    }

    public String listWhitelist() {
        String str = "";
        int i = 0;
        for (aPlayer p : ps) {
            str = str.concat(p.getName());
            if (i < ps.length){
                str = str.concat(", ");
            }
            i++;
        }
        return str;
    }

    public boolean playerInsideWhitelist(UUID uuid) {
        for (aPlayer p : ps) {
            if (p.playerInside(uuid)) return true;
        }
        return false;
    }

    // 在线
    public void add(Profile profile) {
        aPlayer player = profile.getAplayer();
        int len = ps.length;
        ps = Arrays.copyOf(ps, len + 1);
        ps[len] = player;
        saveFile();
        userCache.add(profile.getName(), profile.getUuid());
    }

    public void remove(Profile profile) {
        int i = 0;
        int index = 0;
        aPlayer[] temp = new aPlayer[ps.length - 1];
        for (aPlayer p : ps) {
            if (p.playerInside(profile.getUuid())) {
                index = 1;
            }
            if (i < temp.length) {
                temp[i] = ps[i + index];
            }
            i++;
        }
        ps = temp;
        saveFile();
    }

    public String[] getWhiteListPLAYERNAME() {
        String[] ALL = new String[ps.length];
        int i = 0;
        for (aPlayer p : ps) {
            ALL[i] = p.getName();
            i++;
        }
        return ALL;
    }

    public int getLength(){
        return ps.length;
    }
    public void reLoadWhitelist(){
        loadFile();
        userCache.reLoadUserCache();
    }
    private void loadFile() {
        createFile();
        String whiteListFile;
        try {
            whiteListFile = Files.readString(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ps = gson.fromJson(whiteListFile, aPlayer[].class);
    }
    private void saveFile() {
        createFile();
        try {
            Files.write(filePath, gson.toJson(ps).getBytes(StandardCharsets.UTF_8));
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
