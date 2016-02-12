package htl_leonding.fiplyteam.fiply.data;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.UebungenEntry;

public class UebungenRepository extends Service {

    private static Context repoContext;
    private static UebungenRepository instance;
    SQLiteDatabase db = getWritableDatabase();

    public static UebungenRepository getInstance() {
        if (instance == null)
            instance = new UebungenRepository();
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
     * Gibt eine Uebung in die Datenbank ein
     *
     * @param name          Der Name
     * @param beschreibung  Die Beschreibung
     * @param anleitung     Die Anleitung
     * @param muskelgruppe  Die Muskelgruppe
     * @param schwierigkeit Die Schwierigkeit (einfach, mittel, schwierig)
     * @param video         Der Videolink
     * @param equipment     Das benötigte Equipment
     * @return liefert ein long mit der id zurück
     */
    public long insertUebung(String name, String beschreibung, String anleitung,
                             String muskelgruppe, String schwierigkeit, String video, String equipment) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UebungenEntry.COLUMN_NAME, name);
        initialValues.put(UebungenEntry.COLUMN_BESCHREIBUNG, beschreibung);
        initialValues.put(UebungenEntry.COLUMN_ANLEITUNG, anleitung);
        initialValues.put(UebungenEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        initialValues.put(UebungenEntry.COLUMN_SCHWIERIGKEIT, schwierigkeit);
        initialValues.put(UebungenEntry.COLUMN_VIDEO, video);
        initialValues.put(UebungenEntry.COLUMN_EQUIPMENT, equipment);
        return db.insert(UebungenEntry.TABLE_NAME, null, initialValues);
    }

    /**
     * Löscht eine Uebung
     *
     * @param rowId Id der zu löschenden Entry
     * @return Anzahl der gelöschten Uebungen
     */
    public long deleteUebung(long rowId) {
        return db.delete(UebungenEntry.TABLE_NAME, UebungenEntry.COLUMN_ROWID + "=" + rowId, null);
    }

    /**
     * Löscht alle Uebungen
     *
     * @return Anzahl der gelöschten Uebungen
     */
    public long deleteAllUebungen() {
        return db.delete(UebungenEntry.TABLE_NAME, null, null);
    }

