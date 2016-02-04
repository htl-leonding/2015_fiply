package htl_leonding.fiplyteam.fiply.Music;

import android.media.MediaPlayer;

import java.io.File;
import java.io.IOException;

public class MusicControl {
    private static MusicControl ourInstance = new MusicControl();
    MediaPlayer mp;

    private MusicControl() {
        mp = new MediaPlayer();
    }

    public static MusicControl getInstance() {
        return ourInstance;
    }

    /*
        public void setupMusic(String fileName) {
            //Toast.makeText(getActivity(), path + File.separator + fileName, Toast.LENGTH_LONG).show();
            try {
                mp.stop();
                mp.setDataSource(ReadMusic.PATH_MUSIC + File.separator + fileName); //setzen der Datensource (Initialized-State)
                mp.prepare(); //abspielen ermöglichen (Prepared-State)
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    */
    public void changeSong(String fileName) {
        try {
            //mp.reset();
            mp.setDataSource(ReadMusic.PATH_MUSIC + File.separator + fileName); //setzen der Datensource (Initialized-State)
            mp.prepare(); //abspielen ermöglichen (Prepared-State)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isPlaying() {
        return mp.isPlaying();
    }

    public void start() {
        mp.pause();
    }

    public void pause() {
        mp.pause();
    }

    public void stop() {
        try {
            mp.stop();
            mp.prepare();
            mp.seekTo(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
