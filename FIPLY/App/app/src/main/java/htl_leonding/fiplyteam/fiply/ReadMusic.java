package htl_leonding.fiplyteam.fiply;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadMusic {
    public static String PATH_MUSIC = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music";
    private ArrayList<HashMap<String, String>> songs = new ArrayList<>();

    public ArrayList<HashMap<String, String>> getSongs() {
        File home = new File(PATH_MUSIC);

        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());
                songs.add(song);
            }
        }
        return songs;
    }
}