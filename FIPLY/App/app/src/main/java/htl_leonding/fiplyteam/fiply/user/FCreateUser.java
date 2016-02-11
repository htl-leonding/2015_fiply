package htl_leonding.fiplyteam.fiply.user;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FCreateUser extends Fragment {

    ImageView imgGender;
    ImageView imgName;
    EditText etName;
    Spinner spGender;
    ArrayAdapter<CharSequence> genderAdapter;
    KeyValueRepository kvr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kvr = KeyValueRepository.getInstance();
        etName = (EditText) getActivity().findViewById(R.id.etName);
        spGender = (Spinner) getActivity().findViewById(R.id.spGender);
        imgName = (ImageView) getActivity().findViewById(R.id.imgName);
        imgGender = (ImageView) getActivity().findViewById(R.id.imgGender);

        init();

        try {
            setSettings();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void init() {
        //load images
        imgName.setImageDrawable(getResources().getDrawable(R.drawable.fcreateusername));
        imgGender.setImageDrawable(getResources().getDrawable(R.drawable.fcreateusergender));

        //init Adapter
        genderAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.gender, android.R.layout.simple_spinner_item);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //set Adapter
        spGender.setAdapter(genderAdapter);
    }

    @Override
    public void onDestroyView() {
        KeyValueRepository.getInstance().updateKeyValue("userName", etName.getText().toString());
        KeyValueRepository.getInstance().updateKeyValue("userGender", spGender.getSelectedItem().toString());
        super.onDestroyView();
    }

    private void setSettings() throws SQLException {
        etName.setText(kvr.getKeyValue("userName").getString(1));
    }
}
