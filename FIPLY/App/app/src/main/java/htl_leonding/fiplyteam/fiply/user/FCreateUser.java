package htl_leonding.fiplyteam.fiply.user;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import java.sql.SQLException;

import javax.annotation.Nullable;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FCreateUser extends Fragment {

    ImageView imgName;
    final ThreadLocal<ImageView> imgGender = new ThreadLocal<>();
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
        etName = (EditText) getView().findViewById(R.id.etName);
        spGender = (Spinner) getView().findViewById(R.id.spGender);
        imgName = (ImageView) getView().findViewById(R.id.imgName);
        imgGender.set((ImageView) getView().findViewById(R.id.imgGender));

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
        imgGender.get().setImageDrawable(getResources().getDrawable(R.drawable.fcreateusergender));

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
        if (!kvr.getKeyValue("userGender").getString(1).equals("Gender"))
            spGender.setSelection(genderAdapter.getPosition(kvr.getKeyValue("userGender").getString(1)));

        etName.setText(kvr.getKeyValue("userName").getString(1));
    }
}
