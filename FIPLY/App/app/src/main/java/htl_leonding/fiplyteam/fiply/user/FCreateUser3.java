package htl_leonding.fiplyteam.fiply.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FCreateUser3 extends Fragment {
    Spinner spAge;
    Spinner spProf;
    ArrayAdapter<CharSequence> ageAdapter;
    ArrayAdapter<CharSequence> profAdapter;
    KeyValueRepository kvr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser3, container, false);
    }

    //Initialisiert das repo und die Layout elemente

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        spAge = (Spinner) getActivity().findViewById(R.id.spAge);
        spProf = (Spinner) getActivity().findViewById(R.id.spProf);
        kvr = KeyValueRepository.getInstance();
        init();
        try {
            setSettings();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Wenn die View geschlossen wird, werden die Werte in der KeyValueRepository geupdated.
     */
    @Override
    public void onDestroyView() {
        kvr.updateKeyValue("userAge", spAge.getSelectedItem().toString());
        kvr.updateKeyValue("userProf", spProf.getSelectedItem().toString());
        super.onDestroyView();
    }

    //Initialisierungsmethode
    private void init() {

        //Create Adapters
        ageAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.ageGroups, android.R.layout.simple_spinner_item);
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        profAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.proficiency, android.R.layout.simple_spinner_item);
        profAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Set Adapters
        spAge.setAdapter(ageAdapter);
        spProf.setAdapter(profAdapter);
    }

    /**
     * Setzt die input felder auf den aktuellen Wert, falls einer vorhanden ist.
     * @throws SQLException
     */
    private void setSettings() throws SQLException {
        spAge.setSelection(ageAdapter.getPosition(kvr.getKeyValue("userAge").getString(1)));
        spProf.setSelection(ageAdapter.getPosition(kvr.getKeyValue("userProf").getString(1)));
    }

}
