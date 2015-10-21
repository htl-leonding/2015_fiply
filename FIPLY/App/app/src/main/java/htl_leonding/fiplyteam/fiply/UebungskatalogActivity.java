package htl_leonding.fiplyteam.fiply;

import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

public class UebungskatalogActivity extends ListActivity {

    SimpleCursorAdapter ca;
    Cursor c;
    DBAdapter dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dba = new DBAdapter(this);
        dba.open();
        InsertTestUebungen();

        c = dba.getAllUebungen();


        ListAdapter adapter = new SimpleCursorAdapter(this,
                android.R.layout.activity_list_item,
                c,
                new String[]{"Name"},
                new int[]{android.R.id.text1}, 0);

        GridView gv = (GridView) findViewById(R.id.uebungsGrid);
        gv.setAdapter(adapter);
        setContentView(R.layout.activity_uebungskatalog);
    }

    private void InsertTestUebungen() {
        dba.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dba.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dba.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dba.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dba.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");
        dba.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "Testtipp", "Testvideo");

    }
}
