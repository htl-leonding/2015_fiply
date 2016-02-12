package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.KeyValueEntry;

public class KeyValueRepository {
    private static Context repoContext;
    private static KeyValueRepository instance;
    SQLiteDatabase db = getWritableDatabase();

    public static KeyValueRepository getInstance() {
        if (instance == null)
            instance = new KeyValueRepository();
        return instance;
    }

    public static void setContext(Context context) {
        repoContext = context;
    }

    private SQLiteDatabase getWritableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - " + "Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getWriteableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getWritableDatabase();
    }

    private SQLiteDatabase getReadableDatabase() {
        if (repoContext == null)
            throw new IllegalStateException("Context is null - " + "Set a repoContext in the Repository with setContext()");

        Log.wtf(FiplyDBHelper.LOG_TAG, "Repository::getReadableDatabase()");
        return FiplyDBHelper.getInstance(repoContext).getReadableDatabase();
    }

    /**
     * Gibt einen KeyValue-Entry in die Datenbank ein
     *
     * @param key   Der Key
     * @param value Der zum Key gehörende Value
     * @return liefert ein long mit der id zurück
     */
    public long insertKeyValue(String key, String value) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KeyValueEntry.COLUMN_KEY, key);
        initialValues.put(KeyValueEntry.COLUMN_VALUE, value);
        return db.insert(KeyValueEntry.TABLE_NAME, null, initialValues);
    }

    /**
     * Löscht einen KeyValue-Entries
     *
     * @param key der Key
     * @return Anzahl der gelöschten KeyValue-Entries
     */
    public long deleteKeyValue(String key) {
        return db.delete(KeyValueEntry.TABLE_NAME, KeyValueEntry.COLUMN_KEY + "=" + "'" + key + "'", null);
    }

    /**
     * Löscht alle KeyValues
     *
     * @return Anzahl der gelöschten KeyValue-Entries
     */
    public long deleteAllKeyValues() {
        return db.delete(KeyValueEntry.TABLE_NAME, null, null);
    }

    /**
     * Liefert alle KeyValues zurück
     *
     * @return Alle KeyValues
     */
    public Cursor getAllKeyValues() {
        return db.query(KeyValueEntry.TABLE_NAME, new String[]{
                        KeyValueEntry.COLUMN_KEY,
                        KeyValueEntry.COLUMN_VALUE},
                null, null, null, null, KeyValueEntry.COLUMN_KEY + " ASC");
    }

    /**
     * Liefert den KeyValue zum zugehörigen Key zurück
     *
     * @param key Der Key zu dem der Value gesucht wird
     * @return gesuchter KeyValue
     * @throws SQLException
     */
    public Cursor getKeyValue(String key) throws SQLException {
        Cursor myCursor = db.query(true, KeyValueEntry.TABLE_NAME, new String[]{
                        KeyValueEntry.COLUMN_KEY,
                        KeyValueEntry.COLUMN_VALUE},
                KeyValueEntry.COLUMN_KEY + "=" + "'" + key + "'",
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**
     * Updated einen bestimmten KeyValueEntry
     *
     * @param key   Der Key der geupdated werden soll
     * @param value Der Value der geupdated werden soll
     * @return Anzahl der geupdateten Entries
     */
    public long updateKeyValue(String key, String value) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(KeyValueEntry.COLUMN_KEY, key);
        updatedValues.put(KeyValueEntry.COLUMN_VALUE, value);
        return db.update(KeyValueEntry.TABLE_NAME, updatedValues, KeyValueEntry.COLUMN_KEY + "=" + "'" + key + "'", null);
    }

    /**
     * Liefert die Anzahl aller KeyValues zurück
     *
     * @return Anzahl aller KeyValues
     */
    public long getKeyValueCount() {
        return DatabaseUtils.queryNumEntries(db, KeyValueEntry.TABLE_NAME);
    }

    /**
     * Dropt und created anschließend die KeyValueTabelle
     */
    public void reCreateKeyValueTable() {
        db.execSQL("DROP TABLE IF EXISTS " + KeyValueEntry.TABLE_NAME + ";");
        db.execSQL("create table " + KeyValueEntry.TABLE_NAME +
                " (" +
                KeyValueEntry.COLUMN_KEY + " text primary key not null, " +
                KeyValueEntry.COLUMN_VALUE + " text not null" +
                ");");
    }

    public void setDefaultUserSettings() throws SQLException {
        if (getKeyValue("isUserCustomized").getCount() == 0) {
            insertKeyValue("userName","Name");
            insertKeyValue("userGender","Male");
            insertKeyValue("userWeight","80");
            insertKeyValue("userHeight","180");
            insertKeyValue("userAge","21-30");
            insertKeyValue("userProf","Not Fit");



            insertKeyValue("isUserCustomized", "true");


            insertKeyValue("trainingsphasenloaded", "false");


        }


        if(getKeyValue("filterName").getCount()==0){
            insertKeyValue("filterName","");
        } else {
            updateKeyValue("filterName","");
        }

        if(getKeyValue("filterMuskelGruppe").getCount()==0){
            insertKeyValue("filterMuskelGruppe","");
        } else {
            updateKeyValue("filterMuskelGruppe","");
        }

        if(getKeyValue("greetings").getCount()==0) {
            insertKeyValue("greetings","");
        }
        //getGreeting();

        Log.wtf("kvr test:", getKeyValue("filterName").getString(0));
        Log.wtf("kvr test:", getKeyValue("filterName").getString(1));

    }

    //returns the greeting corresponding to the time of day
    public String getGreeting() throws SQLException {
        if(Calendar.getInstance().getTime().getTime() < 1000)
            updateKeyValue("greeting", repoContext.getResources().getStringArray(R.array.greetingArray)[0]);
        else if(Calendar.getInstance().getTime().getTime() < 2000)
            updateKeyValue("greeting", repoContext.getResources().getStringArray(R.array.greetingArray)[1]);
        else if(Calendar.getInstance().getTime().getTime() < 3000)
            updateKeyValue("greeting", repoContext.getResources().getStringArray(R.array.greetingArray)[2]);
        else if(Calendar.getInstance().getTime().getTime() < 4000)
            updateKeyValue("greeting", repoContext.getResources().getStringArray(R.array.greetingArray)[3]);
        else
            updateKeyValue("greeting", "Gute Nacht");
        return null;
    }
}
