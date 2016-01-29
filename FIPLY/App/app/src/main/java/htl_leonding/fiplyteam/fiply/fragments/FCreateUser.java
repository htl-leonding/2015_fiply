package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

/**
 * Created by Gerildo on 09.01.2016.
 */
public class FCreateUser extends Fragment {

    ImageView imgName;
    ImageView imgGender;
    EditText etName;
    Spinner spGender;
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
        etName = (EditText) getView().findViewById(R.id.etName);
        spGender = (Spinner) getView().findViewById(R.id.spGender);
        imgName = (ImageView) getView().findViewById(R.id.imgName);
        imgGender = (ImageView) getView().findViewById(R.id.imgGender);

        init();

        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        //load images
        imgName.setImageDrawable(getResources().getDrawable(R.drawable.fcreateusername));
        imgGender.setImageDrawable(getResources().getDrawable(R.drawable.fcreateusergender));

        //init Adapter
        ArrayAdapter<CharSequence> genderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set Adapter
        spGender.setAdapter(genderAdapter);
    }

    @Override
    public void onDestroyView() {
        KeyValueRepository.getInstance().insertKeyValue("userName", etName.getText().toString());
        KeyValueRepository.getInstance().insertKeyValue("userGender", spGender.getSelectedItem().toString());
        super.onDestroyView();
    }

    private Bundle prepareBundle() {
        Bundle data = new Bundle();

        data.putString("name", etName.getText().toString());

        return data;
    }


    private void displayView(Fragment fragment) {
        fragment.setArguments(prepareBundle());
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(this.getId(), fragment);
        fragmentTransaction.commit();
    }
}
