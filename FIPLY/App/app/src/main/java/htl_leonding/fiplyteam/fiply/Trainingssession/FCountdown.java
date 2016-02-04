package htl_leonding.fiplyteam.fiply.Trainingssession;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import htl_leonding.fiplyteam.fiply.R;

public class FCountdown extends Fragment {

    EditText etTimer;
    Button btTimerStart;
    CountDownTimer cdt;
    long milliSecondsTotal;
    Boolean isTimerRunning = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_countdown, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etTimer = (EditText) getActivity().findViewById(R.id.etTimer);
        btTimerStart = (Button) getActivity().findViewById(R.id.btnTimerStart);

        setMilliSecondsTotal(30000);
        etTimer.setText("" + getMilliSecondsTotal() / 1000);

        btTimerStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMilliSecondsTotal(Long.valueOf(etTimer.getText().toString()) * 1000);

                if (!getIsTimerRunning()) {
                    setIsTimerRunning(true);
                    createNewTimer(Long.valueOf(etTimer.getText().toString()) * 1000);
                    etTimer.setClickable(false);
                    btTimerStart.setText(R.string.timerPause);
                } else {
                    setIsTimerRunning(false);
                    cdt.cancel();
                    etTimer.setClickable(true);
                    btTimerStart.setText(R.string.timerContinue);
                }
            }
        });
    }

    /**
     * Erstellt einen neuen Timer der in einem EditText angezeigt wird
     *
     * @param timerLength Zeit in ms
     */
    public void createNewTimer(long timerLength) {
        cdt = new CountDownTimer(timerLength, 1000) {
            public void onTick(long millisUntilFinished) {
                etTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                etTimer.setText("" + 30);
                Toast.makeText(getActivity(), "Tolle Arbeit!", Toast.LENGTH_SHORT).show();
            }
        };
        cdt.start();
    }

    public long getMilliSecondsTotal() {
        return milliSecondsTotal;
    }

    public void setMilliSecondsTotal(long milliSecondsTotal) {
        this.milliSecondsTotal = milliSecondsTotal;
    }

    public Boolean getIsTimerRunning() {
        return isTimerRunning;
    }

    public void setIsTimerRunning(Boolean isTimerRunning) {
        this.isTimerRunning = isTimerRunning;
    }
}

