package averagePixel;

import lombok.SneakyThrows;

import java.awt.*;

public class DisplayColor {

    private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int sampleHeight = 200;
    private final int sampleWidth = 200;

    private static int pixelsNumber;
    private static int pixelsHeightToCheck;
    private static int pixelsWidthToCheck;

    private static int pixelHeightStart;
    private static int pixelWidthStart;
    private static SelectedPart part = new SelectedPart();


    private Color color;
    private int blue;
    private int green;
    private int red;

    @SneakyThrows
    public DisplayColor( int witchQuarter, int quarter) {
        setFirstTime(witchQuarter, quarter);
        PixelColor pixel = new PixelColor();
        for (int i = pixelWidthStart; i <pixelWidthStart +  pixelsWidthToCheck; i++) {
            for (int j = pixelHeightStart; j < pixelHeightStart + pixelsHeightToCheck; j++) {
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

    private void setFirstTime(int witchQuarter, int quarter) {
        if (!part.isSet()){
            pixelsNumber = ((height * width) / (sampleHeight * sampleWidth) / quarter);
            pixelsHeightToCheck = (int) ((height / sampleHeight) / Math.sqrt(quarter));
            pixelsWidthToCheck = (int) ((width / sampleWidth) / Math.sqrt(quarter));
            part = new SelectedPart(quarter, witchQuarter);
            pixelHeightStart = part.getStartHeightPart() * pixelsHeightToCheck ;
            pixelWidthStart = part.getStartWidthPart() * pixelsWidthToCheck;
        }
    }
}
