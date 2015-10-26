package htl_leonding.fiplyteam.fiply.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.UebungenEntry;

public class FiplyDBHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = FiplyDBHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "FiplyDB";
    private static final int DATABASE_VERSION = 2;

    private static FiplyDBHelper instance;

    public FiplyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static FiplyDBHelper getInstance(Context context){
        if (instance == null)
            instance = new FiplyDBHelper(context);
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_UEBUNGEN_TABLE = "create table " + UebungenEntry.TABLE_NAME +
                " (" + UebungenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                UebungenEntry.COLUMN_NAME + " text not null, " +
                UebungenEntry.COLUMN_BESCHREIBUNG + " text not null, " +
                UebungenEntry.COLUMN_ANLEITUNG + " text not null, " +
                UebungenEntry.COLUMN_MUSKELGRUPPE + " text not null, " +
                UebungenEntry.COLUMN_ZIELGRUPPE + " text not null, " +
                UebungenEntry.COLUMN_VIDEO + " text not null" +
                ");";
        Log.d(LOG_TAG, SQL_CREATE_UEBUNGEN_TABLE);
        db.execSQL(SQL_CREATE_UEBUNGEN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UebungenEntry.TABLE_NAME);
        onCreate(db);
    }
}
