package averagePixel;

public class SelectedPart {


    private int startHeightPart;
    private int startWidthPart;
    private boolean set = false;

    public SelectedPart(){
    }

    public SelectedPart(int quarter, int witchQuarter) {
        int sqrt = (int) (Math.sqrt(quarter));
        this.startHeightPart = witchQuarter / sqrt;
        this.startWidthPart = witchQuarter % sqrt;
        set = true;
    }

    public boolean isSet() {
        return set;
    }

    public int getStartHeightPart() {
        return startHeightPart;
    }

    public int getStartWidthPart() {
        return startWidthPart;
    }
}
