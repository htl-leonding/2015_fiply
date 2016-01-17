package htl_leonding.fiplyteam.fiply;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;

import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

import static java.lang.Thread.sleep;

public class SplashActivity extends Activity {

    Context context;
    UebungenRepository rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this;
        UebungenRepository.setContext(context);
        rep = UebungenRepository.getInstance();
        SleepIntentTask sleepIntentTask = new SleepIntentTask();
        sleepIntentTask.execute("");
    }

    /**
     * Dieser Task führt zuerst ein Sleep aus bevor er einen Intent auf MainActivity durchführt
     */
    public class SleepIntentTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                rep.insertAllExercises();
                sleep(3500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Intent openMain = new Intent("fiply.MAINACTIVITY");
            //Diese Flags verhindern dass man durch Klicken des BackButtons auf diese Activity zurückkommt.
            openMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(openMain);
            return "Success";
        }
    }

}
