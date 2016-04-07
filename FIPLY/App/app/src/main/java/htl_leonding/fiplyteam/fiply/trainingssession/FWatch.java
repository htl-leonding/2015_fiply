package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import htl_leonding.fiplyteam.fiply.R;

public class FWatch extends Fragment {

    Chronometer chrono;
    Button watchStart, watchReset, watchLap;
    Boolean isChronoRunning = false, isShowingLap = false;
    private long capturedTime;

    /**
     * Hier wird das fragment_watch fragment angezeigt
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_watch, container, false);
    }

    /**
     * Hier werden alle ViewElemente und Listener gesetzt
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chrono = (Chronometer) getActivity().findViewById(R.id.myChronometer);
        watchStart = (Button) getActivity().findViewById(R.id.btnWatchStart);
        watchReset = (Button) getActivity().findViewById(R.id.btnWatchReset);
        watchLap = (Button) getActivity().findViewById(R.id.btnWatchLap);

        /**
         * Diesr Listener behandelt das Verhalten des Start Buttons
         */
        watchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!getIsChronoRunning()) {
                    watchStart.setText(R.string.watchPause);
                    setIsChronoRunning(true);
                    chrono.setBase(SystemClock.elapsedRealtime() + getCapturedTime());
                    chrono.start();
                    watchLap.setClickable(true);
                    setIsShowingLap(false);
                } else if (getIsShowingLap()) {
                    watchStart.setText(R.string.watchPause);
                    chrono.start();
                    watchLap.setClickable(true);
                    watchLap.setText(R.string.watchLap);
                    setIsShowingLap(false);
                } else {
                    setIsChronoRunning(false);
                    watchStart.setText(R.string.watchContinue);
                    setCapturedTime(chrono.getBase() - SystemClock.elapsedRealtime());
                    chrono.stop();
                    watchLap.setClickable(false);
                    setIsShowingLap(false);
                }
            }
        });
        /**
         * Dieser Lsitener beinhaltet das Verhalten des Reset Buttons
         */
        watchReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchStart.setText(R.string.watchStart);
                setCapturedTime(0);
                setIsChronoRunning(false);
                chrono.setBase(SystemClock.elapsedRealtime());
                chrono.stop();
                watchLap.setClickable(false);
                watchLap.setText(R.string.watchLap);
                setIsShowingLap(false);
            }
        });
        /**
         * Dieser Listener behandelt das Verhalten des Lap Buttons
         */
        watchLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getIsShowingLap()) {
                    watchStart.setText(R.string.watchPause);
                    chrono.start();
                    setIsShowingLap(false);
                    watchLap.setClickable(true);
                    watchLap.setText(R.string.watchLap);
                } else {
                    setIsShowingLap(true);
                    watchLap.setText(R.string.watchContinue);
                    watchStart.setText(R.string.watchContinue);
                    chrono.stop();
                }
            }
        });

        watchLap.setClickable(false);
    }

    public long getCapturedTime() {
        return capturedTime;
    }

    public void setCapturedTime(long capturedTime) {
        this.capturedTime = capturedTime;
    }

    public Boolean getIsChronoRunning() {
        return isChronoRunning;
    }

    public void setIsChronoRunning(Boolean chronoRunning) {
        this.isChronoRunning = chronoRunning;
    }

    public Boolean getIsShowingLap() {
        return isShowingLap;
    }

    public void setIsShowingLap(Boolean isShowingLap) {
        this.isShowingLap = isShowingLap;
    }
}
