package cn.fsp.fspwhitelist;

import java.io.IOException;
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

    public Whitelist(Logger logger) throws IOException {
        this.logger = logger;
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        ps = gson.fromJson(loadFile(), aPlayer[].class);
    }

//    public static void main(String[] args) throws IOException, InterruptedException {
//        Whitelist whitelist = new Whitelist();
//        MinecraftAPI minecraftAPI = new MinecraftAPI("tangsu99");
//        whitelist.add("tangsu", minecraftAPI.getUUID());
//        for (aPlayer p : whitelist.ps) {
//            System.out.println(p.getName());
//            System.out.println(p.getUuid());
//        }
//    }

    public String listWhitelist() {
        String str = "";
        for (aPlayer p : ps) {
            str = str.concat(p.getName() + ", ");
        }
        return str;
    }

    public void debug() {
        for (aPlayer p : ps) {
            logger.info(p.getName());
        }
    }

    public boolean playerInsideWhitelist(String player) {
        for (aPlayer p : ps) {
            if (p.playerInside(player)) {
                return true;
            }
        }
        return false;
    }

    public void add(String playerName, UUID uuid) {
        aPlayer newp = new aPlayer();
        newp.setName(playerName);
        newp.setUuid(uuid);
        int len = ps.length;
        ps = Arrays.copyOf(ps, len + 1);
        ps[len] = newp;
    }

    private String loadFile() throws IOException {
        return Files.readString(Paths.get("C:/Users/18763/Desktop/whitelist.json"))
                .replaceAll("\r|\n| ", "")
                .replaceAll("\"", "\"")
                .replaceAll(",", ", ");
    }
}
