package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.UebungenEntry;

public class UebungenRepository {

    private static Context repoContext;
    SQLiteDatabase db = getWritableDatabase();

    private static UebungenRepository instance;

    public static UebungenRepository getInstance(){
        if (instance == null)
            instance = new UebungenRepository();
        return instance;
    }

    public static void setContext(Context context){
        repoContext = context;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - " + "Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getWriteableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase(){
        if (repoContext == null)
            throw new IllegalStateException("Context is null - " + "Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getReadableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getReadableDatabase();
    }

    //Gibt eine Uebung in die Datenbank ein
    public long insertUebung(String name, String beschreibung, String anleitung,
                            String muskelgruppe, String ZIELGRUPPE, String video) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UebungenEntry.COLUMN_NAME, name);
        initialValues.put(UebungenEntry.COLUMN_BESCHREIBUNG, beschreibung);
        initialValues.put(UebungenEntry.COLUMN_ANLEITUNG, anleitung);
        initialValues.put(UebungenEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        initialValues.put(UebungenEntry.COLUMN_ZIELGRUPPE, ZIELGRUPPE);
        initialValues.put(UebungenEntry.COLUMN_VIDEO, video);
        return db.insert(UebungenEntry.TABLE_NAME, null, initialValues);
    }

    //Löscht eine Uebung
    public boolean deleteUebung(long rowId) {
        return db.delete(UebungenEntry.TABLE_NAME, UebungenEntry.COLUMN_ROWID + "=" + rowId, null) > 0;
    }

    //Löscht alle Uebungen
    public boolean deleteAllUebungen() {
        return db.delete(UebungenEntry.TABLE_NAME, null, null) > 0;
    }

    //Liefert alle Uebungen zurück
    public Cursor getAllUebungen() {
        return db.query(UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_ZIELGRUPPE,
                        UebungenEntry.COLUMN_VIDEO},
                null, null, null, null, UebungenEntry.COLUMN_NAME + " ASC");
    }

    //Liefert eine bestimmte Uebung zurück
    public Cursor getUebung(long rowId) throws SQLException {
        Cursor myCursor = db.query(true, UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_ZIELGRUPPE,
                        UebungenEntry.COLUMN_VIDEO},
                UebungenEntry.COLUMN_ROWID + "=" + rowId,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    //Updated eine bestimmte Uebung
    public boolean updateUebung(long rowId, String name, String beschreibung, String anleitung,
                                String muskelgruppe, String ZIELGRUPPE, String video) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(UebungenEntry.COLUMN_NAME, name);
        updatedValues.put(UebungenEntry.COLUMN_BESCHREIBUNG, beschreibung);
        updatedValues.put(UebungenEntry.COLUMN_ANLEITUNG, anleitung);
        updatedValues.put(UebungenEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        updatedValues.put(UebungenEntry.COLUMN_ZIELGRUPPE, ZIELGRUPPE);
        updatedValues.put(UebungenEntry.COLUMN_VIDEO, video);
        return db.update(UebungenEntry.TABLE_NAME, updatedValues, UebungenEntry.COLUMN_ROWID + "=" + rowId, null) > 0;
    }

    //Liefert die Anzahl aller Uebungen zurück
    public int getUebungCount() {
        return (int) DatabaseUtils.queryNumEntries(db, "uebungen");
    }

    //Liefert die Übung mit dem passenden Namen Zurück
    public Cursor getUebungByName(String name) throws SQLException {
        Cursor myCursor = db.query(true, UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_ZIELGRUPPE,
                        UebungenEntry.COLUMN_VIDEO},
                UebungenEntry.COLUMN_NAME + "=" + "'" + name + "'",
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    //Liefert alle Uebungen für eine bestimmte Muskelgruppe zurück
    public Cursor getUebungenByMuskelgruppe(String Muskelgruppe) {
        return db.query(UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_ZIELGRUPPE,
                        UebungenEntry.COLUMN_VIDEO},
                UebungenEntry.COLUMN_MUSKELGRUPPE + "=" + "'" + Muskelgruppe + "'",
                null, null, null, UebungenEntry.COLUMN_NAME + " ASC", null);
    }


}
