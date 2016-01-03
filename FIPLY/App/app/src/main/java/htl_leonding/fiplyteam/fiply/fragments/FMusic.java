package htl_leonding.fiplyteam.fiply.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.ReadMusic;
import htl_leonding.fiplyteam.fiply.displayFragment;

public class FMusic extends Fragment implements SeekBar.OnSeekBarChangeListener {

    Button btnPlay, btnStop, btnBack, btnForward, btnList;
    Boolean listOpen = false;
    TextView tvSongname, tvCurrentDur, tvTotalDur;
    SeekBar progressBar;
    FMusicList fMusicList;
    MediaPlayer mp = new MediaPlayer();
    Handler mHandler = new Handler();

//    private static FMusic ourInstance = new FMusic();

//    public static FMusic getInstance() {
//        return ourInstance;
//    }
private Runnable mUpdateDurTask = new Runnable() {
    @Override
    public void run() {
        long totalDur = mp.getDuration();
        long currentDur = mp.getCurrentPosition();

        tvCurrentDur.setText(millisecondsToHMS(currentDur));
        tvTotalDur.setText(millisecondsToHMS(totalDur));

        int progress = getProgressPercentage(currentDur, totalDur);
        progressBar.setProgress(progress);
        mHandler.postDelayed(this, 100);
    }
};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_music, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnPlay = (Button) getActivity().findViewById(R.id.btnMuPlay);
        btnStop = (Button) getActivity().findViewById(R.id.btnMuStop);
        btnBack = (Button) getActivity().findViewById(R.id.btnMuBack);
        btnForward = (Button) getActivity().findViewById(R.id.btnMuForward);
        btnList = (Button) getActivity().findViewById(R.id.btnMuList);
        tvSongname = (TextView) getActivity().findViewById(R.id.tvMuSongname);
        tvCurrentDur = (TextView) getActivity().findViewById(R.id.tvMuCurrentDur);
        tvTotalDur = (TextView) getActivity().findViewById(R.id.tvMuTotalDur);
        progressBar = (SeekBar) getActivity().findViewById(R.id.seekBarMu);

        fMusicList = new FMusicList();

        progressBar.setOnSeekBarChangeListener(this);

        btnBack.setVisibility(View.GONE);
        btnForward.setVisibility(View.GONE);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stop();
            }
        });

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getListOpen()) {
                    setListOpen(false);
                    FUebungDetail fragmentUebung = new FUebungDetail();
                    Bundle args = new Bundle();
                    args.putString("name", "TestName");
                    args.putString("beschreibung", "TestBeschreibung");
                    args.putString("anleitung", "TestAnleitung");
                    args.putString("muskelgruppe", "TestMuskelgruppe");
                    args.putString("zielgruppe", "TestZielgruppe");
                    args.putString("video", "https://www.youtube.com/embed/ykJmrZ5v0Oo");
                    args.putBoolean("showVideo", false);
                    fragmentUebung.setArguments(args);

                    displayFragment.displayTSUebung(fragmentUebung, getFragmentManager());
                } else {
                    setListOpen(true);
                    displayFragment.displayTSUebung(fMusicList, getFragmentManager());
                }
            }
        });
        changeSong("test10min");
    }

    public void changeSong(String fileName) {
        try {
            mp.reset();
            mp.setDataSource(ReadMusic.PATH_MUSIC + File.separator + fileName + ".mp3"); //setzen der Datensource (Initialized-State)
            mp.prepare(); //abspielen ermöglichen (Prepared-State)
            progressBar.setProgress(0);
            progressBar.setMax(100);
            updateProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvSongname.setText(fileName);
    }

    public void play() {
        if (mp.isPlaying()) {
            mp.pause();
            btnPlay.setText("Start");
        } else {
            mp.start();
            btnPlay.setText("Pause");
        }
    }

    public void stop() {
        try {
            mp.stop();
            mp.prepare();
            mp.seekTo(0);
            progressBar.setProgress(0);
            progressBar.setMax(100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        btnPlay.setText("Play");
    }

    private void updateProgressBar() {
        mHandler.postDelayed(mUpdateDurTask, 100);
    }

    public String millisecondsToHMS(long milliseconds) {
        String hms = "";
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60)) / 1000;

        if (hours > 0)
            hms = hours + ":";

        if (minutes < 10 && hours > 0)
            hms += "0" + minutes + ":";
        else
            hms += minutes + ":";

        if (seconds < 10)
            hms += "0" + seconds;
        else
            hms += seconds;

        return hms;
    }

    public int getProgressPercentage(long currentDur, long totalDur) {
        Double percentage;

        long currentSeconds = (int) (currentDur / 1000);
        long totalSeconds = (int) (totalDur / 1000);

        percentage = (((double) currentSeconds) / totalSeconds) * 100;
        return percentage.intValue();
    }

    public int setCurrentDuration(int progress, int totalDur) {
        return (int) (((double) progress / 100) * ((double) totalDur / 1000)) * 1000;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateDurTask);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mUpdateDurTask);
        mp.seekTo(setCurrentDuration(seekBar.getProgress(), mp.getDuration()));
        updateProgressBar();
    }

    public Boolean getListOpen() {
        return listOpen;
    }

    public void setListOpen(Boolean listOpen) {
        this.listOpen = listOpen;
    }
}


