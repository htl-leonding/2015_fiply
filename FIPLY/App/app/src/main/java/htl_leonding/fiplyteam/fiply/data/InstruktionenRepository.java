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
import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;

public class InstruktionenRepository extends Service{
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
     * @param wochentag     Der Wochentag an dem die Instruktion bewältigt werden soll
     * @param repmax        Beschreibt das Trainingsgewicht
     * @param uebungsid     Der Verweis auf die zu machende Übung
     * @param phasenid      Der Verweis auf die Phase in der diese Übung absolviert werden soll
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
