package htl_leonding.fiplyteam.fiply.statistic;

import android.util.Log;

import com.jjoe64.graphview.series.DataPointInterface;


public class WeightLifted implements DataPointInterface {

    //x und y Koordinaten des Datenpunktes
    private double _weight;
    private double _timestamp;

    public WeightLifted(double x, double y) {
        Log.wtf("new WeightLifted", "x:" + x + "y:" + y);
        _weight = y;
        _timestamp = x;
    }

    @Override
    public double getX() {
        return _timestamp;
    }

    @Override
    public double getY() {
        return _weight;
    }
}
