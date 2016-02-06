package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class FTrainingsplan extends Fragment {

    GenerateAllgemein gAlg;
    Context context;
    ListView lView;
    SimpleCursorAdapter sCAdaptaber;
    UebungenRepository rep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        rep = UebungenRepository.getInstance();
        String[] wochentage = {"Montag", "Donnerstag", "Samstag"};
        gAlg = new GenerateAllgemein(true, 2, wochentage, new Date());
        return inflater.inflate(R.layout.fragment_trainingsplantest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lView = (ListView) getView().findViewById(R.id.testlistview);

        ArrayList<Uebung> uebungsListe = gAlg.getTPhase().getUebungenAsArrayList();
        Uebung ueb = new Uebung();
        ueb.setUebungsName("Starttermin: " + gAlg.getTPhase().getStartDate().toString());
        ueb.setWochenTag("Endtermin: " + gAlg.getTPhase().getEndDate().toString());
        ueb.setUebungsID("Dauer: " + gAlg.getTPhase().getPhasenDauer() + " Wochen");

        Collections.sort(uebungsListe, new Comparator<Uebung>() {
            @Override
            public int compare(Uebung lhs, Uebung rhs) {

                return lhs.getWochenTag().compareTo(rhs.getWochenTag());
            }
        });
        uebungsListe.add(ueb);

        UebungsAdapter adapter = new UebungsAdapter(this.getContext(), uebungsListe);
        lView = (ListView) view.findViewById(R.id.testlistview);
        lView.setAdapter(adapter);
    }
}
