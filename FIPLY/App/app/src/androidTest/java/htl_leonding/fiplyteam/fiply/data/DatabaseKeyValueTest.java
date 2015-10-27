package htl_leonding.fiplyteam.fiply.data;

import android.database.Cursor;
import android.test.AndroidTestCase;

import java.sql.SQLException;

public class DatabaseKeyValueTest extends AndroidTestCase {
    KeyValueRepository rep;

    @Override
    protected void setUp() throws Exception {
        KeyValueRepository.setContext(mContext);
        rep = KeyValueRepository.getInstance();
        rep.reCreateKeyValueTable();
    }

    @Override
    protected void tearDown() throws Exception {
    }

    /**
     * testet das get einer KeyValue mittels insert und get
     *
     * @throws SQLException
     */
    public void testGetKeyValue() throws SQLException {
        Cursor c;
        rep.insertKeyValue("Name", "Herbert");
        c = rep.getKeyValue("Name");
        assertEquals("Herbert", c.getString(1));
    }


    /**
     * testet das get aller KeyValues mittels insert und getAllKeyValues
     */
    public void testGetAllKeyValues() {
        Cursor c;
        rep.insertKeyValue("Name", "Herbert");
        rep.insertKeyValue("Alter", "14");
        rep.insertKeyValue("Groesse", "180");

        c = rep.getAllKeyValues();

        c.moveToFirst();
        assertEquals("Alter", c.getString(0));
        assertEquals("14", c.getString(1));

        c.moveToNext();
        assertEquals("Groesse", c.getString(0));
        assertEquals("180", c.getString(1));

        c.moveToNext();
        assertEquals("Name", c.getString(0));
        assertEquals("Herbert", c.getString(1));
    }

    /**
     * testet das update einer Uebung mittels insert und updateUebung
     *
     * @throws SQLException
     */
    public void testUpdateKeyValue() throws SQLException {
        Cursor c;
        long updateKeyValueReturn;
        rep.insertKeyValue("Name", "Herbert");
        c = rep.getKeyValue("Name");
        assertEquals("Herbert", c.getString(1));
        updateKeyValueReturn = rep.updateKeyValue("Name", "Bernd");
        assertEquals(1, updateKeyValueReturn);
        c = rep.getKeyValue("Name");
        assertEquals("Bernd", c.getString(1));
    }

    /**
     * testet das count aller KeyValues mittels insert und getKeyValueCount
     */
    public void testCountKeyValue() {
        assertEquals(0, rep.getKeyValueCount());
        rep.insertKeyValue("Name", "Herbert");
        rep.insertKeyValue("Alter", "14");
        rep.insertKeyValue("Groesse", "180");
        rep.insertKeyValue("Geschlecht", "männlich");
        rep.insertKeyValue("Zustand", "Pleb");
        rep.insertKeyValue("Version", "1.0.0.3");
        assertEquals(6, rep.getKeyValueCount());
    }

    /**
     * testet das delete einer KeyValue mittels insert, getKeyValueCount und deleteKeyValue
     */
    public void testDeleteKeyValue() {
        long deleteKeyValueReturn;
        rep.insertKeyValue("Name", "Herbert");
        rep.insertKeyValue("Alter", "14");
        rep.insertKeyValue("Groesse", "180");
        assertEquals(3, rep.getKeyValueCount());
        deleteKeyValueReturn = rep.deleteKeyValue("Alter");
        assertEquals(1, deleteKeyValueReturn);
        assertEquals(2, rep.getKeyValueCount());
    }

    /**
     * testet das delete aller Uebungen mittels insert, getUebungCount und deleteAllUebungen
     */
    public void testDeleteAllKeyValues() {
        long deleteAllKeyValuesReturn;
        assertEquals(0, rep.getKeyValueCount());
        rep.insertKeyValue("Name", "Herbert");
        rep.insertKeyValue("Alter", "14");
        rep.insertKeyValue("Groesse", "180");
        rep.insertKeyValue("Geschlecht", "männlich");
        rep.insertKeyValue("Zustand", "Pleb");
        rep.insertKeyValue("Version", "1.0.0.3");
        assertEquals(6, rep.getKeyValueCount());
        deleteAllKeyValuesReturn = rep.deleteAllKeyValues();
        assertEquals(6, deleteAllKeyValuesReturn);
        assertEquals(0, rep.getKeyValueCount());
    }
}