package htl_leonding.fiplyteam.fiply.fragments;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import java.sql.SQLException;
import htl_leonding.fiplyteam.fiply.ExpandableListAdapter;
import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class FUebungskatalog extends Fragment {
    UebungenRepository rep;
    ExpandableListAdapter adapter;
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //UebungenRepository.setContext(this);
        rep = UebungenRepository.getInstance();
        rep.deleteAllUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        InsertTestUebungen();
        //context = this;
        return inflater.inflate(R.layout.activity_uebungskatalog, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            ExpandableListView gv = (ExpandableListView) getView().findViewById(android.R.id.list);
            gv.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            c = rep.getAllUebungen();
            return "Success";
        }
    }

}
