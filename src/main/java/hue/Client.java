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
    private static boolean isOn = false;

    @SneakyThrows
    public void setBulbColor(float x, float y) {
        JSONObject gamut = Messages.BuildGamut(x, y);
        String uri = Connect.BRIDGE_ADDRESS
                + Connect.API
                + Connect.USERNAME
                + Connect.LIGHTS
                + Connect.DEN_LIGHTS_ID
                + Connect.STATE;
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

    @SneakyThrows
    public void setPowerOn(){
        if(!isOn){
            String uri = Connect.BRIDGE_ADDRESS
                    + Connect.API
                    + Connect.USERNAME
                    + Connect.LIGHTS
                    + Connect.DEN_LIGHTS_ID;
            HttpRequest request = HttpRequest.newBuilder(URI.create(uri))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            boolean status = new JSONObject(response.body()).getJSONObject("state").getBoolean("on") ;
            if(!status){
                JSONObject turnOn = Messages.BuildLightIsOn(true);
                HttpRequest request2 = HttpRequest.newBuilder(URI.create(uri + Connect.STATE))
                        .header("Content-Type", "application/json")
                        .PUT(HttpRequest.BodyPublishers.ofString(turnOn.toString()))
                        .build();

                HttpResponse<String> response2 = client.send(request2,
                        HttpResponse.BodyHandlers.ofString());
            }
        }
    }
}
