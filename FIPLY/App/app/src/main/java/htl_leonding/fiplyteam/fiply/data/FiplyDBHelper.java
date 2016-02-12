package htl_leonding.fiplyteam.fiply.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.KeyValueEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PhasenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlaylistSongsEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.UebungenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.StatisticEntry;

public class FiplyDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FiplyDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "FiplyDB";
    private static final int DATABASE_VERSION = 4;

    private static FiplyDBHelper instance;

    public FiplyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static FiplyDBHelper getInstance(Context context) {
        if (instance == null)
            instance = new FiplyDBHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_UEBUNGEN_TABLE = "create table " + UebungenEntry.TABLE_NAME +
                " (" +
                UebungenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                UebungenEntry.COLUMN_NAME + " text not null, " +
                UebungenEntry.COLUMN_BESCHREIBUNG + " text not null, " +
                UebungenEntry.COLUMN_ANLEITUNG + " text not null, " +
                UebungenEntry.COLUMN_MUSKELGRUPPE + " text not null, " +
                UebungenEntry.COLUMN_SCHWIERIGKEIT + " text not null, " +
                UebungenEntry.COLUMN_VIDEO + " text not null, " +
                UebungenEntry.COLUMN_EQUIPMENT + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_UEBUNGEN_TABLE);
        db.execSQL(SQL_CREATE_UEBUNGEN_TABLE);

        final String SQL_CREATE_KEYVALUE_TABLE = "create table " + KeyValueEntry.TABLE_NAME +
                " (" +
                KeyValueEntry.COLUMN_KEY + " text primary key not null, " +
                KeyValueEntry.COLUMN_VALUE + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_KEYVALUE_TABLE);
        db.execSQL(SQL_CREATE_KEYVALUE_TABLE);

        final String SQL_CREATE_PHASEN_TABLE = "create table " + PhasenEntry.TABLE_NAME +
                " (" + PhasenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                PhasenEntry.COLUMN_STARTDATE + " text not null, " +
                PhasenEntry.COLUMN_ENDDATE + " text not null, " +
                PhasenEntry.COLUMN_PHASENNAME + " text not null, " +
                PhasenEntry.COLUMN_PHASENDAUER + " text not null, " +
                PhasenEntry.COLUMN_PAUSENDAUER + " text not null, " +
                PhasenEntry.COLUMN_SAETZE + " text not null, " +
                PhasenEntry.COLUMN_WIEDERHOLUNGEN + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_PHASEN_TABLE);
        db.execSQL(SQL_CREATE_PHASEN_TABLE);

        final String SQL_CREATE_INSTRUKTIONEN_TABLE = "create table " + InstruktionenEntry.TABLE_NAME +
                " (" + InstruktionenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                InstruktionenEntry.COLUMN_WOCHENTAG + " text not null, " +
                InstruktionenEntry.COLUMN_REPMAX + " text not null, " +
                InstruktionenEntry.COLUMN_UEBUNGSID + " text not null, " +
                InstruktionenEntry.COLUMN_PHASENID + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_INSTRUKTIONEN_TABLE);
        db.execSQL(SQL_CREATE_INSTRUKTIONEN_TABLE);

        final String SQL_CREATE_PLAYLISTSONGS_TABLE = "create table " + PlaylistSongsEntry.TABLE_NAME +
                " (" + PlaylistSongsEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                PlaylistSongsEntry.COLUMN_PLAYLISTNAME + " text not null, " +
                PlaylistSongsEntry.COLUMN_SONGTITLE + " text not null, " +
                PlaylistSongsEntry.COLUMN_SONGPATH + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_PLAYLISTSONGS_TABLE);
        db.execSQL(SQL_CREATE_PLAYLISTSONGS_TABLE);

        final String SQL_CREATE_STATISTICS_TABLE = "create table " + StatisticEntry.TABLE_NAME +
                " (" + StatisticEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                StatisticEntry.COLUMN_DATE + " text not null, " +
                StatisticEntry.COLUMN_LIFTEDWEIGHT + " text not null, " +
                StatisticEntry.COLUMN_MOOD + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_STATISTICS_TABLE);
        db.execSQL(SQL_CREATE_STATISTICS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UebungenEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + KeyValueEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PhasenEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + InstruktionenEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + PlaylistSongsEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + StatisticEntry.TABLE_NAME);
        onCreate(db);
    }
}
