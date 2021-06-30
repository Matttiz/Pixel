package hue;

import lombok.SneakyThrows;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Messages {


    public static JSONObject BuildLightIsOn(boolean isOn) {
        JSONObject isLightOn = new JSONObject();
        isLightOn.put("on", isOn);
        return isLightOn;
    }

    @SneakyThrows
    public static JSONObject BuildBrightness(short brightness) throws Exception {
        if (brightness < 0 || brightness > 255) {
            throw new Exception("Brightness isn't correct");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bri", brightness);
        return jsonObject;
    }

    @SneakyThrows
    public static JSONObject BuildGamut(float x, float y) throws Exception {
        if (x < 0.0 || x > 1.0) {
            throw new Exception("x isn't correct");
        }
        if (y < 0.0 || y > 1.0) {
            throw new Exception("x isn't correct");
        }
        List<Float> list = new ArrayList<Float>();
        list.add(x);
        list.add(y);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xy", list);
        return jsonObject;
    }

    @SneakyThrows
    public static JSONObject BuildNewUser() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("devicetype", "pixel-sync");
        return jsonObject;
    }
}