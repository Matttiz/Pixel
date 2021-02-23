package averagePixel;

import lombok.SneakyThrows;

import java.awt.*;

public class PixelColor {
    private Color color;
    private Robot robot = new Robot();

    public PixelColor() throws AWTException {
    }

    @SneakyThrows
    public PixelColor(int x, int y) throws AWTException {
        color = robot.getPixelColor(x, y);
    }

    public Color getColor() {
        return color;
    }

    public Color getColor(int x, int y) {
        color = robot.getPixelColor(x, y);
        return color;
    }

    public int getBlue() {
        return color.getBlue();
    }

    public int getGreen() {
        return color.getGreen();
    }

    public int getRed() {
        return color.getRed();
    }
}
