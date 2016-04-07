package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlaylistSongsEntry;

public class PlaylistSongsRepository {
    private static Context repoContext;
    private static PlaylistSongsRepository instance = null;

    SQLiteDatabase db = getWritableDatabase();

    public static PlaylistSongsRepository getInstance() {
        if (instance == null)
            instance = new PlaylistSongsRepository();
        return instance;
    }

    public static void setContext(Context context) {
        repoContext = context;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getWriteableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getReadableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getReadableDatabase();
    }

    /**
     * Liefert eine Playlist über den Namen zurück
     * @param pname Name der Playlist
     * @return ArrayList von HashMaps mit allen Sogns der Playliste
     */
    public ArrayList<HashMap<String, String>> getByPlaylistName(String pname) {
        Cursor c = db.query(true, PlaylistSongsEntry.TABLE_NAME, new String[]{
                        PlaylistSongsEntry.COLUMN_SONGPATH,
                        PlaylistSongsEntry.COLUMN_SONGTITLE},
                PlaylistSongsEntry.COLUMN_PLAYLISTNAME + "=" + "'" + pname + "'",
                null, null, null, PlaylistSongsEntry.COLUMN_SONGTITLE + " ASC", null);
        if (c != null) {
            c.moveToFirst();
        }

        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        HashMap<String, String> item;
        for (int i = 0; i < c.getCount(); i++) {
            item = new HashMap<>();
            item.put("songPath", c.getString(0));
            item.put("songTitle", c.getString(1));
            list.add(item);
            c.moveToNext();
        }
        return list;
    }

    /**
     * Löscht ene bestimmte Playlist und fügt diese anschließend wieder ein
     * @param pname Name der Playlist
     * @param list Playlist die eingefügt werden soll
     */
    public void reenterPlaylist(String pname, ArrayList<HashMap<String, String>> list) {
        deleteByPlaylistName(pname);
        insertMany(pname, list);
    }

    /**
     * Fügt mehrere Songs ein
     * @param pname Name der Playlist in die eingefügt werden soll
     * @param list Liste von Songs die eingefügt werden soll
     */
    public void insertMany(String pname, ArrayList<HashMap<String, String>> list) {
        ContentValues initialValues;
        for (HashMap<String, String> item : list) {
            initialValues = new ContentValues();
            initialValues.put(PlaylistSongsEntry.COLUMN_PLAYLISTNAME, pname);
            initialValues.put(PlaylistSongsEntry.COLUMN_SONGPATH, item.get("songPath"));
            initialValues.put(PlaylistSongsEntry.COLUMN_SONGTITLE, item.get("songTitle"));
            db.insert(PlaylistSongsEntry.TABLE_NAME, null, initialValues);
        }
    }

    /**
     * Löscht eine Playliste über deren namen
     * @param name Playlistname
     * @return
     */
    public long deleteByPlaylistName(String name) {
        return db.delete(PlaylistSongsEntry.TABLE_NAME, PlaylistSongsEntry.COLUMN_PLAYLISTNAME + "=?", new String[]{name});
    }

    /**
     * Löscht einen Song aus der Playlist
     * @param path Pfad des zu löschenden Songs
     * @return
     */
    public long deleteBySongPath(String path) {
        return db.delete(PlaylistSongsEntry.TABLE_NAME, PlaylistSongsEntry.COLUMN_SONGPATH + "=?", new String[]{path});
    }

    /**
     * Löscht alle Einträge in der PlaylistSongs-Tabelle
     * @return
     */
    public long deleteAll() {
        return db.delete(PlaylistSongsEntry.TABLE_NAME, null, null);
    }

    /**
     * Liefert die Namen aller Playlisten zurück
     * @return
     */
    public List<String> getPlaylists() {
        Cursor c = db.query(true, PlaylistSongsEntry.TABLE_NAME, new String[]{
                        PlaylistSongsEntry.COLUMN_PLAYLISTNAME},
                null, null, PlaylistSongsEntry.COLUMN_PLAYLISTNAME, null, PlaylistSongsEntry.COLUMN_PLAYLISTNAME + " ASC", null);
        if (c != null) {
            c.moveToFirst();
        }
        List<String> list = new LinkedList<>();
        for (int i = 0; i < c.getCount(); i++) {
            list.add(c.getString(0));
            c.moveToNext();
        }
        return list;
    }

    /**
     * Löscht die Tabelle und erstellt sie neu (wird nur beim ersten Start der App ausgeführt)
     */
    public void reCreatePlaylistSongsTable() {
        db.execSQL("DROP TABLE IF EXISTS " + PlaylistSongsEntry.TABLE_NAME + ";");
        db.execSQL("create table " + PlaylistSongsEntry.TABLE_NAME +
                " (" + PlaylistSongsEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                PlaylistSongsEntry.COLUMN_PLAYLISTNAME + " text not null, " +
                PlaylistSongsEntry.COLUMN_SONGTITLE + " text not null, " +
                PlaylistSongsEntry.COLUMN_SONGPATH + " text not null" +
                ");");
    }
}
