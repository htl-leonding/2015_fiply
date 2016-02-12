package htl_leonding.fiplyteam.fiply.statistic;

import com.jjoe64.graphview.series.DataPointInterface;

/**
 * Created by Gerald on 12/02/2016.
 */
public class MoodTime implements DataPointInterface {


    private double x;
    private double y;


    public MoodTime(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
