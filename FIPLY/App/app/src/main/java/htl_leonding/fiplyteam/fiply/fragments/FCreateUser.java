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
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

/**
 * Created by Gerildo on 09.01.2016.
 */
public class FCreateUser extends Fragment {

    Button btCUS2;
    EditText etFirstName;
    EditText etLastName;
    Bundle userArgs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userArgs = new Bundle();
        return inflater.inflate(R.layout.fragment_createuser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        etFirstName = (EditText) getView().findViewById(R.id.etFirstName);
        etLastName = (EditText) getView().findViewById(R.id.etLastName);
        btCUS2 = (Button) getView().findViewById(R.id.btCUS2);


        btCUS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                KeyValueRepository kvRep = KeyValueRepository.getInstance();
                if (etFirstName.getText().length() > 0 && etLastName.getText().length() > 0) {
                    userArgs.putString("userFirstName", etFirstName.getText().toString());
                    userArgs.putString("userLastName", etLastName.getText().toString());
                }


                FCreateUser2 fcreateUser2 = new FCreateUser2();
                fcreateUser2.setArguments(userArgs);
                displayView(fcreateUser2);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    public String getFirstName() {
        return etFirstName.getText().toString();
    }

    public String getLastName() {
        return etLastName.getText().toString();
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
