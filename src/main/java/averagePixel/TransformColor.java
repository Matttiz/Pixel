package averagePixel;

import java.awt.*;

import static java.lang.Math.pow;

public class TransformColor {

    Color color;
    float x;
    float y;
    float red;
    float green;
    float blue;
    float X;
    float Y;
    float Z;

    public TransformColor(Color color) {
        this.color = color;

        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();

        this.red = (color.getRed() > 0.04045f) ? (float) pow((color.getRed() + 0.055f) / (1.0f + 0.055f), 2.4f) : (color.getRed() / 12.92f);
        this.green = (color.getGreen() > 0.04045f) ? (float) pow((color.getGreen() + 0.055f) / (1.0f + 0.055f), 2.4f) : (color.getGreen() / 12.92f);
        this.blue = (color.getBlue() > 0.04045f) ? (float) pow((color.getBlue() + 0.055f) / (1.0f + 0.055f), 2.4f) : (color.getBlue() / 12.92f);


        this.X = red * 0.649926f + green * 0.103455f + blue * 0.197109f;
        this.Y = red * 0.234327f + green * 0.743075f + blue * 0.022598f;
        this.Z = red * 0.0000000f + green * 0.053077f + blue * 1.035763f;

        this.x = X / (X + Y + Z);
        this.y = Y / (X + Y + Z);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
