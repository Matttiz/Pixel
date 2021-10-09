package averagePixel;

import lombok.SneakyThrows;

import java.awt.*;

public class DisplayColor {

    private static int quarter;
    private static int  witchQuarter;

    private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int sampleHeight = 600;
    private final int sampleWidth = 600;

    private final int pixelsNumber = (height * width / quarter) / (sampleHeight * sampleWidth);
    private final int pixelsHeightToCheck = (int) ((height / sampleHeight) / Math.sqrt(quarter));
    private final int pixelsWidthToCheck = (int) ((width / sampleWidth) / Math.sqrt(quarter));

    private static int pixelHeightStart;
    private static int pixelWidthStart;
    private static SelectedPart part = new SelectedPart();


    private Color color;
    private int blue;
    private int green;
    private int red;

    @SneakyThrows
    public DisplayColor(int quarter, int witchQuarter) {
        if (!part.isSet()){
            part = new SelectedPart(quarter,witchQuarter);
            pixelHeightStart = part.getStartHeightPart() * pixelsHeightToCheck ;
            pixelWidthStart = part.getStartWidthPart() * pixelsWidthToCheck;

        }


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

    public void setHeightQuarter(int witchQuarter){
        int  sqrt  = (int) (Math.sqrt(quarter));
        int all = (int) witchQuarter/sqrt;
        int part = (int) witchQuarter%sqrt;

        int startHeight = all + pixelsHeightToCheck/sqrt;
        int startWidth = part + pixelsWidthToCheck/sqrt;
    }
}
