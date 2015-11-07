package htl_leonding.fiplyteam.fiply;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class TrainingSessionActivity extends AppCompatActivity {

    EditText tvCountDown;
    Button btStart;
    Button btReset;
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
        tvCountDown = (EditText) findViewById(R.id.tvCountDown);
        btStart = (Button) findViewById(R.id.tsbtStart);
        btReset = (Button) findViewById(R.id.tsbtReset);

        setMilliSecondsTotal(30000);
        tvCountDown.setText("" + getMilliSecondsTotal() / 1000);

        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btStart.getText().equals("Start")) {//Start the Timer
                    createNewTimer(Long.valueOf(tvCountDown.getText().toString()) * 1000);
                    tvCountDown.setCursorVisible(false);
                    btStart.setText("Pause");
                } else if (btStart.getText().equals("Pause")) {//Pause the Timer
                    cdt.cancel();
                    btStart.setText("Continue");
                } else if (btStart.getText().equals("Continue")) {//Continue the Timer
                    btStart.setText("Pause");
                    createNewTimer(Long.valueOf(tvCountDown.getText().toString()) * 1000);
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cdt.cancel();
                tvCountDown.setText("" + getMilliSecondsTotal() / 1000);
                tvCountDown.setCursorVisible(true);
                btStart.setText("Start");
            }
        });
    }

    public void createNewTimer(long timerLength) {
        cdt = new CountDownTimer(timerLength, 1000) {
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                tvCountDown.setText("" + getMilliSecondsTotal() / 1000);
                Toast.makeText(getApplicationContext(), "Tolle Arbeit!", Toast.LENGTH_SHORT);
            }
        };
        cdt.start();
    }
}
