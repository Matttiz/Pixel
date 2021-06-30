package averagePixel;

import lombok.SneakyThrows;

import java.awt.*;

public class DisplayColor {

    private final int height = 2160;
    private final int width = 3840;
    private final int sampleHeight = 800;
    private final int sampleWidth = 800;

    private final int pixelsHeightToCheck = height / sampleHeight;
    private final int pixelsWidthToCheck = width / sampleWidth;
    private final int pixelsNumber = (height * width) / (sampleHeight * sampleWidth);

    private Color color;
    private int blue;
    private int green;
    private int red;

    @SneakyThrows
    public DisplayColor() throws AWTException {
        Robot robot = new Robot();
        PixelColor pixel = new PixelColor();
        for (int i = 0; i < pixelsWidthToCheck; i++) {
            for (int j = 0; j < pixelsHeightToCheck; j++) {
                pixel.getColor(i * sampleHeight, j * sampleWidth);
                blue += pixel.getBlue();
                red += pixel.getRed();
                green += pixel.getGreen();
            }
        }
    }

    public void setAverageColor() {
        blue = blue / pixelsNumber;
        green = green / pixelsNumber;
        red = red / pixelsNumber;
        this.color = new Color(red, green, blue);
    }

    public Color getPixelColor() {
        return this.color;
    }
}
