package htl_leonding.fiplyteam.fiply.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
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

public class FMusic extends Fragment {

    Button btnPlay, btnStop, btnBack, btnForward, btnList;
    Boolean listOpen = false;
    TextView tvSongname;
    SeekBar progressBar;
    FMusicList fMusicList;
    MediaPlayer mp = new MediaPlayer();
//    private static FMusic ourInstance = new FMusic();

//    public static FMusic getInstance() {
//        return ourInstance;
//    }

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
        tvSongname = (TextView) getActivity().findViewById(R.id.tvSongname);
        progressBar = (SeekBar) getActivity().findViewById(R.id.seekBar);

        fMusicList = new FMusicList();

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
        changeSong("test");
    }

    public void changeSong(String fileName) {
        try {
            mp.reset();
            mp.setDataSource(ReadMusic.PATH_MUSIC + File.separator + fileName + ".mp3"); //setzen der Datensource (Initialized-State)
            mp.prepare(); //abspielen ermöglichen (Prepared-State)
            progressBar.setProgress(0);
            progressBar.setMax(100);
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

    public Boolean getListOpen() {
        return listOpen;
    }

    public void setListOpen(Boolean listOpen) {
        this.listOpen = listOpen;
    }
}
