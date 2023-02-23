package cn.fsp.fspwhitelist.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.velocitypowered.api.util.UuidUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class ProfileAPI {
    public static Profile getProfile(String playerName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + playerName))
                .build();
        HttpResponse<String> response;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (response.statusCode() == 200) {
            Gson gson = new GsonBuilder().create();
            JsonObject jsonObject = gson.fromJson(response.body(), JsonObject.class);
            String name = jsonObject.get("name").getAsString();
            UUID uuid = UuidUtils.fromUndashed(jsonObject.get("uuid").getAsString());
            Profile profile = new Profile(name, uuid, true);
            return profile;
        }
        Profile profile = new Profile(playerName, UUID.nameUUIDFromBytes(("OfflinePlayer:" + playerName).getBytes(StandardCharsets.UTF_8)), false);
        return profile;
    }
}
