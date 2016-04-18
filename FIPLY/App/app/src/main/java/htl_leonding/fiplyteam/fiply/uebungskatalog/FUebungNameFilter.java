package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

/**
 * Created by Gerildo on 18.04.2016.
 */
public class FUebungNameFilter extends Fragment {
    KeyValueRepository kvr;

    Button back;
    Button resetFilter;
    EditText nameFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_uebungnamefilter, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kvr = KeyValueRepository.getInstance();

        back = (Button) getView().findViewById(R.id.btNameFilterBack);
        resetFilter = (Button) getView().findViewById(R.id.btResetNameFilter);
        nameFilter = (EditText) getView().findViewById(R.id.etNameFilter);


        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kvr.updateKeyValue("filterName","");
                Toast.makeText(getContext(),"Namensfilter zurückgesetzt", Toast.LENGTH_SHORT);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //senki sei back button code
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        kvr.updateKeyValue("filterName", nameFilter.getText().toString());
        super.onDestroyView();
    }
}
