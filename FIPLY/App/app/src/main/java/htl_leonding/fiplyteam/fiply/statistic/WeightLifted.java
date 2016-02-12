package htl_leonding.fiplyteam.fiply.statistic;

import com.jjoe64.graphview.series.DataPointInterface;

/**
 * Created by Gerald on 11/02/2016.
 */
public class WeightLifted implements DataPointInterface {
    public double x;
    public double y;

    public WeightLifted(double x, double y) {
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
