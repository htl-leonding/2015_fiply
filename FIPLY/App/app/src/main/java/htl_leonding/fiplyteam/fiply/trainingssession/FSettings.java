package htl_leonding.fiplyteam.fiply.trainingssession;


import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.menu.FMain;
import htl_leonding.fiplyteam.fiply.trainingsplan.RepMax;
import htl_leonding.fiplyteam.fiply.trainingsplan.Trainingsphase;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;


public class FSettings extends Fragment {

    TextView welcomeText;
    PlanSessionPort port;
    TextView uebungsText;
    Button chooseDay;
    Button gotosession;
    PhasenRepository rep;
    ProgressBar pBar;

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

        if (!port.isGenerated())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.alertnoplan)
                    .setTitle(R.string.fehler).setIcon(R.drawable.alertsmall);
            AlertDialog dialog = builder.create();
            dialog.show();
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Fragment fragment = new FMain();
            fragmentTransaction.replace(R.id.fraPlace, fragment);
            fragmentTransaction.commit();
            onDestroy();
        }

        welcomeText = (TextView) getActivity().findViewById(R.id.sessionsettingwelcome);
        uebungsText = (TextView) getActivity().findViewById(R.id.sessionsettinguebungen);
        chooseDay = (Button) getActivity().findViewById(R.id.choosedaybt);
        gotosession = (Button) getActivity().findViewById(R.id.gotosession);
        pBar = (ProgressBar) getActivity().findViewById(R.id.progressBar);
        Drawable draw= getActivity().getDrawable(R.drawable.progressbar);
        pBar.setProgressDrawable(draw);

        gotosession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Trainingsphase phase = port.getCurrentPhase();
                Cursor c = rep.getPhaseByStartDate(phase.getStartDate());
                c.moveToFirst();
                int iRowId = c.getColumnIndex(FiplyContract.PhasenEntry.COLUMN_ROWID);
                String rowid = c.getString(iRowId);
                Bundle args = new Bundle();
                args.putInt("uebungAnzahl", port.howManyUebungToday());
                for (int i = 0; i < port.howManyUebungToday(); i++) {
                    args.putString("uebung" + (i + 1), phase.getUebungListOfToday().get(i).getUebungsID());
                    args.putInt("gewicht" + (i + 1), RepMax.getTrainingsgewicht(phase.getWiederholungen(), Integer.valueOf(phase.getUebungListOfToday().get(i).getRepmax())));
                }
                args.putString("phase", rowid);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                Fragment fragment = new FTrainingssession();
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.fraPlace, fragment);
                fragmentTransaction.commit();
            }
        });
        chooseDay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putStringArray("days", port.getDays());
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                Fragment fragment = new FDialogChooseDay();
                fragment.setArguments(args);
                fragmentTransaction.replace(R.id.fraPlace, fragment);
                fragmentTransaction.commit();
            }
        });

        welcomeText.setText(port.getProgress());
        pBar.setProgress((port.getPhaseIndex()/3)*100 - 10);

        if (!port.isAnyUebungToday()){
            uebungsText.setText(R.string.nouebungenfortoday);
            chooseDay.setVisibility(View.VISIBLE);
        }else {
            uebungsText.setText("Heute stehen " + port.howManyUebungToday() + " Übungen an");
            chooseDay.setVisibility(View.INVISIBLE);
        }
    }
}
