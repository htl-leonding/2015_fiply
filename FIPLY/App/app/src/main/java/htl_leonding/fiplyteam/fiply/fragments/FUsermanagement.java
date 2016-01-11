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

public class FUsermanagement extends Fragment {

    Button btCreate;
    Button btEdit;
    Button btChoose;



    /**
     * Lädt das fragment_usermanagement in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_usermanagement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        btCreate = (Button) getView().findViewById(R.id.btCreate);
        btChoose = (Button) getView().findViewById(R.id.btChoose);
        btEdit = (Button) getView().findViewById(R.id.btEdit);


        btCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FCreateUser fCreateUser = new FCreateUser();
                displayView(fCreateUser);
            }
        });

        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FEditUser fEditUser = new FEditUser();
                displayView(fEditUser);
            }
        });

        btChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FChooseUser fChooseUser = new FChooseUser();
                displayView(fChooseUser);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    private void displayView(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(this.getId(), fragment);
        fragmentTransaction.commit();
    }

}
