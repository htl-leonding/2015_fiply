package htl_leonding.fiplyteam.fiply.music;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.trainingssession.displayFragment;
import htl_leonding.fiplyteam.fiply.uebungskatalog.FUebungDetail;

public class FMusic extends Fragment implements MediaPlayer.OnCompletionListener, SeekBar.OnSeekBarChangeListener {

    ImageButton btnPlay, btnStop, btnLast, btnNext, btnList;
    Boolean listOpen = false;
    TextView tvSongname, tvCurrentDur, tvTotalDur;
    SeekBar progressBar;
    FMusicList fMusicList;
    MediaPlayer mp = new MediaPlayer();
    Handler mHandler = new Handler();
    ArrayList<HashMap<String, String>> playlist;
    PlaylistSongsRepository psrep;
    int songIndex;
    String aktPlaylist;

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

        btnPlay = (ImageButton) getActivity().findViewById(R.id.btnMuPlay);
        btnStop = (ImageButton) getActivity().findViewById(R.id.btnMuStop);
        btnLast = (ImageButton) getActivity().findViewById(R.id.btnMuLast);
        btnNext = (ImageButton) getActivity().findViewById(R.id.btnMuNext);
        btnList = (ImageButton) getActivity().findViewById(R.id.btnMuList);
        tvSongname = (TextView) getActivity().findViewById(R.id.tvMuSongname);
        tvCurrentDur = (TextView) getActivity().findViewById(R.id.tvMuCurrentDur);
        tvTotalDur = (TextView) getActivity().findViewById(R.id.tvMuTotalDur);
        progressBar = (SeekBar) getActivity().findViewById(R.id.seekBarMu);

        fMusicList = new FMusicList();
        playlist = new ArrayList<>();
        progressBar.setOnSeekBarChangeListener(this);
        mp.setOnCompletionListener(this);
        PlaylistSongsRepository.setContext(getActivity());
        psrep = PlaylistSongsRepository.getInstance();

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
                    btnList.setImageResource(R.drawable.listviewunpressed);
                    setListOpen(false);
                    displayFragment.displayTSInstruktionAgain(getFragmentManager());
                } else {
                    btnList.setImageResource(R.drawable.listviewpressed);
                    setListOpen(true);
                    displayFragment.displayTSMusicList(fMusicList, getFragmentManager());
                }
            }
        });
        aktPlaylist = "All";
        refreshPlaylist();
        progressBar.setMax(100);
        setPlaylist(psrep.getByPlaylistName(aktPlaylist));
        changeSong(0, aktPlaylist);
    }

    public void changeSong(int songIndex, String playlist) {
        aktPlaylist = playlist;
        configureMediaPlayer(!getPlaylist().isEmpty());
        setPlaylist(psrep.getByPlaylistName(aktPlaylist));
        if (getPlaylist().isEmpty()) {
            configureMediaPlayer(false);
        } else {
            configureMediaPlayer(true);
            tvSongname.setText(getPlaylist().get(songIndex).get("songTitle"));
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
            tvSongname.setText(getPlaylist().get(songIndex).get("songTitle"));
        }
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
            btnPlay.setImageResource(R.drawable.play);
        } else {
            mp.start();
            btnPlay.setImageResource(R.drawable.pause);
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
        btnPlay.setImageResource(R.drawable.play);
    }

    public void nextSong() {
        if ((getSongIndex() + 1) < getPlaylist().size())
            changeSong(getSongIndex() + 1, aktPlaylist);
        else
            changeSong(0, aktPlaylist);
        play();
    }

    public void lastSong() {
        if (getSongIndex() > 0)
            changeSong(getSongIndex() - 1, aktPlaylist);
        else
            changeSong(getPlaylist().size() - 1, aktPlaylist);
        play();
    }

    public void refreshPlaylist() {
        setPlaylist(psrep.getByPlaylistName(aktPlaylist));
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


