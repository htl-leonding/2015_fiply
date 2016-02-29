package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.PlanEntry;

import static htl_leonding.fiplyteam.fiply.data.FiplyContract.*;

public class PlanRepository {

    private static Context repoContext;
    private static PlanRepository instance = null;

    SQLiteDatabase db = getWritableDatabase();

    public static PlanRepository getInstance() {
        if (instance == null)
            instance = new PlanRepository();
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


    public long insertPlan(String planName, String startDate, String endDate, String ziel) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PlanEntry.COLUMN_PLANNAME, planName);
        initialValues.put(PlanEntry.COLUMN_STARTDATE, startDate);
        initialValues.put(PlanEntry.COLUMN_ENDDATE, endDate);
        initialValues.put(PlanEntry.COLUMN_ZIEL, ziel);
        return db.insert(PlanEntry.TABLE_NAME, null, initialValues);
    }

    public Cursor getPlanByName(String name){
        return db.query(PlanEntry.TABLE_NAME, new String[]{
                        PlanEntry.COLUMN_ROWID
                }, PlanEntry.COLUMN_PLANNAME + "=" + "'" + name + "'",
                null, null, null, PlanEntry.COLUMN_ROWID + " ASC", null);
    }

    public void reCreatePlanTable() {
        db.execSQL("DROP TABLE IF EXISTS " + PlanEntry.TABLE_NAME + ";");
        final String SQL_CREATE_PLAN_TABLE = "create table " + PlanEntry.TABLE_NAME +
                " (" + PhasenEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                PlanEntry.COLUMN_PLANNAME + " text not null, " +
                PlanEntry.COLUMN_STARTDATE + " text not null, " +
                PlanEntry.COLUMN_ENDDATE + " text not null, " +
                PlanEntry.COLUMN_ZIEL + " text not null" +
                ");";
        db.execSQL(SQL_CREATE_PLAN_TABLE);
    }

    public void deleteAll() {
        db.delete(PlanEntry.TABLE_NAME, null, null);
    }

    public Cursor getAllPlans(){
        return db.query(PlanEntry.TABLE_NAME, new String[]{
                PlanEntry.COLUMN_ROWID,
                PlanEntry.COLUMN_PLANNAME,
                PlanEntry.COLUMN_STARTDATE,
                PlanEntry.COLUMN_ENDDATE,
                PlanEntry.COLUMN_ZIEL
        }
                ,null,null,null,null, PlanEntry.COLUMN_STARTDATE + " ASC");
    }

    public void deleteByPlanId(String id){
        db.delete(PlanEntry.TABLE_NAME, PlanEntry.COLUMN_ROWID + "=" + id, null);
    }

    public long getPlanCount(){
        return DatabaseUtils.queryNumEntries(db, PlanEntry.TABLE_NAME);

    }
}
