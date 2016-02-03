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

import java.sql.SQLException;

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

    KeyValueRepository kvr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_createuser2, container, false);
    }

    @Override
    public void onDestroyView() {
        KeyValueRepository.getInstance().updateKeyValue("userHeight", String.valueOf(GetHeight()));
        KeyValueRepository.getInstance().updateKeyValue("userWeight", String.valueOf(GetWeight()));
        super.onDestroyView();
    }


    /**
     * @return current selected Height
     */
    public int GetHeight() {
        /*
        Adds 100 to the Seekbar value (0cm-100cm) so you get a range of 100cm-200cm which is more realistic.
        In the same vein having a range of 0-200 is bad, as the first 100cm of the range are just clutter
         */
        return sbHeight.getProgress() + 100;
    }

    public void SetHeight(int height) {
        sbHeight.setProgress(height - 100);
    }

    /**
     * @return current selected Weight
     */
    public int GetWeight() {
        /*

         */
        return sbHeight.getProgress() + 40;
    }

    public void SetWeight(int weight) {
        sbWeight.setProgress(weight - 40);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kvr = KeyValueRepository.getInstance();

        sbHeight = (SeekBar) getView().findViewById(R.id.sbHeight);
        sbWeight = (SeekBar) getView().findViewById(R.id.sbWeight);
        tvHeight = (TextView) getView().findViewById(R.id.tvSizeStat);
        tvWeight = (TextView) getView().findViewById(R.id.tvWeightStat);

        sbHeight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvHeight.setText("Größe: " + GetHeight());
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
                tvWeight.setText("Gewicht: " + GetWeight());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        try {
            setSettings();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        super.onViewCreated(view, savedInstanceState);
    }

    private void setSettings() throws SQLException {
        SetWeight(Integer.getInteger(kvr.getKeyValue("userHeight").getString(0)));
        SetHeight(Integer.getInteger(kvr.getKeyValue("userWeight").getString(0)));
    }
}
