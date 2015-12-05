package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.Toast;

import htl_leonding.fiplyteam.fiply.R;

public class FTrainingssession extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_session);
        tvTimer = (EditText) findViewById(R.id.tvTimer);
        btTimerStart = (Button) findViewById(R.id.tsTimerBtStart);
        btTimerReset = (Button) findViewById(R.id.tsTimerBtReset);
        mChronometer = new Chronometer(this);

        setMilliSecondsTotal(30000);
        tvTimer.setText("" + getMilliSecondsTotal() / 1000);

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

    public void createNewTimer(long timerLength) {
        cdt = new CountDownTimer(timerLength, 1000) {
            public void onTick(long millisUntilFinished) {
                tvTimer.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvTimer.setText("" + getMilliSecondsTotal() / 1000);
                Toast.makeText(getApplicationContext(), "Tolle Arbeit!", Toast.LENGTH_SHORT).show();
            }
        };
        cdt.start();
    }
}
