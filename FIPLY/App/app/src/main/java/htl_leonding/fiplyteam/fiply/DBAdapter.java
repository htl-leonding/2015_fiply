package htl_leonding.fiplyteam.fiply;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_MUSKELGRUPPE = "muskelgruppe";
    public static final String KEY_BESCHREIBUNG = "beschreibung";
    public static final String KEY_ANLEITUNG = "anleitung";
    public static final String KEY_TIPP = "tipp";
    public static final String KEY_VIDEO = "video";

    private static final String TAG = "DBAdapter";
    private static final String DATABASE_NAME = "FiplyDB";
    private static final String DATABASE_TABLE = "uebungen";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table " + DATABASE_TABLE +
            " (" + KEY_ROWID + " integer primary key autoincrement, " +
            KEY_NAME + " text not null, " +
            KEY_BESCHREIBUNG + " text not null, " +
            KEY_ANLEITUNG + " text not null, " +
            KEY_MUSKELGRUPPE + "text not null, " +
            KEY_TIPP +
            KEY_VIDEO +
            ");";

    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    public DBAdapter open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public long insertTitle(String name, String beschreibung, String anleitung,
                            String muskelgruppe, String tipp, String video) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_BESCHREIBUNG, beschreibung);
        initialValues.put(KEY_ANLEITUNG, anleitung);
        initialValues.put(KEY_MUSKELGRUPPE, muskelgruppe);
        initialValues.put(KEY_TIPP, tipp);
        initialValues.put(KEY_VIDEO, video);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteTitle(long rowId) {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllUebungen() {
        return db.query(DATABASE_TABLE, new String[]{
                        KEY_ROWID,
                        KEY_NAME,
                        KEY_BESCHREIBUNG,
                        KEY_ANLEITUNG,
                        KEY_MUSKELGRUPPE,
                        KEY_TIPP,
                        KEY_VIDEO},
                null, null, null, null, null);
    }




    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }

}
