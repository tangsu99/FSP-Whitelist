package cn.fsp.fspwhitelist;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import org.slf4j.Logger;

public class Whitelist {
    private aPlayer[] ps;
    @Inject
    private Logger logger;

    public Whitelist(Logger logger) {
        this.logger = logger;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        ps = gson.fromJson(loadFile(), aPlayer[].class);
    }

    public String listWhitelist() {
        String str = "";
        for (aPlayer p : ps) {
            str = str.concat(p.getName() + ", ");
        }
        return str;
    }

    public boolean playerInsideWhitelist(aPlayer player) {
        for (aPlayer p : ps) {
            if (p.playerInside(player.getUuid())) {
                return true;
            }
        }
        return false;
    }
    public boolean playerInsideWhitelist(String player) {
        for (aPlayer p : ps) {
            if (p.playerInside(player)) {
                return true;
            }
        }
        return false;
    }
    public void add(aPlayer player) {
        int len = ps.length;
        ps = Arrays.copyOf(ps, len + 1);
        ps[len] = player;
        saveFile();
    }

    public void remove(UUID uuid) {
        int i = 0;
        int index = 0;
        aPlayer[] temp = new aPlayer[ps.length - 1];
        for (aPlayer p : ps) {
            if (p.playerInside(uuid)) {
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
    public int getLength(){
        return ps.length;
    }
    private String loadFile() {
        try {
            return Files.readString(Paths.get("C:/Users/18763/Desktop/whitelist.json"))
                    .replaceAll("\r|\n| ", "")
                    .replaceAll("\"", "\"")
                    .replaceAll(",", ", ");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void saveFile() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        try {
            Files.write(Paths.get("C:/Users/18763/Desktop/whitelist.json"), gson.toJson(ps).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
