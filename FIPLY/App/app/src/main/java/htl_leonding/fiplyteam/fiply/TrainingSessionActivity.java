package htl_leonding.fiplyteam.fiply;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainingSessionActivity extends AppCompatActivity {

    EditText tvStopwatchCountDown;
    Button btStopwatchStart;
    Button btStopwatchReset;
    long milliSecondsTotal;
    CountDownTimer cdt;

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
        tvStopwatchCountDown = (EditText) findViewById(R.id.tvStopwatchCountDown);
        btStopwatchStart = (Button) findViewById(R.id.tsStopwatchBtStart);
        btStopwatchReset = (Button) findViewById(R.id.tsStopwatchBtReset);

        setMilliSecondsTotal(30000);
        tvStopwatchCountDown.setText("" + getMilliSecondsTotal() / 1000);

        btStopwatchStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btStopwatchStart.getText().equals("Start")) {//Start the Timer
                    createNewTimer(Long.valueOf(tvStopwatchCountDown.getText().toString()) * 1000);
                    tvStopwatchCountDown.setCursorVisible(false);
                    btStopwatchStart.setText("Pause");
                } else if (btStopwatchStart.getText().equals("Pause")) {//Pause the Timer
                    cdt.cancel();
                    btStopwatchStart.setText("Continue");
                } else if (btStopwatchStart.getText().equals("Continue")) {//Continue the Timer
                    btStopwatchStart.setText("Pause");
                    createNewTimer(Long.valueOf(tvStopwatchCountDown.getText().toString()) * 1000);
                }
            }
        });

        btStopwatchReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                tvStopwatchCountDown.setText("" + getMilliSecondsTotal() / 1000);
                tvStopwatchCountDown.setCursorVisible(true);
                btStopwatchStart.setText("Start");
            }
        });
    }

    public void createNewTimer(long timerLength) {
        cdt = new CountDownTimer(timerLength, 1000) {
            public void onTick(long millisUntilFinished) {
                tvStopwatchCountDown.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvStopwatchCountDown.setText("" + getMilliSecondsTotal() / 1000);
                Toast.makeText(getApplicationContext(), "Tolle Arbeit!", Toast.LENGTH_SHORT);
            }
        };
        cdt.start();
    }
}
