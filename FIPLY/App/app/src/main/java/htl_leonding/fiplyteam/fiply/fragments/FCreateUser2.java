package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

/**
 * Created by Gerildo on 10.01.2016.
 */
public class FCreateUser2 extends Fragment {

    SeekBar sbWeight;
    SeekBar sbHeight;
    TextView tvWeight;
    TextView tvHeight;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_createuser2, container, false);
    }

    @Override
    public void onDestroyView() {
        KeyValueRepository.getInstance().insertKeyValue("userHeight", (String.valueOf(sbHeight.getProgress())));
        KeyValueRepository.getInstance().insertKeyValue("userWeight", (String.valueOf(sbWeight.getProgress())));
        super.onDestroyView();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sbHeight = (SeekBar) getView().findViewById(R.id.sbHeight);
        sbWeight = (SeekBar) getView().findViewById(R.id.sbWeight);
        tvHeight = (TextView) getView().findViewById(R.id.tvSizeStat);
        tvWeight = (TextView) getView().findViewById(R.id.tvWeightStat);

        sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvHeight.setText("Größe: " + sbHeight.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sbWeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvWeight.setText("Gewicht: " + sbWeight.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}
