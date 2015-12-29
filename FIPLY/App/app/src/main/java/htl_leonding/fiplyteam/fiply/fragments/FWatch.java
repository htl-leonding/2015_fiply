package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Andreas on 29.12.2015.
 */
public class FWatch extends Fragment {

    private long capturedTime;
    Chronometer chrono;
    Button watchStart, watchStop, watchReset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chrono = (Chronometer) getActivity().findViewById(R.id.myChronometer);
        watchStart = (Button) getActivity().findViewById(R.id.watchStart);
        watchStop = (Button) getActivity().findViewById(R.id.watchStop);
        watchReset=(Button) getActivity().findViewById(R.id.watchReset);
        watchStop.setClickable(false);

        watchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchStart.setClickable(false);
                watchStop.setClickable(true);
                watchStart.setText("Continue");
                chrono.setBase(SystemClock.elapsedRealtime() + getCapturedTime());
                chrono.start();
            }
        });
        watchStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchStart.setClickable(true);
                watchStop.setClickable(false);
                setCapturedTime(chrono.getBase() - SystemClock.elapsedRealtime());
                chrono.stop();
            }
        });
        watchReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchStart.setClickable(true);
                watchStop.setClickable(false);
                watchStart.setText("Start");
                chrono.stop();
                setCapturedTime(0);
                chrono.setBase(SystemClock.elapsedRealtime());
            }
        });
    }

    public long getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(long capturedTime) {
        this.capturedTime = capturedTime;
    }
}
