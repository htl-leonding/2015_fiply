package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import java.util.Date;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerald on 11/01/2016.
 */
public class FCreateUser3 extends Fragment {
    DatePicker dpAge;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser3, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        dpAge = (DatePicker) getView().findViewById(R.id.dpAge);
        super.onViewCreated(view, savedInstanceState);
    }

    public String getDateOfBirth() {
        return new Date(dpAge.getYear(), dpAge.getMonth(), dpAge.getDayOfMonth()).toString();
    }
}
