package htl_leonding.fiplyteam.fiply.statistic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.Series;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;

/**
 * Created by Gerald on 11/02/2016.
 */
public class FStatistic extends Fragment {
    KeyValueRepository kvr;
    StatisticRepository str;

    TextView tvStatisticGreeting;
    GraphView gvMood;
    GraphView gvLift;

    LineGraphSeries<WeightLifted> weightLiftedSeries;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_statistic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        gvMood = (GraphView) getActivity().findViewById(R.id.graphMood);
        gvLift = (GraphView) getActivity().findViewById(R.id.graphLift);
        tvStatisticGreeting = (TextView) getActivity().findViewById(R.id.statisticGreeting);
        kvr = KeyValueRepository.getInstance();
        str = StatisticRepository.getInstance();

        try {
            tvStatisticGreeting.setText("Hallo " + kvr.getKeyValue("userName").getString(1) + "! Hier hast du einen Überblick über deine Fortschritte und deine physische Entwicklung.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        fillSeries();




        super.onViewCreated(view, savedInstanceState);
    }

    //Adds series
    public void fillSeries(){
        gvMood.addSeries(str.getSeriesForMoodTime());
        gvLift.addSeries(str.getSeriesForLiftedWeight());
    }
}
