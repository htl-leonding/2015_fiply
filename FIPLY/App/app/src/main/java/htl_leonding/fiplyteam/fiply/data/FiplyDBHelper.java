package htl_leonding.fiplyteam.fiply.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.UebungsEntry;

/**
 * Created by Andreas on 26.10.2015.
 */
public class FiplyDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FiplyDB";
    private static final int DATABASE_VERSION = 1;

    public FiplyDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_UEBUNGEN_TABLE = "create table " + UebungsEntry.TABLE_NAME +
                " (" + UebungsEntry.COLUMN_ROWID + " integer primary COLUMN autoincrement, " +
                UebungsEntry.COLUMN_NAME + " text not null, " +
                UebungsEntry.COLUMN_BESCHREIBUNG + " text not null, " +
                UebungsEntry.COLUMN_ANLEITUNG + " text not null, " +
                UebungsEntry.COLUMN_MUSKELGRUPPE + " text not null, " +
                UebungsEntry.COLUMN_TIPP + " text not null, " +
                UebungsEntry.COLUMN_VIDEO + " text not null" +
                ");";

        db.execSQL(SQL_CREATE_UEBUNGEN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UebungsEntry.TABLE_NAME);
        onCreate(db);
    }
}
