package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
                args.putString("beschreibung", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(2));
                args.putString("anleitung", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(3));
                args.putString("muskelgruppe", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(4));
                args.putString("zielgruppe", ((Cursor) uebungenLV.getItemAtPosition(position)).getString(5));
                args.putString("video", ((Cursor)uebungenLV.getItemAtPosition(position)).getString(6));
                args.putBoolean("showVideo", true);
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
}
