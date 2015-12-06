package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import htl_leonding.fiplyteam.fiply.R;

public class FTrainingssession extends Fragment {

    EditText tvTimer;
    Button btTimerStart;
    Button btTimerReset;

    long milliSecondsTotal;
    CountDownTimer cdt;
    Chronometer mChronometer;

    public long getMilliSecondsTotal() {
        return milliSecondsTotal;
    }

    public void setMilliSecondsTotal(long milliSecondsTotal) {
        this.milliSecondsTotal = milliSecondsTotal;
    }

    /**
     * Lädt das fragment_trainingssession in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_trainingssession, container, false);
    }

    /**
     * Wird aufgerufen nachdem die View aufgebaut ist und dient dem setzen der OnClickListener
     *
     * @param view               default
     * @param savedInstanceState default
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTimer = (EditText) getView().findViewById(R.id.tvTimer);
        btTimerStart = (Button) getView().findViewById(R.id.tsTimerBtStart);
        btTimerReset = (Button) getView().findViewById(R.id.tsTimerBtReset);
        mChronometer = new Chronometer(getActivity());

        setMilliSecondsTotal(30000);
        tvTimer.setText("" + getMilliSecondsTotal() / 1000);

        /**
         * Bei Drücken des Start Buttons wird der Timer gestartet
         * Bei erneutem Drücken wird der Timer pausiert
         * Danach dient er abwechselnd dem Weiterzählen oder pausieren
         */
        btTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btTimerStart.getText().equals("Start")) {//Start the Timer
                    createNewTimer(Long.valueOf(tvTimer.getText().toString()) * 1000);
                    tvTimer.setCursorVisible(false);
                    btTimerStart.setText("Pause");
                } else if (btTimerStart.getText().equals("Pause")) {//Pause the Timer
                    cdt.cancel();
                    btTimerStart.setText("Continue");
                } else if (btTimerStart.getText().equals("Continue")) {//Continue the Timer
                    btTimerStart.setText("Pause");
                    createNewTimer(Long.valueOf(tvTimer.getText().toString()) * 1000);
                }
            }
        });

        /**
         * Stellt den ursprünglichen Zustand des Timers wieder her
         */
        btTimerReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                tvTimer.setText("" + getMilliSecondsTotal() / 1000);
                tvTimer.setCursorVisible(true);
                btTimerStart.setText("Start");
            }
        });
    }

    /**
     * Erstellt einen neuen Timer der in einem EditText angezeigt wird
     * @param timerLength   Zeit in ms
     */
    public void createNewTimer(long timerLength) {
        cdt = new CountDownTimer(timerLength, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTimer.setText("" + getMilliSecondsTotal() / 1000);
                Toast.makeText(getActivity().getApplicationContext(), "Tolle Arbeit!", Toast.LENGTH_SHORT).show();
            }
        };
        cdt.start();
    }
}
