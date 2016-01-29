package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerald on 11/01/2016.
 */
public class FCreateUser4 extends Fragment {
    RadioButton rbBeginner;
    RadioButton rbAverage;
    RadioButton rbPro;
    RadioButton rbBodyBuilder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser4, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    public String getProficiency() {
        if (rbBeginner.isChecked()) {
            return "Beginner";
        } else if (rbAverage.isChecked()) {
            return "Average";
        } else if (rbPro.isChecked()) {
            return "Pro";
        } else {
            return "Bodybuilder";
        }
    }
}
