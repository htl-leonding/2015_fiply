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
