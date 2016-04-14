package htl_leonding.fiplyteam.fiply.trainingssession;


import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.menu.FMain;
import htl_leonding.fiplyteam.fiply.trainingsplan.RepMax;
import htl_leonding.fiplyteam.fiply.trainingsplan.Trainingsphase;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;


public class FTrainingsSettings extends Fragment {

    TextView welcomeText;
    PlanSessionPort port;
    TextView uebungsText;
    Button chooseDay;
    Button gotosession;
    PhasenRepository rep;
    ProgressBar pBar;
    ListView uebList;
    TextView title;
    ImageView imgView;
    ArrayList<String> uebs;
    String day = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.fsettingtitle);
        return inflater.inflate(R.layout.fragment_sessionsettings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rep = PhasenRepository.getInstance();
        PhasenRepository.setContext(getContext());
        InstruktionenRepository.setContext(getContext());
        port = PlanSessionPort.getInstance();
        port.init();
        if (!port.isGenerated()) { // Wenn kein Trainingsplan existiert kann diese View nicht besucht werden.
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.alertnoplan)
                    .setTitle(R.string.fehler).setIcon(R.drawable.alertsmall);
            AlertDialog dialog = builder.create();
            dialog.show();
            displayFragment.displayMainMenu(new FMain(), getFragmentManager());
            onDestroy();
        }

        title = (TextView) getActivity().findViewById(R.id.sessionsettingstitle);
        welcomeText = (TextView) getActivity().findViewById(R.id.sessionsettingwelcome);
        uebungsText = (TextView) getActivity().findViewById(R.id.sessionsettinguebungen);
        gotosession = (Button) getActivity().findViewById(R.id.gotosession);
        pBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        Drawable draw = getActivity().getDrawable(R.drawable.progressbar);
        pBar.setProgressDrawable(draw);
        uebList = (ListView) getActivity().findViewById(R.id.listviewuebungen);
        chooseDay = (Button) getActivity().findViewById(R.id.choosedaybt);
        imgView = (ImageView) getActivity().findViewById(R.id.imageViewSleeping);
        chooseDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Dialog zum bestellen der Übungen von einem bestimmten Tag.
                AlertDialog.Builder b = new AlertDialog.Builder(getContext());
                b.setTitle("Wähle Tag");
                b.setIcon(R.drawable.questionsmall);
                final String[] types = {getResources().getString(R.string.montag), getResources().getString(R.string.dienstag),
                        getResources().getString(R.string.mittwoch), getResources().getString(R.string.donnerstag),
                        getResources().getString(R.string.freitag), getResources().getString(R.string.samstag),
                        getResources().getString(R.string.sonntag)};
                b.setItems(types, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        dialog.dismiss();
                        loadUebungen(types[position]);
                    }

                });

                b.show();
            }
        });

        gotosession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // Schreibt die Übungen in ein Bundle und übergibt sie der Trainingssession damit sie gestartet werden kann.
                Trainingsphase phase = port.getCurrentPhase();
                Cursor c = rep.getPhaseByStartDate(phase.getStartDate());
                c.moveToFirst();
                int iRowId = c.getColumnIndex(FiplyContract.PhasenEntry.COLUMN_ROWID);
                String rowid = c.getString(iRowId);
                Bundle args = new Bundle();
                args.putInt("uebungAnzahl", phase.getUebungByDay(day).size());

                Double gesgewicht = 0.0;
                for (int i = 0; i < uebs.size(); i++) {
                    args.putString("uebung" + (i + 1), phase.getUebungByDay(day).get(i).getUebungsID());
                    args.putDouble("gewicht" + (i + 1), RepMax.getTrainingsgewicht(phase.getWiederholungen(), Integer.valueOf(phase.getUebungByDay(day).get(i).getRepmax())));
                    gesgewicht += RepMax.getTrainingsgewicht(phase.getWiederholungen(), Integer.valueOf(phase.getUebungByDay(day).get(i).getRepmax()))*phase.getWiederholungen()*phase.getSaetze();
                }
                args.putString("phase", rowid);
                args.putDouble("gesamtgewicht", gesgewicht);

                Fragment fragment = new FTrainingssession();
                fragment.setArguments(args);
                displayFragment.displayTrainingsession(fragment, getFragmentManager());
            }
        });


        welcomeText.setText("Aktuelle Trainingsphase " + port.getPhaseIndex() + " von " + port.getPhasenListe().size() + "."); // Anzeige aktuelle Trainigsphase
        pBar.setProgress((port.getPhaseIndex() / 3) * 100 - 10);

        uebs = new ArrayList<String>();
        for (Uebung element : port.getCurrentPhase().getUebungListOfToday()) { // Holt sich die aktuellen Übungen und schreibt sie in die Liste
            uebs.add(String.valueOf(element.getUebungsName()));
            Log.wtf("WTF", element.getUebungsName());
        }
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), R.layout.uebungslist_item, uebs);
        uebList.setAdapter(adapt);
        String heutestehen = getResources().getString(R.string.heutestehen);
        String uebungenan = getResources().getString(R.string.uebungenan);
        uebungsText.setText(heutestehen + " " + port.howManyUebungToday() + " " + uebungenan);
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.GERMAN);
        title.setText(dateFormat.format(now));
        day = dateFormat.format(now);
        if (uebs.size() == 0) { // Wenn übungen anstehen kann man in die Trainingssession gehen.
            gotosession.setVisibility(View.INVISIBLE);
            final Animation animRevTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
            final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
            imgView.setVisibility(View.VISIBLE);
            gotosession.setAnimation(animRevTranslate);
            imgView.setAnimation(animTranslate);

        } else {
            gotosession.setVisibility(View.VISIBLE);
            final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
            final Animation animRevTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
            gotosession.startAnimation(animTranslate);
            imgView.setVisibility(View.INVISIBLE);
            imgView.setAnimation(animRevTranslate);
        }
    }

    // Lädt die Übungen für den bestimmten Tag.
    private void loadUebungen(String dayyo) {
        uebs = new ArrayList<String>();
        ArrayAdapter<String> adapt = new ArrayAdapter<String>(getActivity(), R.layout.uebungslist_item, uebs);
        uebList.setAdapter(adapt);
        String esstehen = getResources().getString(R.string.esstehen);
        String uebungenan = getResources().getString(R.string.uebungenan);
        day = dayyo;
        for (Uebung element : port.getCurrentPhase().getUebungByDay(day)) {
            uebs.add(String.valueOf(element.getUebungsName()));
            Log.wtf("WTF", element.getUebungsName());
        }
        uebungsText.setText(esstehen + " " + port.getCurrentPhase().getUebungByDay(day).size() + " " + uebungenan);
        title.setText(dayyo);
        if (uebs.size() == 0) {
            gotosession.setVisibility(View.INVISIBLE);
            final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
            final Animation animRevTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
            imgView.setVisibility(View.VISIBLE);
            imgView.setAnimation(animTranslate);
            gotosession.setAnimation(animRevTranslate);
        } else {
            gotosession.setVisibility(View.VISIBLE);
            final Animation animTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate);
            final Animation animRevTranslate = AnimationUtils.loadAnimation(getContext(), R.anim.anim_translate_revert);
            gotosession.startAnimation(animTranslate);
            imgView.setVisibility(View.INVISIBLE);
            imgView.setAnimation(animRevTranslate);

        }
    }
}
