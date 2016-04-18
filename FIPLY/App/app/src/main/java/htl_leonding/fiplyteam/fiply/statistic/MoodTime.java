package htl_leonding.fiplyteam.fiply.statistic;

import android.util.Log;

import com.jjoe64.graphview.series.DataPointInterface;

/**
 * Created by Gerald on 12/02/2016.
 */
public class MoodTime implements DataPointInterface {

    //x und y Koordinaten des Datenpunktes
    private double _mood;
    private double _timestamp;


    public MoodTime(double x, double y) {
        Log.wtf("new MoodTime","x:"+x+"y:"+y);
        _mood = y;
        _timestamp = x;
    }

    @Override
    public double getX() {
        return _timestamp;
    }

    @Override
    public double getY() {
        return _mood;
    }
}
