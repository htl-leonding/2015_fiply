package htl_leonding.fiplyteam.fiply.menu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class NotificationService extends Service {

    KeyValueRepository kvr;
    private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate() {
        super.onCreate();
        KeyValueRepository.setContext(this);
        kvr = KeyValueRepository.getInstance();
    }

    /**
     * Simply return null, since our Service will not be communicating with
     * any other components. It just does its work silently.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void handleIntent(Intent intent) {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm");
        mWakeLock.acquire();

        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            //if(cal.get(Calendar.HOUR_OF_DAY) >= 9 && cal.get(Calendar.HOUR_OF_DAY) <= 20) {
                NotificationCompat.Builder mBuilder = null;
                mBuilder = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(getNotificationIcon())
                        .setContentTitle("FIPLY")
                        .setContentText("Notifications Work!")
                        .setAutoCancel(true);
                Intent resultIntent = new Intent(getApplication(), MainActivity.class);
                TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplication());
                stackBuilder.addParentStack(MainActivity.class);
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(0,
                                PendingIntent.FLAG_UPDATE_CURRENT);
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuilder.build());
            //}
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            stopSelf();
        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.splash1 : R.drawable.splash1;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}