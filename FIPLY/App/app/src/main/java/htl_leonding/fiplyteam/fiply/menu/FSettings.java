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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.music.ReadMusic;

public class FSettings extends Fragment {
    Button btMusic, btRefresh;
    TextView tvSongsCount;
    ReadMusic rm;
    PlaylistSongsRepository psr;
    final public int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btMusic = (Button) getActivity().findViewById(R.id.btSettingsReadMusic);
        btRefresh = (Button) getActivity().findViewById(R.id.btSettingsRefreshMusic);
        tvSongsCount = (TextView) getActivity().findViewById(R.id.tvSettingsSongFoundCount);

        rm = ReadMusic.getInstance();
        PlaylistSongsRepository.setContext(getActivity());
        psr = PlaylistSongsRepository.getInstance();
        tvSongsCount.setText("Songs in der Bibliothek: " + psr.getByPlaylistName("All").size());

        btMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckMusicPermissionAndReadMusic(getActivity().getApplicationContext());
                refreshButtonStatus();
            }
        });
        btRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckMusicPermissionAndReadMusic(getActivity().getApplicationContext());
                fillPlaylistDb();
                btRefresh.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent mStartActivity = new Intent(getContext(), SplashActivity.class);
                        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), 12345, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                        AlarmManager mgr = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                        mgr.set(AlarmManager.RTC, System.currentTimeMillis(), mPendingIntent);
                        System.exit(0);
                    }
                });
            }
        });
    refreshButtonStatus();
    }

    public void CheckMusicPermissionAndReadMusic(Context context) {
        int hasReadStoragePermission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showMessageOKCancel("Um Musik zu hören benötigt FIPLY die Erlaubnis auf den externen Speicher zugreifen zu dürfen.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions(FSettings.this.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
                    }
                }, this.getActivity());
                return;
            }
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_ASK_PERMISSIONS);
            return;
        }
        rm.ReadSongsIntoArrayList(context);
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener, Activity activity) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

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

    private void refreshButtonStatus() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            enableRefresh(true);
        }
        else
        {
            enableRefresh(false);
        }
    }
}
