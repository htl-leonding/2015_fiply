package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerildo on 09.01.2016.
 */
public class FCreateUser extends Fragment {

    Button btCUS2;
    EditText etName;
    EditText etEmail;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etName = (EditText) getView().findViewById(R.id.etName);
        etEmail = (EditText) getView().findViewById(R.id.etEmail);
        btCUS2 = (Button) getView().findViewById(R.id.btCUS2);


        btCUS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FCreateUser2 fcreateUser2 = new FCreateUser2();
                displayView(fcreateUser2);
            }
        });

        super.onViewCreated(view, savedInstanceState);
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
