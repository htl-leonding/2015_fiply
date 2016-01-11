package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Created by Gerildo on 10.01.2016.
 */
public class FCreateUser2 extends Fragment {

    SeekBar sbWeight;
    SeekBar sbHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_createuser, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        sbHeight = (SeekBar) getView().findViewById(R.id.sbHeight);
        sbWeight = (SeekBar) getView().findViewById(R.id.sbWeight);
        super.onViewCreated(view, savedInstanceState);
    }

    public int getHeight() {
        return sbHeight.getProgress();
    }

    public int getWeight() {
        return sbWeight.getProgress();
    }

}
