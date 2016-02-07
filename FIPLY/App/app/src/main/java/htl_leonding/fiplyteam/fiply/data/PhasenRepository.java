package htl_leonding.fiplyteam.fiply.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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


}
