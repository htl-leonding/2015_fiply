package htl_leonding.fiplyteam.fiply.menu;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class Reset extends Activity {
    KeyValueRepository kvr;

    /**
     * Wird beim Ersten Aufruf der ResetActivity aufgerufen und dient dem setzen aller Ressourcen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        KeyValueRepository.setContext(this);
        kvr = KeyValueRepository.getInstance();
        kvr.deleteAllKeyValues();

        Intent mStartActivity = new Intent(this, SplashActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 12345, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager mgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
        System.exit(0);
    }
}
