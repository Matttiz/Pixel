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


    private final int channelNumber = 128;
    private Color color;
    private int blue;
    private int green;
    private int red;

    private int[][][] pixelChannelArray = new int[channelNumber][channelNumber][channelNumber];

    @SneakyThrows
    public DisplayColor(int witchQuarter, int quarter, boolean simplest){
        setFirstTime(witchQuarter, quarter);
        PixelColor pixel = new PixelColor();
        for (int i = pixelWidthStart; i <pixelWidthStart +  pixelsWidthToCheck; i++) {
            for (int j = pixelHeightStart; j < pixelHeightStart + pixelsHeightToCheck; j++) {
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

    @SneakyThrows
    public DisplayColor(boolean simplest) {
        setFirstTime(1, 1);
        PixelColor pixel = new PixelColor();
        for (int i = pixelWidthStart; i <pixelWidthStart +  pixelsWidthToCheck; i++) {
            for (int j = pixelHeightStart; j < pixelHeightStart + pixelsHeightToCheck; j++) {
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

    @SneakyThrows
    public DisplayColor(){
        boolean simplest = true;
        setFirstTime(1, 1);
        setProperPixelToDisplay(simplest);
    }

    private void setProperPixelToDisplay(boolean simplest) throws AWTException {
        PixelColor pixel = new PixelColor();
        for (int i = pixelWidthStart; i <pixelWidthStart +  pixelsWidthToCheck; i++) {
            for (int j = pixelHeightStart; j < pixelHeightStart + pixelsHeightToCheck; j++) {
                pixel.getColor(i * sampleHeight, j * sampleWidth);
                blue += pixel.getBlue();
                red += pixel.getRed();
                green += pixel.getGreen();
            }
        }
        for (int i = pixelWidthStart; i <pixelWidthStart +  pixelsWidthToCheck; i++) {
            for (int j = pixelHeightStart; j < pixelHeightStart + pixelsHeightToCheck; j++) {
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

    public void setMostPopularColor() {
        Color mostPopularColor = new Color(0, 0, 0);
        int frequencyMostPopularColor = pixelChannelArray[0][0][0];
        int channelSize = 256 / channelNumber;
        int halfChannelSize = channelSize / 2;
        for (int red = 0; red < channelNumber; red++) {
            for (int green = 0; green < channelNumber; green++) {
                for (int blue = 0; blue < channelNumber; blue++) {
                    if (pixelChannelArray[red][green][blue] > frequencyMostPopularColor) {
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
