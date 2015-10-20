package htl_leonding.fiplyteam.fiply;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        try {
            sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Intent openMain = new Intent("fiply.MAINACTIVITY");
        startActivity(openMain);
    }
}
