package htl_leonding.fiplyteam.fiply.menu;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

/**
 * Quelle: http://it-ride.blogspot.co.at/2010/10/android-implementing-notification.html
 */
public class NotificationService extends Service {

    KeyValueRepository kvr;
    private PowerManager.WakeLock mWakeLock;

    /**
     * Wird beim Ersten Aufruf des NotificationServices aufgerufen und dient dem setzen des KeyValueRepository-Context
     */
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

    /**
     * This is where we initialize. We call this when onStart/onStartCommand is
     * called by the system. We won't do anything with the intent here, and you
     * probably won't, either.
     */
    private void handleIntent(Intent intent) {
        // obtain the wake lock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm");
        mWakeLock.acquire();

        // do the actual work, in a separate thread
        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        /**
         * Hier werden Notifications erstellt und anschließend angezeigt.
         */
        @Override
        protected Void doInBackground(Void... params) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            //if(cal.get(Calendar.HOUR_OF_DAY) >= 9 && cal.get(Calendar.HOUR_OF_DAY) <= 20) {
                NotificationCompat.Builder mBuilder = null;
                mBuilder = new NotificationCompat.Builder(getApplication())
                        .setSmallIcon(getNotificationIcon())
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.notificationicon))
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

        /**
         * Der Service wird anschließend gestoppt um alle Ressourcen wieder freizugeben
         * @param result
         */
        @Override
        protected void onPostExecute(Void result) {
            stopSelf();
        }
    }

    /**
     * Dient dazu das richtige Icon (je nach Android-Version) anzuzeigen
     */
    private int getNotificationIcon() {
        boolean useWhiteIcon = (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.silhouettesmall : R.drawable.notificationicon;
    }

    /**
     * This is called on 2.0+ (API level 5 or higher). Returning
     * START_NOT_STICKY tells the system to not restart the service if it is
     * killed because of poor resource (memory/cpu) conditions.
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handleIntent(intent);
        return START_NOT_STICKY;
    }

    /**
     * In onDestroy() we release our wake lock. This ensures that whenever the
     * Service stops (killed for resources, stopSelf() called, etc.), the wake
     * lock will be released.
     */
    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }
}