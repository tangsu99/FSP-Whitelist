package cn.fsp.fspwhitelist;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.velocitypowered.api.util.UuidUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

public class UuidAPI {
    private aPlayer aplayer = new aPlayer();
    private Boolean online;
    public UuidAPI(String playerName){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + playerName))
                .build();
        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(response.statusCode() == 200){
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create();
            bPlayer bplayer = gson.fromJson(response.body(), bPlayer.class);
            aplayer.setName(bplayer.getName());
            aplayer.setUuid(UuidUtils.fromUndashed(bplayer.getId()));
            online = true;
        }else {
            online = false;
        }
    }

    public UUID getUUID(){
        return aplayer.getUuid();
    }

    public aPlayer getAplayer() {
        return aplayer;
    }

    public boolean isOnline(){
        return online;
    }
}
