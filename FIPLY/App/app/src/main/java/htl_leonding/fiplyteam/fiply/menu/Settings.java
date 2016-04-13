package htl_leonding.fiplyteam.fiply.menu;

import android.Manifest;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.music.ReadMusic;
import htl_leonding.fiplyteam.fiply.trainingssession.ListViewUebung;

public class Settings extends Activity {
    Button btMusic, btRefresh;
    ListView lvSongs;
    TextView tvSongsCount;
    ReadMusic rm;
    PlaylistSongsRepository psr;
    final public int REQUEST_CODE_ASK_PERMISSIONS = 123;

    /**
     * Wird beim Ersten Aufruf der SettingsActivity aufgerufen und dient dem setzen aller Ressourcen
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        btMusic = (Button) findViewById(R.id.btSettingsReadMusic);
        btRefresh = (Button) findViewById(R.id.btSettingsRefreshMusic);
        tvSongsCount = (TextView) findViewById(R.id.tvSettingsSongFoundCount);
        lvSongs = (ListView) findViewById(R.id.lvSettingsSongs);

        rm = ReadMusic.getInstance();
        PlaylistSongsRepository.setContext(this);
        psr = PlaylistSongsRepository.getInstance();
        tvSongsCount.setText("Songs in der Bibliothek: " + psr.getByPlaylistName("All").size());
        lvSongs.setAdapter(new SimpleAdapter(this, psr.getByPlaylistName("All"), R.layout.music_item,
                new String[]{"songTitle"}, new int[]{R.id.songTitle}));

        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckMusicPermissionAndReadMusic(Settings.this);
                refreshButtonStatus();
            }
        });
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckMusicPermissionAndReadMusic(Settings.this.getApplicationContext());
                fillPlaylistDb();
                btRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent mStartActivity = new Intent(Settings.this, SplashActivity.class);
                        PendingIntent mPendingIntent = PendingIntent.getActivity(Settings.this, 12345, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) Settings.this.getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
                        System.exit(0);
                    }
                });
            }
        });
        refreshButtonStatus();
    }

    /**
     * Überprüft ob die MusicPermission gegeben ist
     * Falls diese noch nicht gegeben ist, wird sie abgefragt
     * @param context
     */
    public void CheckMusicPermissionAndReadMusic(Context context) {
        int hasReadStoragePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessageOKCancel("Um Musik zu hören benötigt FIPLY die Erlaubnis auf den externen Speicher zugreifen zu dürfen.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(Settings.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                }, this);
                return;
            }
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        rm.ReadSongsIntoArrayList(context);
    }

    /**
     * Zeigt ein DialogFenster an, welches den User darüber informiert warum die App diese Permission benötigt.
     * @param message
     * @param okListener
     * @param activity
     */
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * Die "All"-Playlist wird in die Datenbank eingefügt
     */
    private void fillPlaylistDb() {
        if (!rm.getSongs().isEmpty()) {
            ArrayList<HashMap<String, String>> alt = psr.getByPlaylistName("All");
            ArrayList<HashMap<String, String>> neu = rm.getSongs();
            for (HashMap<String, String> itemAlt : alt) {
                if (!neu.contains(itemAlt)) {
                    Log.i("Songs entfernt", "Der Song " + itemAlt.toString() + " wurde entfernt.");
                    psr.deleteBySongPath(itemAlt.get("songPath"));
                }
            }
        }

        psr.reenterPlaylist("All", rm.getSongs());
        //Toast.makeText(getActivity(), "Es wurden " + rm.getSongs().size() + " Songs eingelesen!", Toast.LENGTH_SHORT).show();
        tvSongsCount.setText("Songs in der Bibliothek: " + psr.getByPlaylistName("All").size());
    }

    /**
     * Die Buttons zum einlesen der Music werden enabled oder disabled
     * @param enable
     */
    public void enableRefresh(Boolean enable){
        if(enable)
        {
            btRefresh.setEnabled(true);
            btRefresh.setAlpha(1f);
            btRefresh.setText(R.string.btSettingsRefreshMusic);
        }
        else
        {
            btRefresh.setEnabled(false);
            btRefresh.setAlpha(0.5f);
            btRefresh.setText(R.string.btSettingsCheckPermissions);
        }
    }

    /**
     * Je nachdem ob die READ_EXTERNAL_STORAGE-Permission gegeben ist wird enableRefresh mit passenden Parameter aufgerufen
     */
    private void refreshButtonStatus() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            enableRefresh(true);
        }
        else
        {
            enableRefresh(false);
        }
    }
}
