package htl_leonding.fiplyteam.fiply.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.StatisticEntry;
import htl_leonding.fiplyteam.fiply.statistic.MoodTime;
import htl_leonding.fiplyteam.fiply.statistic.WeightLifted;

public class StatisticRepository {
    private static Context repoContext;
    private static StatisticRepository instance = null;

    SQLiteDatabase db = getWritableDatabase();

    public static StatisticRepository getInstance() {
        if (instance == null)
            instance = new StatisticRepository();
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

    public Cursor getAllDataPoints() {
        return db.query(StatisticEntry.TABLE_NAME, new String[]{
                        StatisticEntry.COLUMN_ROWID,
                        StatisticEntry.COLUMN_DATE,
                        StatisticEntry.COLUMN_MOOD,
                        StatisticEntry.COLUMN_LIFTEDWEIGHT},
                null, null, null, null, StatisticEntry.COLUMN_DATE + " ASC");
    }


    public LineGraphSeries<WeightLifted> getSeriesForLiftedWeight() {

        Cursor cDataPoints = getAllDataPoints();
        cDataPoints.moveToFirst();

        WeightLifted[] dataPoints = new WeightLifted[cDataPoints.getCount()];

        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = new WeightLifted(Double.parseDouble(cDataPoints.getString(0)), cDataPoints.getDouble(2));
            cDataPoints.moveToNext();
        }

        LineGraphSeries<WeightLifted> lgsr = new LineGraphSeries<>(dataPoints);
        return lgsr;
    }

    public LineGraphSeries<MoodTime> getSeriesForMoodTime() {
        Cursor cDataPoints = getAllDataPoints();
        cDataPoints.moveToFirst();

        MoodTime[] dataPoints = new MoodTime[cDataPoints.getCount()];

        for (int i = 0; i < dataPoints.length; i++) {
            dataPoints[i] = new MoodTime(Double.parseDouble(cDataPoints.getString(0)), cDataPoints.getDouble(1));
            cDataPoints.moveToNext();
        }

        LineGraphSeries<MoodTime> lgsr = new LineGraphSeries<>(dataPoints);
        return lgsr;
    }


    public long insertDataPoint(double mood, double weight) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(StatisticEntry.COLUMN_DATE, Calendar.DATE);
        initialValues.put(StatisticEntry.COLUMN_MOOD, mood);
        initialValues.put(StatisticEntry.COLUMN_LIFTEDWEIGHT, weight);
        return db.insert(StatisticEntry.TABLE_NAME, null, initialValues);
    }

    public long insertDataPointWithDate(String date, double mood, double weight) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(StatisticEntry.COLUMN_DATE, date);
        initialValues.put(StatisticEntry.COLUMN_MOOD, mood);
        initialValues.put(StatisticEntry.COLUMN_LIFTEDWEIGHT, weight);
        return db.insert(StatisticEntry.TABLE_NAME, null, initialValues);
    }


    //insert some test stats
    public void insertTestStats() {

        deleteAllDataPoints();
        insertDataPointWithDate("150221", 4, 20);
        insertDataPointWithDate("150224", 2, 40);
        insertDataPointWithDate("150228", 3, 90);
        insertDataPointWithDate("150302", 2, 80);
        insertDataPointWithDate("150306", 5, 60);
        insertDataPointWithDate("150309", 4, 120);
        insertDataPointWithDate("150312", 1, 140);
    }

    public void deleteAllDataPoints() {
        db.delete(StatisticEntry.TABLE_NAME, null, null);
    }

    public void reCreateUebungenTable() {
        db.execSQL("DROP TABLE IF EXISTS " + StatisticEntry.TABLE_NAME + ";");
        db.execSQL("create table " + StatisticEntry.TABLE_NAME +
                " (" + StatisticEntry.COLUMN_ROWID + " integer primary key autoincrement, " +
                StatisticEntry.COLUMN_DATE + " text not null, " +
                StatisticEntry.COLUMN_MOOD + " text not null, " +
                StatisticEntry.COLUMN_LIFTEDWEIGHT + " text not null" +
                ");");
    }
}