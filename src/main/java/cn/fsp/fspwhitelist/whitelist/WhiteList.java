package cn.fsp.fspwhitelist.whitelist;

import cn.fsp.fspwhitelist.FspWhitelist;
import cn.fsp.fspwhitelist.Profile;
import cn.fsp.fspwhitelist.UserCache;
import cn.fsp.fspwhitelist.aFspWhitelist;
import com.google.gson.*;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WhiteList {
    public final Map<UUID, String> byUuid = new HashMap<>();

    private final Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
    private final Path path = Paths.get("./whitelist.json");
    private JsonArray whiteListArray;
    private Logger logger;
    private UserCache userCache;
    private final Map<UUID, Integer> index = new HashMap<>();

    public WhiteList(aFspWhitelist plugin) {
        load();
    }

    public boolean isAllowed(Profile profile) {
        return byUuid.containsKey(profile.getUuid());
    }

    public void add(Profile profile) {
        whiteListArray.add(newEntry(profile.getUuid(), profile.getName()));
        save();
    }

    public void remove(Profile profile) {
        whiteListArray.remove(index.get(profile.getUuid()));
        save();
    }

    public void load() {
        createFile();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            this.whiteListArray = gson1.fromJson(reader, JsonArray.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (this.whiteListArray == null) {
            return;
        }
        int i = 0;
        for (JsonElement jsonElement : this.whiteListArray) {
            String name = jsonElement.getAsJsonObject().get("name").getAsString();
            UUID uuid = UUID.fromString(jsonElement.getAsJsonObject().get("uuid").getAsString());
            this.byUuid.put(uuid, name);
            this.index.put(uuid, i);
            i++;
        }
    }

    public void save() {
        try {
            Files.write(path, gson1.toJson(whiteListArray).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setIndex();
    }

    private static JsonElement newEntry(UUID uuid, String name) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uuid", uuid.toString());
        jsonObject.addProperty("name", name);
        return jsonObject;
    }

    private void createFile() {
        try {
            Files.createDirectory(path);
        } catch (IOException e) {
        }
        try {
            Files.createFile(path);
            Files.write(path, "[]".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
        }
    }

    private void setIndex() {
        int i = 0;
        for (JsonElement jsonElement : this.whiteListArray) {
            this.index.put(UUID.fromString(jsonElement.getAsJsonObject().get("uuid").getAsString()), i);
            i++;
        }
    }
}
