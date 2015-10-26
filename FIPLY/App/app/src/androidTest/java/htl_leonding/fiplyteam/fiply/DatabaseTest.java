package htl_leonding.fiplyteam.fiply;

import android.test.AndroidTestCase;

public class DatabaseTest extends AndroidTestCase {

    FiplyDBHelper dbHelper = new FiplyDBHelper(mContext);
    SQLiteDatabase dbA = dbHelper.getWritableDatabase();
    private DBAdapter dbA;

    @Override
    protected void setUp() throws Exception {
    }

    @Override
    protected void tearDown() throws Exception {
    }

    //testet das get einer Uebung mittels insert und get
    public void testGetUebung() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        c = dbA.getUebung(1);
        assertEquals("Curls", c.getString(1));
    }

    //testet das get einer Uebung über den Namen mittels insert und get
    public void testGetUebungByName() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        c = dbA.getUebungByName("Curls");
        assertEquals("Curls", c.getString(1));
        c = dbA.getUebungByName("Squatten");
        assertEquals("Squatten", c.getString(1));
    }

    //testet das get aller Uebungen mittels insert und getAllUebungen
    public void testGetAllUebungen() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "TestbeschreibungSquatten", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TesttippBenchpress", "Testvideo");

        c = dbA.getAllUebungen();

        c.moveToFirst();
        assertEquals("Benchpress", c.getString(1));
        assertEquals("TesttippBenchpress", c.getString(5));

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
        dbA.open();
        dbA.insertUebung("TestBizepsC", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("TestBizepsA", "Testbeschreibung", "Testanleitung", "Bizeps", "Testtipp", "Testvideo");
        dbA.insertUebung("TestBizepsB", "Testbeschreibung", "Testanleitung", "Bizeps", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");

        c = dbA.getUebungenByMuskelgruppe("Bizeps");

        c.moveToFirst();
        assertEquals("TestBizepsA", c.getString(1));

        c.moveToNext();
        assertEquals("TestBizepsB", c.getString(1));

        c.moveToNext();
        assertEquals("TestBizepsC", c.getString(1));

    }

    //testet das update einer Uebung mittels insert und updateUebung
    public void testUpdateUebung() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.updateUebung(1, "Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        c = dbA.getUebung(1);
        assertEquals("Curls", c.getString(1));
    }

    //testet das count aller Uebungen mittels insert und getUebungCount
    public void testCountUebungen() {
        dbA.open();
        assertEquals(0, dbA.getUebungCount());
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        assertEquals(6, dbA.getUebungCount());
    }

    //testet das delete einer Uebung mittels insert, getUebungCount und deleteUebung
    public void testDeleteUebung() {
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        assertEquals(2, dbA.getUebungCount());
        dbA.deleteUebung(1);
        assertEquals(1, dbA.getUebungCount());
    }

    //testet das delete aller Uebungen mittels insert, getUebungCount und deleteAllUebungen
    public void testDeleteAllUebungen() {
        dbA.open();
        assertEquals(0, dbA.getUebungCount());
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        assertEquals(6, dbA.getUebungCount());
        dbA.deleteAllUebungen();
        assertEquals(0, dbA.getUebungCount());
    }
}