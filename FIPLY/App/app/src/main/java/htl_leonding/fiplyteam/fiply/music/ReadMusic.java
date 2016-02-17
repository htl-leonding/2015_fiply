package htl_leonding.fiplyteam.fiply.music;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ReadMusic {
    public static String PATH_MUSIC = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Music";
    private static ReadMusic instance;
    private static ArrayList<HashMap<String, String>> songs;

    private ReadMusic() {
    }

    public static ReadMusic getInstance() {
        if (ReadMusic.instance == null) {
            ReadMusic.instance = new ReadMusic();
        }
        return ReadMusic.instance;
    }

    public ArrayList<HashMap<String, String>> getSongsFromMusicFolder() {
        File home = new File(PATH_MUSIC);
        songs = new ArrayList<>();
        if (home.listFiles(new FileExtensionFilter()) != null) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());
                songs.add(song);
            }
        }
        return songs;
    }

    public static void ReadSongsIntoArrayList(Context context) {
        ContentResolver cr = context.getContentResolver();

        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE_KEY + " ASC";
        Cursor cur = cr.query(uri, null, selection, null, sortOrder);
        int count = 0;
        songs = new ArrayList<>();
        if(cur != null)
        {
            count = cur.getCount();

            if(count > 0)
            {
                while(cur.moveToNext())
                {
                    HashMap<String, String> hm = new HashMap<>();
                    hm.put("songTitle", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.TITLE)));
                    hm.put("songPath", cur.getString(cur.getColumnIndex(MediaStore.Audio.Media.DATA)));
                    Log.i("Songs", hm.toString());
                    songs.add(hm);
                }
            }
        }
        cur.close();
    }

    public static ArrayList<HashMap<String, String>> getSongs() {
        return songs;
    }
}