    /**
     * Liefert alle Uebungen zurück
     *
     * @return Alle Uebungen
     */
    public Cursor getAllUebungen() {
        return db.query(UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_SCHWIERIGKEIT,
                        UebungenEntry.COLUMN_VIDEO,
                        UebungenEntry.COLUMN_EQUIPMENT},
                null, null, null, null, UebungenEntry.COLUMN_NAME + " ASC");
    }

    /**
     * Liefert eine bestimmte Uebung zurück
     *
     * @param rowId Die Id der gesuchten Uebung
     * @return die gesuchte Uebung
     * @throws SQLException
     */
    public Cursor getUebung(long rowId) throws SQLException {
        Cursor myCursor = db.query(true, UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_SCHWIERIGKEIT,
                        UebungenEntry.COLUMN_VIDEO,
                        UebungenEntry.COLUMN_EQUIPMENT},
                UebungenEntry.COLUMN_ROWID + "=" + rowId,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**
     * Updated eine bestimmte Uebung
     *
     * @param name          Der Name
     * @param beschreibung  Die Beschreibung
     * @param anleitung     Die Anleitung
     * @param muskelgruppe  Die Muskelgruppe
     * @param schwierigkeit Die Schwierigkeit (einfach, mittel, schwierig)
     * @param video         Der Videolink
     * @param equipment     Das benötigte Equipment
     * @return Anzahl der geupdateten Uebungen
     */
    public long updateUebung(long rowId, String name, String beschreibung, String anleitung,
                             String muskelgruppe, String schwierigkeit, String video, String equipment) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(UebungenEntry.COLUMN_NAME, name);
        updatedValues.put(UebungenEntry.COLUMN_BESCHREIBUNG, beschreibung);
        updatedValues.put(UebungenEntry.COLUMN_ANLEITUNG, anleitung);
        updatedValues.put(UebungenEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        updatedValues.put(UebungenEntry.COLUMN_SCHWIERIGKEIT, schwierigkeit);
        updatedValues.put(UebungenEntry.COLUMN_VIDEO, video);
        updatedValues.put(UebungenEntry.COLUMN_EQUIPMENT, equipment);
        return db.update(UebungenEntry.TABLE_NAME, updatedValues, UebungenEntry.COLUMN_ROWID + "=" + rowId, null);
    }

    /**
     * Liefert die Anzahl aller Uebungen zurück
     *
     * @return Anzahl aller Uebungen
     */
    public long getUebungCount() {
        return DatabaseUtils.queryNumEntries(db, UebungenEntry.TABLE_NAME);
    }

    /**
     * Liefert die Übung mit dem passenden Namen Zurück
     *
     * @param name Name nach welchem eine Uebung gesucht werden soll
     * @return gesuchte Uebung
     * @throws SQLException
     */
    public Cursor getUebungByName(String name) throws SQLException {
        Cursor myCursor = db.query(true, UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_SCHWIERIGKEIT,
                        UebungenEntry.COLUMN_VIDEO,
                        UebungenEntry.COLUMN_EQUIPMENT},
                UebungenEntry.COLUMN_NAME + "=" + "'" + name + "'",
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**
     * Liefert alle Uebungen für eine bestimmte Muskelgruppe zurück
     *
     * @param Muskelgruppe Die Muskelgruppe nach welcher Uebungen gesucht werden sollen
     * @return alle Uebungen einer bestimmten Muskelgruppe
     */
    public Cursor getUebungenByMuskelgruppe(String Muskelgruppe) {
        return db.query(UebungenEntry.TABLE_NAME, new String[]{
                        UebungenEntry.COLUMN_ROWID,
                        UebungenEntry.COLUMN_NAME,
                        UebungenEntry.COLUMN_BESCHREIBUNG,
                        UebungenEntry.COLUMN_ANLEITUNG,
                        UebungenEntry.COLUMN_MUSKELGRUPPE,
                        UebungenEntry.COLUMN_SCHWIERIGKEIT,
                        UebungenEntry.COLUMN_VIDEO,
                        UebungenEntry.COLUMN_EQUIPMENT},
                UebungenEntry.COLUMN_MUSKELGRUPPE + "=" + "'" + Muskelgruppe + "'",
                null, null, null, UebungenEntry.COLUMN_NAME + " ASC", null);

    }

    public Cursor getFilteredUebungen() throws SQLException {
        KeyValueRepository kvr = KeyValueRepository.getInstance();

        String whereClause = "";

        if (!kvr.getKeyValue("filterName").getString(1).equals("")) {
            whereClause += UebungenEntry.COLUMN_NAME + " LIKE '%" + kvr.getKeyValue("filterName").getString(1) + "%'";
            if (!kvr.getKeyValue("filterMuskelGruppe").getString(1).equals("")) {
                whereClause += " AND ";
            }

        }

        if (!kvr.getKeyValue("filterMuskelGruppe").getString(1).equals("")) {
            whereClause += UebungenEntry.COLUMN_MUSKELGRUPPE + " LIKE '%" + kvr.getKeyValue("filterMuskelGruppe").getString(1) + "%'";
        }

        return db.query(UebungenEntry.TABLE_NAME, new String[]{
                UebungenEntry.COLUMN_ROWID,
                UebungenEntry.COLUMN_NAME,
                UebungenEntry.COLUMN_BESCHREIBUNG,
                UebungenEntry.COLUMN_ANLEITUNG,
                UebungenEntry.COLUMN_MUSKELGRUPPE,
                UebungenEntry.COLUMN_SCHWIERIGKEIT,
                UebungenEntry.COLUMN_VIDEO,
                UebungenEntry.COLUMN_EQUIPMENT}, whereClause, null, null, null, UebungenEntry.COLUMN_NAME + " ASC", null);

    }

    /**
     * Dropt und created anschließend die UebungenTabelle
     */
    public void reCreateUebungenTable() {
        db.execSQL("DROP TABLE IF EXISTS " + UebungenEntry.TABLE_NAME + ";");
        db.execSQL("create table " + UebungenEntry.TABLE_NAME +
                " (" + UebungenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                UebungenEntry.COLUMN_NAME + " text not null, " +
                UebungenEntry.COLUMN_BESCHREIBUNG + " text not null, " +
                UebungenEntry.COLUMN_ANLEITUNG + " text not null, " +
                UebungenEntry.COLUMN_MUSKELGRUPPE + " text not null, " +
                UebungenEntry.COLUMN_SCHWIERIGKEIT + " text not null, " +
                UebungenEntry.COLUMN_VIDEO + " text not null, " +
                UebungenEntry.COLUMN_EQUIPMENT + " text not null" +
                ");");
    }


    public void insertAllExercises() throws JSONException {
        reCreateUebungenTable();
        String json = repoContext.getResources().getString(R.string.exercisecatalog);
        JSONArray exercises = new JSONArray(json);
        JSONObject temp;
        for (int i = 0; i < exercises.length(); i++) {
            temp = exercises.getJSONObject(i);
            Log.wtf("Exercise: ", temp.getString("Name"));
            insertUebung(temp.getString("Name"), temp.getString("Beschreibung"), temp.getString("Durchführung"), temp.getString("Muskelgruppe"), temp.getString("Schwierigkeit"), "https://www.youtube.com/embed/0TjxnrWT8Es", temp.getString("Equipment"));
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
