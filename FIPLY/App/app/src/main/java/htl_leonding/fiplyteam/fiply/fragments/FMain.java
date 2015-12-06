package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import htl_leonding.fiplyteam.fiply.R;

public class FMain extends Fragment {
    Button startUe;
    Button startTS;
    Button startEU;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startUe = (Button) getView().findViewById(R.id.btStartUe);
        startTS = (Button) getView().findViewById(R.id.btStartTr);
        startEU = (Button) getView().findViewById(R.id.btStartEU);

        startUe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                FUebungskatalog fUebungskatalog = new FUebungskatalog();
                fragmentTransaction.replace(R.id.fraPlace, fUebungskatalog);
                fragmentTransaction.commit();
            }
        });

        startTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                FTrainingssession fTrainingssession = new FTrainingssession();
                fragmentTransaction.replace(R.id.fraPlace, fTrainingssession);
                fragmentTransaction.commit();
            }
        });

        startEU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                FUsererstellung fUsererstellung = new FUsererstellung();
                fragmentTransaction.replace(R.id.fraPlace, fUsererstellung);
                fragmentTransaction.commit();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_main, container, false);
    }
}
