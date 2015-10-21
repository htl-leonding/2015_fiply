package htl_leonding.fiplyteam.fiply;

import android.database.Cursor;
import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

public class DatabaseTest extends AndroidTestCase {
    private DBAdapter dbA;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        dbA = new DBAdapter(context);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        dbA.close();
    }

    public void testAddUebung() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        c = dbA.getUebung(1);
        assertEquals("Curls", c.getString(1));
    }

    public void testGetAllUebungen() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");

        c = dbA.getAllUebungen();

        c.moveToFirst();
        assertEquals("Curls", c.getString(1));
        assertEquals("https://www.youtube.com/watch?v=FtAz_85aVxE", c.getString(6));

        c.moveToNext();
        assertEquals("Squatten", c.getString(1));
        assertEquals("Testbeschreibung", c.getString(2));

        c.moveToNext();
        assertEquals("Benchpress", c.getString(1));
        assertEquals("Testtipp", c.getString(5));
    }

    public void testUpdateUebung() {
        Cursor c;
        dbA.open();
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dbA.updateUebung(1, "Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        c = dbA.getUebung(1);
        assertEquals("Curls", c.getString(1));
    }
}
