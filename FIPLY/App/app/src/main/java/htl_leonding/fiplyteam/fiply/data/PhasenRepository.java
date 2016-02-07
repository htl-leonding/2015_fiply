package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PhasenEntry;


public class PhasenRepository {

    private static Context repoContext;
    private static PhasenRepository instance = null;

    SQLiteDatabase db = getWritableDatabase();

    public static PhasenRepository getInstance(){
        if (instance == null)
            instance = new PhasenRepository();
        return instance;
    }

    public static void setContext(Context context){repoContext = context;}

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


    public long insertPhase(String startDate, String endDate, String phasenName,
                             String phasenDauer, String pausenDauer, String saetze, String wiederholungen){
        ContentValues initialValues = new ContentValues();
        initialValues.put(PhasenEntry.COLUMN_STARTDATE, startDate);
        initialValues.put(PhasenEntry.COLUMN_ENDDATE, endDate);
        initialValues.put(PhasenEntry.COLUMN_PHASENNAME, phasenName);
        initialValues.put(PhasenEntry.COLUMN_PHASENDAUER, phasenDauer);
        initialValues.put(PhasenEntry.COLUMN_PAUSENDAUER, pausenDauer);
        initialValues.put(PhasenEntry.COLUMN_SAETZE, saetze);
        initialValues.put(PhasenEntry.COLUMN_WIEDERHOLUNGEN, wiederholungen);
        return db.insert(PhasenEntry.TABLE_NAME, null, initialValues);
    }
}
