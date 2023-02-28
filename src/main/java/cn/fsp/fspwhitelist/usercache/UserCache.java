package cn.fsp.fspwhitelist.usercache;

import cn.fsp.fspwhitelist.FspWhitelist;
import cn.fsp.fspwhitelist.util.Profile;
import cn.fsp.fspwhitelist.util.UserCacheEntry;
import com.google.gson.*;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UserCache {
    private final Map<UUID, UserCacheEntry> byUuid = new HashMap<>();
    private final Map<String, UserCacheEntry> byName = new HashMap<>();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path filePath = Paths.get("./plugins/fsp-whitelist/usercache.json");
    private final Path path = Paths.get("./plugins/fsp-whitelist/");
    private JsonArray userCacheArray;
    private Logger logger;

    public UserCache(FspWhitelist plugin) {
        this.logger = plugin.logger;
        load();
        logger.info("User cache load done!");
    }

    public void add(Profile profile) {
        userCacheArray.add(newEntry(profile.getName(), profile.getUuid(), getExpiresOn().toString()));
        save();
    }

    public UserCacheEntry getUserCache(UUID uuid) {
        return byUuid.get(uuid);
    }
    public UserCacheEntry getUserCache(String name) {
        return byName.get(name);
    }

    public void load() {
        createFile();
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            userCacheArray = gson.fromJson(reader, JsonArray.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (userCacheArray == null) {
            return;
        }
        toMap();
    }

    public void save() {
        try {
            Files.write(filePath, gson.toJson(toJsonArray()).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String updateName(String name) {
        return name;
    }

    private void toMap() {
        for (JsonElement jsonElement : userCacheArray) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            String name = jsonObject.get("name").getAsString();
            UUID uuid = UUID.fromString(jsonObject.get("uuid").getAsString());
            Date expiresOn = fromDate(jsonObject.get("expiresOn").getAsString());
            if (expires(expiresOn, getCurrentDate())) {
                name = updateName(name);
                expiresOn = getExpiresOn();
            }
            byUuid.put(uuid, new UserCacheEntry(name, uuid, expiresOn));
            byName.put(name, new UserCacheEntry(name, uuid, expiresOn));
        }
        save();
    }

    private JsonArray toJsonArray() {
        JsonArray jsonArray = new JsonArray();
        for (UserCacheEntry userCacheEntry : byUuid.values()) {
            jsonArray.add(newEntry(userCacheEntry.getName(), userCacheEntry.getUuid(), fromStringExpiresOn(userCacheEntry.getExpiresOn())));
        }
        return jsonArray;
    }

    private static DateFormat getDateFormat() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
    }

    // 转为 Date 类型
    private static Date fromDate(String expiresOn) {
        DateFormat dateFormat = getDateFormat();
        Date date = null;
        try {
            date = dateFormat.parse(expiresOn);
        } catch (ParseException parseException) {
            // empty catch block
        }
        return date;
    }

    private static String fromStringExpiresOn(Date date) {
        return getDateFormat().format(date);
    }

    // 获取过期时间
    private static Date getExpiresOn() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(2, 1);
        return calendar.getTime();
    }

    // 获取当前时间
    private static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 判断是否过期
     * @param expiresDate 过期时间
     * @param currentDate 当前时间
     * @return 是否过期
     */
    private static boolean expires(Date expiresDate, Date currentDate) {
        return currentDate.getTime() >= expiresDate.getTime();
    }

    private static JsonElement newEntry(String name, UUID uuid, String expiresOn) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("uuid", uuid.toString());
        jsonObject.addProperty("expiresOn", expiresOn);
        return jsonObject;
    }

    private void createPath() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
        }
    }

    private void createFile() {
        createPath();
        try {
            Files.createFile(filePath);
            Files.write(filePath, "[]".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
    }

    public void reLoadUserCache() {
    }
}
