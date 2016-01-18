package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerald on 11/01/2016.
 */
public class FCreateUser3 extends Fragment {
    Spinner spAge;
    Spinner spProf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        spAge = (Spinner) getView().findViewById(R.id.spAge);
        spProf = (Spinner) getView().findViewById(R.id.spProf);

        init();

        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {

        //Create Adapters
        ArrayAdapter<CharSequence> ageAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ageGroups, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> profAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.proficiency, android.R.layout.simple_spinner_item);
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set Adapters
        spAge.setAdapter(ageAdapter);
        spProf.setAdapter(profAdapter);


    }

}
