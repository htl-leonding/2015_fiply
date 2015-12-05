package htl_leonding.fiplyteam.fiply.fragments;

import android.content.Intent;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*
        startUe = (Button) getView().findViewById(R.id.btStartUe);
        startTS = (Button) getView().findViewById(R.id.btStartTr);
        startEU = (Button) getView().findViewById(R.id.btStartEU);

        startUe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openUe = new Intent("fiply.UEBUNGSKATALOGACTIVITY");
                startActivity(openUe);
            }
        });

        startTS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //FTrainingssession fTrainingssession = new FTrainingssession();
                //fragmentTransaction.replace(android.R.id.content, FTrainingssession);
                fragmentTransaction.commit();}
        });

        startEU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           //     FUsererstellung fUsererstellung = new FUsererstellung();
              //  fragmentTransaction.replace(android.R.id.content, fUsererstellung);
                fragmentTransaction.commit();
            }
        });
*/
        return inflater.inflate(R.layout.content_main, container, false);
    }
}
