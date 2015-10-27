package htl_leonding.fiplyteam.fiply;

import android.database.Cursor;
import android.test.AndroidTestCase;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class DatabaseTest extends AndroidTestCase {

    UebungenRepository rep;

    //FiplyDBHelper dbHelper = FiplyDBHelper.getInstance(mContext);
    //SQLiteDatabase db = dbHelper.getWritableDatabase();

    @Override
    protected void setUp() throws Exception {
        UebungenRepository.setContext(mContext);
        rep = UebungenRepository.getInstance();
        rep.reCreateUebungenTable();
    }

    @Override
    protected void tearDown() throws Exception {
        //FiplyDBHelper.getInstance(mContext).close();
    }

    //testet das get einer Uebung mittels insert und get
    public void testGetUebung() throws SQLException {
        Cursor c;
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        c = rep.getUebung(1);
        assertEquals("Curls", c.getString(1));
    }

    //testet das get einer Uebung über den Namen mittels insert und get
    public void testGetUebungByName() throws SQLException {
        Cursor c;
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        c = rep.getUebungByName("Curls");
        assertEquals("Curls", c.getString(1));
        c = rep.getUebungByName("Squatten");
        assertEquals("Squatten", c.getString(1));
    }

    //testet das get aller Uebungen mittels insert und getAllUebungen
    public void testGetAllUebungen() {
        Cursor c;
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "TestbeschreibungSquatten", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPEBenchpress", "Testvideo");

        c = rep.getAllUebungen();

        c.moveToFirst();
        assertEquals("Benchpress", c.getString(1));
        assertEquals("TestZIELGRUPPEBenchpress", c.getString(5));

        c.moveToNext();
        assertEquals("Curls", c.getString(1));
        assertEquals("https://www.youtube.com/watch?v=FtAz_85aVxE", c.getString(6));

        c.moveToNext();
        assertEquals("Squatten", c.getString(1));
        assertEquals("TestbeschreibungSquatten", c.getString(2));
    }

    //testet das get aller Uebungen einer bestimmten Muskelgruppe mittels insert und getUebungenByMuskelgruppe
    public void testGetUebungenByMuskelgruppe() {
        Cursor c;
        rep.insertUebung("TestBizepsC", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("TestBizepsA", "Testbeschreibung", "Testanleitung", "Bizeps", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("TestBizepsB", "Testbeschreibung", "Testanleitung", "Bizeps", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");

        c = rep.getUebungenByMuskelgruppe("Bizeps");
        c.moveToFirst();
        assertEquals("TestBizepsA", c.getString(1));
        c.moveToNext();
        assertEquals("TestBizepsB", c.getString(1));
        c.moveToNext();
        assertEquals("TestBizepsC", c.getString(1));
    }

    //testet das update einer Uebung mittels insert und updateUebung
    public void testUpdateUebung() throws SQLException {
        Cursor c;
        long updateUebungReturn;
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        updateUebungReturn = rep.updateUebung(1, "Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        assertEquals(1, updateUebungReturn);
        c = rep.getUebung(1);
        assertEquals("Curls", c.getString(1));

    }

    //testet das count aller Uebungen mittels insert und getUebungCount
    public void testCountUebungen() {
        assertEquals(0, rep.getUebungCount());
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        assertEquals(6, rep.getUebungCount());
    }

    //testet das delete einer Uebung mittels insert, getUebungCount und deleteUebung
    public void testDeleteUebung() {
        long deleteUebungReturn;
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        assertEquals(2, rep.getUebungCount());
        deleteUebungReturn = rep.deleteUebung(1);
        assertEquals(1, deleteUebungReturn);
        assertEquals(1, rep.getUebungCount());
    }

    //testet das delete aller Uebungen mittels insert, getUebungCount und deleteAllUebungen
    public void testDeleteAllUebungen() {
        long deleteAllUebungenReturn;
        assertEquals(0, rep.getUebungCount());
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        assertEquals(6, rep.getUebungCount());
        deleteAllUebungenReturn = rep.deleteAllUebungen();
        assertEquals(6, deleteAllUebungenReturn);
        assertEquals(0, rep.getUebungCount());
    }

    public void testReturnOnInsert() throws SQLException {
        Cursor c;
        long insertReturn;
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        insertReturn = rep.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");

        c = rep.getUebung(insertReturn);
        assertEquals("Dips", c.getString(1));
    }
}