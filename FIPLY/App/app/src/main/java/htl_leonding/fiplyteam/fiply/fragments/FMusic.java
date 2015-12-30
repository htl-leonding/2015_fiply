package htl_leonding.fiplyteam.fiply.fragments;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.BufferUnderflowException;

import htl_leonding.fiplyteam.fiply.R;

public class FMusic extends Fragment {

    private String PATH_MUSIC = Environment.getExternalStorageDirectory().getAbsolutePath();
    Button btnPlay, btnStop, btnBack, btnForward;
    MediaPlayer mp = new MediaPlayer();

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

        btnBack.setVisibility(View.GONE);
        btnForward.setVisibility(View.GONE);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mp.isPlaying())
                {
                    mp.pause();
                    btnPlay.setText("Play");
                }
                else{
                    mp.start();
                    btnPlay.setText("Pause");
                }
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mp.stop(); //stoppen der Wiedergabe (Stopped-State)
                try {
                    mp.prepare(); //erneutes abspielen ermöglichen (Prepared-State)
                    mp.seekTo(0); //Position an den Beginn setzen damit der Song von Vorne beginnt
                    btnPlay.setText("Play");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        setupMusicPlayer(PATH_MUSIC, "test.mp3");
    }

    private void setupMusicPlayer(String path, String fileName) {
        Toast.makeText(getActivity(), path + File.separator + fileName, Toast.LENGTH_LONG).show();
        try {
            mp.setDataSource(path + File.separator + fileName); //setzen der Datensource (Initialized-State)
            mp.prepare(); //abspielen ermöglichen (Prepared-State)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
