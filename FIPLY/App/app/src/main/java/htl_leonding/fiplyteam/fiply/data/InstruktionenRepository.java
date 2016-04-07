package htl_leonding.fiplyteam.fiply.data;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;

public class InstruktionenRepository extends Service {
    private static Context repoContext;
    private static InstruktionenRepository instance;
    SQLiteDatabase db = getWritableDatabase();

    public static InstruktionenRepository getInstance() {
        if (instance == null)
            instance = new InstruktionenRepository();
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
     * Gibt eine Instruktion in die Datenbank ein
     *
     * @param wochentag Der Wochentag an dem die Instruktion bewältigt werden soll
     * @param repmax    Beschreibt das Trainingsgewicht
     * @param uebungsid Der Verweis auf die zu machende Übung
     * @param phasenid  Der Verweis auf die Phase in der diese Übung absolviert werden soll
     * @return
     */
    public long insertUebung(String wochentag, String repmax, String uebungsid, String phasenid) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(InstruktionenEntry.COLUMN_WOCHENTAG, wochentag);
        initialValues.put(InstruktionenEntry.COLUMN_REPMAX, repmax);
        initialValues.put(InstruktionenEntry.COLUMN_UEBUNGSID, uebungsid);
        initialValues.put(InstruktionenEntry.COLUMN_PHASENID, phasenid);
        return db.insert(InstruktionenEntry.TABLE_NAME, null, initialValues);
    }

    // Gibt einen Curosr mit allen Instruktionen zurück.
    public Cursor getAllInstructions() {
        return db.query(InstruktionenEntry.TABLE_NAME, new String[]{
                        InstruktionenEntry.COLUMN_ROWID,
                        InstruktionenEntry.COLUMN_WOCHENTAG,
                        InstruktionenEntry.COLUMN_REPMAX,
                        InstruktionenEntry.COLUMN_UEBUNGSID,
                        InstruktionenEntry.COLUMN_PHASENID
                },
                null, null, null, null, InstruktionenEntry.COLUMN_PHASENID + " ASC");
    }

    // Gibt eine Instruktion mit einer bestimmten id zurück.
    public Cursor getInstruktionByPhasenId(String id) {
        return db.query(true, InstruktionenEntry.TABLE_NAME, new String[]{
                InstruktionenEntry.COLUMN_ROWID,
                InstruktionenEntry.COLUMN_WOCHENTAG,
                InstruktionenEntry.COLUMN_REPMAX,
                InstruktionenEntry.COLUMN_UEBUNGSID,
                InstruktionenEntry.COLUMN_PHASENID
        }, InstruktionenEntry.COLUMN_PHASENID + "=" + id, null, null, null, null, null);
    }

    // Erstellt die Tabelle
    public void reCreateInstuktionenTable() {
        db.execSQL("DROP TABLE IF EXISTS " + FiplyContract.InstruktionenEntry.TABLE_NAME + ";");
        final String SQL_CREATE_INSTRUKTIONEN_TABLE = "create table " + InstruktionenEntry.TABLE_NAME +
                " (" + InstruktionenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                InstruktionenEntry.COLUMN_WOCHENTAG + " text not null, " +
                InstruktionenEntry.COLUMN_REPMAX + " text not null, " +
                InstruktionenEntry.COLUMN_UEBUNGSID + " text not null, " +
                InstruktionenEntry.COLUMN_PHASENID + " text not null" +
                ");";
        db.execSQL(SQL_CREATE_INSTRUKTIONEN_TABLE);
    }

    // Löscht eine Instruktion mit einer speziellen PhasenId
    public void deleteByPhasenId(String id){
        db.delete(FiplyContract.InstruktionenEntry.TABLE_NAME, InstruktionenEntry.COLUMN_PHASENID + "=" + id, null);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Löscht alle Instruktionen.
    public void deleteAll() {
        db.delete(InstruktionenEntry.TABLE_NAME, null, null);
    }
}
