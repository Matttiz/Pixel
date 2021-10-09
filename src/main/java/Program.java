import averagePixel.DisplayColor;
import averagePixel.TransformColor;
import hue.Client;

import java.awt.*;

public class Program {
    public static void main(String[] args){
        Color averagePixelColor;
        Client client = new Client();
        DisplayColor displayColor;
        client.setPowerOn();
        do {
            displayColor = new DisplayColor(4,3);
            displayColor.setAverageColor();
            averagePixelColor = displayColor.getPixelColor();
            TransformColor transformColor = new TransformColor(averagePixelColor);
            client.setBulbColor(transformColor.getX(), transformColor.getY());
        }while (true);
    }
}
