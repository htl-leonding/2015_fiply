package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.menu.FMain;

public class FFeedback extends Fragment {

    TextView tvGewicht;
    Button btnStats;
    RatingBar rbMood;
    double mood;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_feedback, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                //Methodenaufruf mit getMood und getArguments().getDouble("gesamtgewicht")
                displayFragment.displayMainMenu(new FMain(), getFragmentManager());
            }
        });
        //tvGewicht.setText("Du hast heute insgesamt " + getArguments().getDouble("gesamtgewicht") + " kg gestemmt!");
    }

    public Double getMood() {
        return mood;
    }

    public void setMood(Double mood) {
        this.mood = mood;
    }
}
