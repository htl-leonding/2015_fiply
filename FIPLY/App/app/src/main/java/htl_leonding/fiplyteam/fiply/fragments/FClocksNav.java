package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.displayFragment;

public class FClocksNav extends Fragment {

    Button btnNavWatch, btnNavTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_clocksnav, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnNavWatch = (Button) getActivity().findViewById(R.id.btnNavWatch);
        btnNavTimer = (Button) getActivity().findViewById(R.id.btnNavTimer);

        btnNavWatch.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
        btnNavTimer.setBackgroundColor(getResources().getColor(R.color.darkSecondary));

        btnNavWatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayTSClock(new FWatch(), getFragmentManager());
                btnNavWatch.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
                btnNavTimer.setBackgroundColor(getResources().getColor(R.color.darkSecondary));
            }
        });
        btnNavTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayFragment.displayTSClock(new FCountdown(), getFragmentManager());
                btnNavWatch.setBackgroundColor(getResources().getColor(R.color.darkSecondary));
                btnNavTimer.setBackgroundColor(getResources().getColor(R.color.darkPrimary));
            }
        });

    }
}
