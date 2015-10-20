package htl_leonding.fiplyteam.fiply;

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

    public void testOpenDb() {
        dbA.open();
    }

    public void testAddUebung() {
        dbA.open();
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
    }
}
