package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
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

@Deprecated
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
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //Fügt dieses Fragment zum Backstack hinzu, somit kann man bei drücken des BackButtons darauf zurückspringen
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.fraPlace, new FUebungskatalog());
                fragmentTransaction.commit();
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        kvr.updateKeyValue("filterName", nameFilter.getText().toString());
        super.onDestroyView();
    }

    // Popup, das nach dem Namen fragt wird hier erstellt.
    private void filterName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Filtertext:");
        builder.setIcon(R.drawable.questionsmall);
        final EditText input = new EditText(getContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                kvr.updateKeyValue("filterName", input.getText().toString());
                getActivity().findViewById(R.id.fraPlace).invalidate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                getActivity().findViewById(R.id.fraPlace).invalidate();
            }
        });

        builder.show();
    }
}
