package htl_leonding.fiplyteam.fiply;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class UebungskatalogActivity extends AppCompatActivity {

    UebungenRepository rep;
    ExpandableListAdapter adapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungskatalog);
        UebungenRepository.setContext(this);
        rep = UebungenRepository.getInstance();
        rep.deleteAllUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        context = this;
    }


    @Override
    protected void onStart() {
        super.onStart();
        ActivityDatabaseAccess dbaccess = new ActivityDatabaseAccess();
        dbaccess.execute("");
    }

    private void InsertTestUebungen() {
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Squatten", "Testbeschreibung Squatten", "Testanleitung Squatten", "Beine", "SquattenZIELGRUPPE", "Testvideo");
        rep.insertUebung("Benchpress", "Testbeschreibung Benchpress", "Testanleitung Benchpress", "Brust", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Deadlift", "Testbeschreibung Deadlift", "Testanleitung", "Arme", "TestZIELGRUPPE", "Testvideo");
        rep.insertUebung("Skullcrusher", "Testbeschreibung Skullcrusher", "Testanleitung", "Trizeps", "TestZIELGRUPPE", "Testvideo");
    }

    public class ActivityDatabaseAccess extends AsyncTask<String, Void, String> {
        Cursor c;

        @Override
        protected void onPostExecute(String result) {

            try {
                adapter = new ExpandableListAdapter(context, rep.getHeaderNamesForUebungskatalog(), rep.getChildDataForUebungskatalog());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ExpandableListView gv = (ExpandableListView) findViewById(android.R.id.list);
            gv.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            c = rep.getAllUebungen();
            return "Success";
        }
    }

}
