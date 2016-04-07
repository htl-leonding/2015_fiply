package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class FUebungskatalog extends Fragment {
    KeyValueRepository kvr;
    UebungenRepository rep;
    Context context;
    ListView uebungenLV;
    FloatingActionButton openFilter;
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
        //InsertTestUebungen();
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
        openFilter = (FloatingActionButton) getView().findViewById(R.id.fabFilter);
        kvr = KeyValueRepository.getInstance();
        int[] toViews = {R.id.ueListViewItem};
        String[] fromColumns = {rep.getAllUebungen().getColumnName(1)};

        //Setzt den OnClickListener für den Button openFilter, dieser öffnet die Filter View.
        openFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayView(new FUebungFilter());
            }
        });

        uebungenLV = (ListView) getActivity().findViewById(R.id.ueList);
        try {
            ueAdapter = new SimpleCursorAdapter(context, R.layout.uebungskatalog_item, rep.getFilteredUebungen(), fromColumns, toViews, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        uebungenLV.setAdapter(ueAdapter);

        /**
         * Setzt den OnItemClickListener für die ListView uebungenLV, dieser ruft die DetailView der gedrückten
         * Übung auf und setzt die Argumente zu den jeweiligen Infos über die Übung
         */
        uebungenLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();
                args.putString("name", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(1));
                args.putString("beschreibung", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(2));
                args.putString("anleitung", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(3));
                args.putString("muskelgruppe", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(4));
                args.putString("schwierigkeit", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(5));
                args.putString("video", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(6));
                args.putString("equipment", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(7));
                args.putBoolean("showVideo", false);
                FUebungDetail fUebungDetail = new FUebungDetail();
                fUebungDetail.setArguments(args);
                displayView(fUebungDetail);
            }
        });
    }

    /**
     * Ruft das übergebene Fragment auf
     * @param fragment
     */
    private void displayView(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, fragment);
        fragmentTransaction.commit();
    }
}
