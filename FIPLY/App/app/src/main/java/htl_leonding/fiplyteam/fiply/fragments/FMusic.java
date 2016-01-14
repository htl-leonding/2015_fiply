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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.ReadMusic;
import htl_leonding.fiplyteam.fiply.displayFragment;

public class FMusic extends Fragment implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    Button btnPlay, btnStop, btnLast, btnNext, btnList;
    Boolean listOpen = false;
    TextView tvSongname, tvCurrentDur, tvTotalDur;
    SeekBar progressBar;
    FMusicList fMusicList;
    MediaPlayer mp = new MediaPlayer();
    ReadMusic rm = ReadMusic.getInstance();
    Handler mHandler = new Handler();
    ArrayList<HashMap<String, String>> playlist;
    int songIndex;

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
        btnLast = (Button) getActivity().findViewById(R.id.btnMuLast);
        btnNext = (Button) getActivity().findViewById(R.id.btnMuNext);
        btnList = (Button) getActivity().findViewById(R.id.btnMuList);
        tvSongname = (TextView) getActivity().findViewById(R.id.tvMuSongname);
        tvCurrentDur = (TextView) getActivity().findViewById(R.id.tvMuCurrentDur);
        tvTotalDur = (TextView) getActivity().findViewById(R.id.tvMuTotalDur);
        progressBar = (SeekBar) getActivity().findViewById(R.id.seekBarMu);

        fMusicList = new FMusicList();
        playlist = new ArrayList<>();
        progressBar.setOnSeekBarChangeListener(this);
        mp.setOnCompletionListener(this);


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
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSong();
            }
        });
        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSong();
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
        refreshPlaylist();
        progressBar.setMax(100);
        changeSong(0);
    }

    public void changeSong(int songIndex) {
        configureMediaPlayer(!getPlaylist().isEmpty());

        setSongIndex(songIndex);
        try {
            mp.reset();
            //mp.setDataSource(getPlaylist().get(songIndex).get("songPath") + File.separator + getPlaylist().get(songIndex).get("songTitle") + ".mp3"); //setzen der Datensource (Initialized-State)
            mp.setDataSource(getPlaylist().get(getSongIndex()).get("songPath")); //setzen der Datensource (Initialized-State)
            mp.prepare(); //abspielen ermöglichen (Prepared-State)
            progressBar.setProgress(0);
            updateProgressBar();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //tvSongname.setText(getPlaylist().get(songIndex).get("songTitle"));
    }

    private void configureMediaPlayer(boolean enable) {
        btnPlay.setClickable(enable);
        btnStop.setClickable(enable);
        btnLast.setClickable(enable);
        btnNext.setClickable(enable);
        btnList.setClickable(enable);
        tvSongname.setClickable(enable);
        tvCurrentDur.setClickable(enable);
        tvTotalDur.setClickable(enable);
        progressBar.setClickable(enable);
        if (enable)
            progressBar.setVisibility(View.VISIBLE);
        else
            progressBar.setVisibility(View.INVISIBLE);
    }

    public void play() {
        if (mp.isPlaying()) {
            mp.pause();
            btnPlay.setText(R.string.songPlay);
        } else {
            mp.start();
            btnPlay.setText(R.string.songPause);
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
        btnPlay.setText(R.string.songPlay);
    }

    public void nextSong() {
        if ((getSongIndex() + 1) < getPlaylist().size())
            changeSong(getSongIndex() + 1);
        else
            changeSong(0);
        play();
    }

    public void lastSong() {
        if (getSongIndex() > 0)
            changeSong(getSongIndex() - 1);
        else
            changeSong(getPlaylist().size() - 1);
        play();
    }

    public void refreshPlaylist() {
        setPlaylist(rm.getSongs());
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

    @Override
    public void onCompletion(MediaPlayer mp) {
        nextSong();
    }

    public Boolean getListOpen() {
        return listOpen;
    }

    public void setListOpen(Boolean listOpen) {
        this.listOpen = listOpen;
    }

    public ArrayList<HashMap<String, String>> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<HashMap<String, String>> playlist) {
        this.playlist = playlist;
    }

    public int getSongIndex() {
        return songIndex;
    }

    public void setSongIndex(int songIndex) {
        this.songIndex = songIndex;
    }
}


