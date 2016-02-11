package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import htl_leonding.fiplyteam.fiply.R;

public class FFeedback extends Fragment{

    TextView tvGewicht;
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

        rbMood.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                setMood(Double.valueOf(rating));
                if(getMood() < 3.5 && getMood() > 2.5);
                {
                    Toast.makeText(getActivity(), "Tolle Arbeit!", Toast.LENGTH_SHORT).show();
                }
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
