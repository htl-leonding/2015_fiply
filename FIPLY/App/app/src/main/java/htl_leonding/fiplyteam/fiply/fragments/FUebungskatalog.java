package htl_leonding.fiplyteam.fiply.fragments;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class FUebungskatalog extends Fragment {
    UebungenRepository rep;
    Context context;
    ListView uebungenLV;
    SimpleCursorAdapter ueAdapter;

    /**
     * Lädt das fragment_uebungskatalog in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        UebungenRepository.setContext(context);
        rep = UebungenRepository.getInstance();
        rep.deleteAllUebungen();
        InsertTestUebungen();
        return inflater.inflate(R.layout.fragment_uebungskatalog, container, false);
    }

    /**
     * Wird aufgerufen nachdem die View aufgebaut ist und dient dem setzen der OnClickListener
     *
     * @param view               default
     * @param savedInstanceState default
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        int[] toViews = {R.id.ueListViewItem};
        String[] fromColumns = {rep.getAllUebungen().getColumnName(1)};


        uebungenLV = (ListView) getView().findViewById(R.id.ueList);
        ueAdapter = new SimpleCursorAdapter(context, R.layout.uebungskatalog_item, rep.getAllUebungen(), fromColumns, toViews, 0);
        uebungenLV.setAdapter(ueAdapter);
        uebungenLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle args = new Bundle();
                args.putString("name", ((Cursor)uebungenLV.getItemAtPosition(position)).getString(1));
                args.putString("video", ((Cursor)uebungenLV.getItemAtPosition(position)).getString(6));
                FUebungDetail fUebungDetail = new FUebungDetail();
                fUebungDetail.setArguments(args);
                displayView(fUebungDetail);
            }
        });
    }

    private void displayView(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();
    }

    /**
     * Fügt ein paar Testübungen in die Datenbank ein um die ExpandableListView darstellen zu können
     */
    private void InsertTestUebungen() {
        rep.insertUebung("Curls", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "");
        rep.insertUebung("Squatten", "Testbeschreibung Squatten", "Testanleitung Squatten", "Beine", "SquattenZIELGRUPPE", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Benchpress", "Testbeschreibung Benchpress", "Testanleitung Benchpress", "Brust", "TestZIELGRUPPE", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Dips", "Mit Gewichten wird gecurlt", "Gewicht nehmen und anschließend curlen", "Bizeps", "Langsam durchführen", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Deadlift", "Testbeschreibung Deadlift", "Testanleitung", "Arme", "TestZIELGRUPPE", "https://www.youtube.com/watch?v=FtAz_85aVxE");
        rep.insertUebung("Skullcrusher", "Testbeschreibung Skullcrusher", "Testanleitung", "Trizeps", "TestZIELGRUPPE", "https://www.youtube.com/watch?v=FtAz_85aVxE");
    }

    /*
    public class ActivityDatabaseAccess extends AsyncTask<String, Void, String> {
        Cursor c;

        @Override
        protected void onPostExecute(String result) {

            try {
                adapter = new ExpandableListAdapter(context, rep.getHeaderNamesForUebungskatalog(), rep.getChildDataForUebungskatalog());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            gv.setAdapter(adapter);
        }

        @Override
        protected String doInBackground(String... params) {
            c = rep.getAllUebungen();
            return "Success";
        }
    }
    */
}
