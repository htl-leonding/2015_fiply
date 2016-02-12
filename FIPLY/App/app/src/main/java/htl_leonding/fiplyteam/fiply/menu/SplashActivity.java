package htl_leonding.fiplyteam.fiply.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;
import htl_leonding.fiplyteam.fiply.music.ReadMusic;

public class SplashActivity extends Activity {

    ReadMusic rm;
    UebungenRepository rep;
    KeyValueRepository kvr;
    PlaylistSongsRepository psr;
    StatisticRepository str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UebungenRepository.setContext(this);
        KeyValueRepository.setContext(this);
        StatisticRepository.setContext(this);
        PlaylistSongsRepository.setContext(this);
        rep = UebungenRepository.getInstance();
        kvr = KeyValueRepository.getInstance();
        psr = PlaylistSongsRepository.getInstance();
        str = StatisticRepository.getInstance();
        rm = ReadMusic.getInstance();

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
                fillPlaylistDb();
                kvr.setDefaultUserSettings();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            Intent openMain = new Intent("fiply.MAINACTIVITY");
            //Diese Flags verhindern dass man durch Klicken des BackButtons auf diese Activity zurückkommt.
            openMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(openMain);
            return "Success";
        }

        private void fillPlaylistDb() {
            if(!rm.getSongs().isEmpty())
            {
                ArrayList<HashMap<String, String>> alt = psr.getByPlaylistName("All");
                ArrayList<HashMap<String, String>> neu = rm.getSongs();
                for (HashMap<String, String> itemAlt : alt) {
                    if (!neu.contains(itemAlt)) {
                        psr.deleteBySongPath(itemAlt.get("songPath"));
                    }
                }
            }
            psr.reenterPlaylist("All", rm.getSongs());
        }
    }
}
