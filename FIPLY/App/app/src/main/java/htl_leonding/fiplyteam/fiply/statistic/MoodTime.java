package htl_leonding.fiplyteam.fiply.statistic;

import com.jjoe64.graphview.series.DataPointInterface;

/**
 * Created by Gerald on 12/02/2016.
 */
public class MoodTime implements DataPointInterface {

    //x und y Koordinaten des Datenpunktes
    private double _mood;
    private double _timestamp;


    public MoodTime(double x, double y) {
        _mood = x;
        _timestamp = y;
    }

    @Override
    public double getX() {
        return _mood;
    }

    @Override
    public double getY() {
        return _timestamp;
    }
}
