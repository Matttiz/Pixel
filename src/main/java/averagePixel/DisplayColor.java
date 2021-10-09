package averagePixel;

import lombok.SneakyThrows;

import java.awt.*;

public class DisplayColor {

    private final int height = Toolkit.getDefaultToolkit().getScreenSize().height;
    private final int width = Toolkit.getDefaultToolkit().getScreenSize().width;
    private final int sampleHeight = 600;
    private final int sampleWidth = 600;

    private final int pixelsNumber = (height * width) / (sampleHeight * sampleWidth);
    private final int pixelsHeightToCheck = height / sampleHeight;
    private final int pixelsWidthToCheck = width / sampleWidth;

    private final int channelNumber = 64;
    private Color color;
    private int blue;
    private int green;
    private int red;

    private int[][][] pixelChannelArray = new int[channelNumber][channelNumber][channelNumber];

    @SneakyThrows
    public DisplayColor(boolean simplest) {
        PixelColor pixel = new PixelColor();
//        pixelChannelArray = new int[channelNumber][channelNumber][channelNumber];
        for (int i = 0; i < pixelsWidthToCheck; i++) {
            for (int j = 0; j < pixelsHeightToCheck; j++) {
                pixel.getColor(i * sampleHeight, j * sampleWidth);
                if (simplest) {
                    red += pixel.getRed();
                    green += pixel.getGreen();
                    blue += pixel.getBlue();
                } else {
                    pixelChannelArray
                            [pixel.getRed() / (256 / channelNumber)]
                            [pixel.getGreen() / (256 / channelNumber)]
                            [pixel.getBlue() / (256 / channelNumber)] += 1;
                }
            }
        }
        if (simplest) {
            setAverageColor();
        } else {
            setMostPopularColor();
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

    public void setMostPopularColor() {
        Color mostPopularColor = new Color(0, 0, 0);
        int frequencyMostPopularColor = pixelChannelArray[0][0][0];
        int channelSize = 256 / channelNumber;
        int halfChannelSize = channelSize / 2;
        for (int red = 0; red < channelNumber; red++) {
            for (int green = 0; green < channelNumber; green++) {
                for (int blue = 0; blue < channelNumber; blue++) {
                    System.out.println("Red " + red);
                    System.out.println("Green " + green);
                    System.out.println("Blue " + blue);
                    if (pixelChannelArray[blue][red][green] > frequencyMostPopularColor) {
                        frequencyMostPopularColor = pixelChannelArray[red][green][blue];
                        mostPopularColor = new Color(
                                red * (channelSize - 1) + halfChannelSize,
                                green * (channelSize - 1) + halfChannelSize,
                                blue * (channelSize - 1) + halfChannelSize
                        );
                    }
                }
            }
        }
        this.color = mostPopularColor;
    }
}
