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
    GeneratePhTwoKraftausdauer phTKra;
    GeneratePhTwoMuskelPh3Kraft phTwoMusPhThreeKraft;
    GeneratePhTwoMaxiPh3Muskel phTwoMaxPh3Musk;

    Context context;
    ListView lView;
    SimpleCursorAdapter sCAdaptaber;
    UebungenRepository rep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();

        rep = UebungenRepository.getInstance();
        String[] wochentage = {"Montag", "Donnerstag", "Samstag"};

        String[] muskelgruppen = {"Brust", "Beine", "Schulter"};

        gAlg = new GenerateAllgemein(true, 2, wochentage, new Date());
        phTKra = new GeneratePhTwoKraftausdauer(wochentage, new Date(), true);
        phTwoMusPhThreeKraft = new GeneratePhTwoMuskelPh3Kraft(wochentage, new Date(), "Muskelaufbau", muskelgruppen);
        phTwoMaxPh3Musk = new GeneratePhTwoMaxiPh3Muskel(new Date(), "Muskelaufbau", wochentage);

        return inflater.inflate(R.layout.fragment_trainingsplantest, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lView = (ListView) getActivity().findViewById(R.id.testlistview);

        ArrayList<Uebung> uebungsListeAllg = gAlg.getTPhase().getUebungenAsArrayList();
        ArrayList<Uebung> uebungsListePhTKra = phTKra.getTPhase().getUebungenAsArrayList();
        ArrayList<Uebung> uebungsListePhTwoMusPhThreeKraft = phTwoMusPhThreeKraft.getTPhase().getUebungenAsArrayList();
        ArrayList<Uebung> uebungsListephTwoMaxPh3Musk = phTwoMaxPh3Musk.getTPhase().getUebungenAsArrayList();

        System.out.println("Size: " +  uebungsListephTwoMaxPh3Musk.size());

        Uebung ueb = new Uebung();
        ueb.setUebungsName("Starttermin: " + phTwoMusPhThreeKraft.getTPhase().getStartDate().toString());
        ueb.setWochenTag("Endtermin: " + phTwoMusPhThreeKraft.getTPhase().getEndDate().toString());
        ueb.setUebungsID("Dauer: " + phTwoMusPhThreeKraft.getTPhase().getPhasenDauer());
        ueb.setMuskelgruppe("Lol");

        Collections.sort(uebungsListephTwoMaxPh3Musk, new Comparator<Uebung>() {
            @Override
            public int compare(Uebung lhs, Uebung rhs) {

                return lhs.getWochenTag().compareTo(rhs.getWochenTag());
            }
        });

        uebungsListephTwoMaxPh3Musk.add(ueb);

        UebungsAdapter adapter = new UebungsAdapter(this.getContext(), uebungsListephTwoMaxPh3Musk);
        lView = (ListView) view.findViewById(R.id.testlistview);
        lView.setAdapter(adapter);
    }
}
