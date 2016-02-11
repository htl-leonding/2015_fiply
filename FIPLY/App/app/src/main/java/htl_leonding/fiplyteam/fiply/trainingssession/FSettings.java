package htl_leonding.fiplyteam.fiply.trainingssession;


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
import android.widget.TextView;

import org.w3c.dom.Text;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.menu.FMain;


public class FSettings extends Fragment {

    TextView welcomeText;
    PlanSessionPort port;
    TextView uebungsText;
    Button chooseDay;

    PhasenRepository phasenRep;
    InstruktionenRepository instRep;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_sessionsettings, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PhasenRepository.setContext(getContext());
        InstruktionenRepository.setContext(getContext());
        port = PlanSessionPort.getInstance();
        port.init();

        if (!port.isGenerated())
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.alertnoplan)
                    .setTitle(R.string.ok);
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

        if (!port.isAnyUebungToday()){
            uebungsText.setText("Heute stehen keine Uebungen an! Wollen Sie die Uebungen eines anderen Tags durchführen?");
            chooseDay.setVisibility(View.VISIBLE);
        }else {
            uebungsText.setText("Heute stehen " + port.howManyUebungToday() + " Uebungen an!");
            chooseDay.setVisibility(View.INVISIBLE);
        }
    }
}
