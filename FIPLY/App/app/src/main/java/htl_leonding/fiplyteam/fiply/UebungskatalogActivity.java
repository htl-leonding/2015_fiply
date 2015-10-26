package htl_leonding.fiplyteam.fiply;

import android.app.ListActivity;

public class UebungskatalogActivity extends ListActivity {
/*
    UebungenRepository dbA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uebungskatalog);
        dbA = new UebungenRepository(this);
        dbA.open();
        InsertTestUebungen();

        //c = dba.getAllUebungen();
    }

    @Override
    protected void onStart() {
        super.onStart();
        ActivityDatabaseAccess dbaccess = new ActivityDatabaseAccess();
        dbaccess.execute("");
    }

    private void InsertTestUebungen() {
        dbA.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Squatten", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        dbA.insertUebung("Benchpress", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        dbA.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        dbA.insertUebung("Deadlift", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");
        dbA.insertUebung("Skullcrusher", "Testbeschreibung", "Testanleitung", "Testmuskelgruppe", "TestZIELGRUPPE", "Testvideo");

    }

    public class ActivityDatabaseAccess extends AsyncTask<String, Void, String> {
        Cursor c;

        @Override
        protected void onPostExecute(String result) {
            ListAdapter adapter = new SimpleCursorAdapter(UebungskatalogActivity.this,
                    android.R.layout.activity_list_item,
                    c,
                    new String[]{"name"},
                    new int[]{android.R.id.text1}, 0);

            ListView gv = (ListView) findViewById(android.R.id.list);
            gv.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            c = dbA.getAllUebungen();


            return "Success";
        }
    }
          */
}
