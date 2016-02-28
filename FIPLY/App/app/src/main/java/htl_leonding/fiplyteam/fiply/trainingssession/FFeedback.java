package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;
import htl_leonding.fiplyteam.fiply.menu.FMain;
import htl_leonding.fiplyteam.fiply.menu.MainActivity;
import htl_leonding.fiplyteam.fiply.statistic.FStatistic;

public class FFeedback extends Fragment {

    TextView tvGewicht;
    Button btnStats;
    RatingBar rbMood;
    double mood;
    StatisticRepository srep;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        StatisticRepository.setContext(getActivity());
        srep = StatisticRepository.getInstance();
        mainActivity = (MainActivity) getActivity();

        tvGewicht = (TextView) getActivity().findViewById(R.id.tvFeedbackGewicht);
        rbMood = (RatingBar) getActivity().findViewById(R.id.rbFeedbackMood);
        btnStats = (Button) getActivity().findViewById(R.id.btnFeedbackMood);

        rbMood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setMood(Double.valueOf(rating));
            }
        });

        btnStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayMainMenu(new FStatistic(), getFragmentManager());
            }
        });

        tvGewicht.setText("Du hast heute insgesamt " + getArguments().getDouble("gesamtgewicht") + " kg gestemmt!");
        srep.insertDataPoint(getMood(), getArguments().getDouble("gesamtgewicht"));

        if(mainActivity.mInterstitialAd.isLoaded())
        {
            mainActivity.mInterstitialAd.show();
        }

        mainActivity.mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                mainActivity.requestNewInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                super.onAdFailedToLoad(errorCode);
                mainActivity.requestNewInterstitial();
            }
        });
    }

    public Double getMood() {
        return mood;
    }

    public void setMood(Double mood) {
        this.mood = mood;
    }
}
