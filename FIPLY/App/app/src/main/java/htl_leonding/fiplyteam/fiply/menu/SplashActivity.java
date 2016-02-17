package htl_leonding.fiplyteam.fiply.menu;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.PlanRepository;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.data.StatisticRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;
import htl_leonding.fiplyteam.fiply.music.ReadMusic;
import htl_leonding.fiplyteam.fiply.trainingsplan.GenerateAllgemein;
import htl_leonding.fiplyteam.fiply.trainingsplan.GeneratePhTwoMaxiPh3Muskel;
import htl_leonding.fiplyteam.fiply.trainingsplan.GeneratePhTwoMuskelPh3Kraft;
import htl_leonding.fiplyteam.fiply.trainingsplan.Trainingsphase;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;

public class SplashActivity extends Activity {

    ReadMusic rm;
    UebungenRepository uer;
    KeyValueRepository kvr;
    PlaylistSongsRepository psr;
    StatisticRepository str;
    PlanRepository prep;
    InstruktionenRepository instRep;
    PhasenRepository phasenRep;
    List<Trainingsphase> trainingsphaseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        UebungenRepository.setContext(this);
        KeyValueRepository.setContext(this);
        StatisticRepository.setContext(this);
        PlaylistSongsRepository.setContext(this);
        uer = UebungenRepository.getInstance();
        kvr = KeyValueRepository.getInstance();
        psr = PlaylistSongsRepository.getInstance();
        str = StatisticRepository.getInstance();
        rm = ReadMusic.getInstance();

        PlanRepository.setContext(this);
        prep = PlanRepository.getInstance();

        InstruktionenRepository.setContext(this);
        PhasenRepository.setContext(this);

        instRep = InstruktionenRepository.getInstance();
        phasenRep = PhasenRepository.getInstance();
        trainingsphaseList = new LinkedList<Trainingsphase>();


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
                try {
                    if (!kvr.getKeyValue("firstStart").getString(1).equals("false")) {
                        reCreateDatabaseOnFirstStart();
                    }
                } catch (Exception e) {
                    reCreateDatabaseOnFirstStart();
                }
                uer.insertAllExercises();
                rm.ReadSongsIntoArrayList(getApplicationContext());
                fillPlaylistDb();
                kvr.setDefaultUserSettings();
                fillTestTrainingsgplan();
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

        private void reCreateDatabaseOnFirstStart() {
            kvr.reCreateKeyValueTable();
            uer.reCreateUebungenTable();
            psr.reCreatePlaylistSongsTable();
            str.reCreateUebungenTable();
            prep.reCreatePlanTable();
            phasenRep.reCreatePhasenTable();
            kvr.insertKeyValue("firstStart", "false");
            Log.wtf("DatabaseOnFirstStart?", "reCreatedDatabaseOnFirstStart");
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
        }

        private void fillTestTrainingsgplan() {
            phasenRep.deleteAll();
            instRep.deleteAll();
            prep.deleteAll();

            DateFormat format = new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH);
            String[] actualdays = new String[]{"Montag", "Donnerstag", "Samstag"};
            Date startDate = new Date();
            Calendar car = Calendar.getInstance();
            car.set(2015, 12, 1);
            startDate.setTime(car.getTimeInMillis());
            trainingsphaseList = new LinkedList<Trainingsphase>();
            GenerateAllgemein allgemein = new GenerateAllgemein(true, 1, actualdays, startDate);
            trainingsphaseList.add(allgemein.getTPhase());
            GeneratePhTwoMuskelPh3Kraft phaseZweiMuskel = new GeneratePhTwoMuskelPh3Kraft(actualdays, allgemein.getTPhase().getEndDate(), "Muskelaufbau", new String[]{"Bauch", "Beine", "Brust"});
            trainingsphaseList.add(phaseZweiMuskel.getTPhase());
            GeneratePhTwoMaxiPh3Muskel phaseDreiMuskel = new GeneratePhTwoMaxiPh3Muskel(phaseZweiMuskel.getTPhase().getEndDate(), "Muskelaufbau", actualdays);
            trainingsphaseList.add(phaseDreiMuskel.getTPhase());
            String ziel = getResources().getString(R.string.trainingszielMuskelaufbau);
            String planName = "Default Trainingsplan";

            String planStartDate = format.format(trainingsphaseList.get(0).getStartDate());
            String planEndDate = format.format(trainingsphaseList.get(trainingsphaseList.size() - 1).getEndDate());
            prep.insertPhase(planName, planStartDate, planEndDate, ziel);
            Cursor cplan = prep.getPlanByName(planName);
            cplan.moveToFirst();
            int iPlanId = cplan.getColumnIndex(FiplyContract.PlanEntry.COLUMN_ROWID);
            String planId = cplan.getString(iPlanId);

            for (Trainingsphase phase : trainingsphaseList) {
                String dbStartDate = format.format(phase.getStartDate());
                String dbEndDate = format.format(phase.getEndDate());
                phasenRep.insertPhase(dbStartDate, dbEndDate,
                        phase.getPhasenName(), String.valueOf(phase.getPhasenDauer()), String.valueOf(phase.getPausenDauer()),
                        String.valueOf(phase.getSaetze()), String.valueOf(phase.getWiederholungen()), planId);
                Cursor c = null;
                try {
                    c = phasenRep.getPhaseByStartDate(format.parse(dbStartDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                c.moveToFirst();
                int index = c.getColumnIndex(FiplyContract.PhasenEntry.COLUMN_ROWID);
                String rowid = c.getString(index);
                for (Uebung ueb : phase.getUebungList()) {
                    instRep.insertUebung(ueb.getWochenTag(), String.valueOf(ueb.getRepmax()), ueb.getUebungsID(), rowid);
                }
            }
            Cursor c = phasenRep.getAllPhasen();
            Cursor c2;
            int iRowId = c.getColumnIndex(FiplyContract.PhasenEntry.COLUMN_ROWID);
            int iPhasenname = c.getColumnIndex(FiplyContract.PhasenEntry.COLUMN_PHASENNAME);
            int iRowName;
            String rowid;
            String phasenname;
            String uebid;
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
                rowid = c.getString(iRowId);
                phasenname = c.getString(iPhasenname);
                c2 = instRep.getInstruktionByPhasenId(rowid);
                iRowName = c2.getColumnIndex(FiplyContract.InstruktionenEntry.COLUMN_ROWID);
                for (c2.moveToFirst(); !c2.isAfterLast(); c2.moveToNext()) {
                    uebid = c2.getString(iRowName);
                }
            }
        }
    }
}
