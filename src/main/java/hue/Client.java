package hue;

import lombok.SneakyThrows;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Client {
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> response;

    @SneakyThrows
    public void setBulbColor(float x, float y) {
        String uri = Connect.BRIDGE_ADDRESS + Connect.API + Connect.USERNAME + Connect.LIGHTS + Connect.DEN_LIGHTS_ID + Connect.STATE;
        JSONObject gamut = Messages.BuildGamut(x, y);

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gamut.toString()))
                .build();

        this.response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public void createUser(String bridgeAddress) {
        String uri = "http://" + bridgeAddress + Connect.API;
        JSONObject gamut = Messages.BuildNewUser();

        HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gamut.toString()))
                .build();

        this.response = client.send(request,
                HttpResponse.BodyHandlers.ofString());
    }

    @SneakyThrows
    public String username() {
        String string = response.body().lines().toString();
        if (response.body().contains("error")) {
            System.out.println("Naciśnij przycisk na mostku, a następnie naciśnij dowolny przycisk");
            Scanner scanner = new Scanner(System.in);
            System.in.read();
            return "";
        }
        if (response.body().contains("success")) {
            return response.body().substring(response.body().indexOf("username\":\""), response.body().indexOf("\"}}]"));
        }
        return "";
    }
}
