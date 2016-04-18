package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;
import htl_leonding.fiplyteam.fiply.menu.FMain;
import htl_leonding.fiplyteam.fiply.menu.MainActivity;
import htl_leonding.fiplyteam.fiply.statistic.FStatistic;

public class FFeedback extends Fragment {

    TextView tvGewicht;
    Button btnStats;
    RatingBar rbMood;
    double mood = 3;
    double weight;
    StatisticRepository srep;
    MainActivity mainActivity;


    /**
     * Hier wird das fragment_feedback fragment angezeigt
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    /**
     * Hier werden alle VuewElemente und Listener gesetzt
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StatisticRepository.setContext(getActivity());
        srep = StatisticRepository.getInstance();
        mainActivity = (MainActivity) getActivity();

        tvGewicht = (TextView) getActivity().findViewById(R.id.tvFeedbackGewicht);
        rbMood = (RatingBar) getActivity().findViewById(R.id.rbFeedbackMood);
        btnStats = (Button) getActivity().findViewById(R.id.btnFeedbackMood);
        weight = getArguments().getDouble("gesamtgewicht")/100;
        rbMood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setMood((double)rating);
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayTrainingsession(new FStatistic(), getFragmentManager());
            }
        });


        tvGewicht.setText("Du hast heute " + weight + " mal deine Maximalkraft gehoben!");

        /*
        if(mainActivity.mInterstitialAd.isLoaded()) { //wenn die Werbung geladen ist, wird sie angezeigt
            mainActivity.mInterstitialAd.show();
        }
        mainActivity.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() { //wird die Werbung geschlossen, wird die nächste bereits geladen
                super.onAdClosed();
                mainActivity.requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) { //tritt ein Fehler beim Laden einer Werbung auf, wird die nächste bereits geladen
                super.onAdFailedToLoad(errorCode);
                mainActivity.requestNewInterstitial();
            }
        });
        */
    }

    @Override
    public void onDestroyView() {
        try {
            srep.insertDataPoint(getMood(), weight);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onDestroyView();
    }

    public Double getMood() {
        return mood;
    }

    public void setMood(Double mood) {
        Log.wtf("setMood", String.valueOf(mood));
        this.mood = mood;
    }
}
