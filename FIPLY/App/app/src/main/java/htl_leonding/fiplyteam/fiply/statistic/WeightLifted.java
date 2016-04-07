package htl_leonding.fiplyteam.fiply.statistic;

import com.jjoe64.graphview.series.DataPointInterface;


public class WeightLifted implements DataPointInterface {

    //x und y Koordinaten des Datenpunktes
    private double _weight;
    private double _timestamp;

    public WeightLifted(double x, double y) {
        _weight = x;
        _timestamp = y;
    }

    @Override
    public double getX() {
        return _weight;
    }

    @Override
    public double getY() {
        return _timestamp;
    }
}
