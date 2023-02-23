package cn.fsp.fspwhitelist.whitelist;

import cn.fsp.fspwhitelist.util.Profile;
import cn.fsp.fspwhitelist.usercache.UserCache;
import cn.fsp.fspwhitelist.FspWhitelist;
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

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final Path filePath = Paths.get("./plugins/fsp-whitelist/whitelist.json");
    private final Path path = Paths.get("./plugins/fsp-whitelist/");
    private JsonArray whiteListArray;
    private Logger logger;
    private UserCache userCache;
    private final Map<UUID, Integer> index = new HashMap<>();

    public WhiteList(FspWhitelist plugin) {
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
        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            this.whiteListArray = gson.fromJson(reader, JsonArray.class);
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
            Files.write(filePath, gson.toJson(whiteListArray).getBytes(StandardCharsets.UTF_8));
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

    private void createPath() {
        try {
            Files.createDirectory(filePath);
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

    private void setIndex() {
        int i = 0;
        for (JsonElement jsonElement : this.whiteListArray) {
            this.index.put(UUID.fromString(jsonElement.getAsJsonObject().get("uuid").getAsString()), i);
            i++;
        }
    }
}
