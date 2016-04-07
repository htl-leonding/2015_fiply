package htl_leonding.fiplyteam.fiply.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FCreateUser2 extends Fragment {

    SeekBar sbWeight;
    SeekBar sbHeight;
    TextView tvWeight;
    TextView tvHeight;

    ImageView ivBody;

    KeyValueRepository kvr;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        return inflater.inflate(R.layout.fragment_createuser2, container, false);
    }

    /**
     * Wenn die View geschlossen wird, werden die Werte in der KeyValueRepository geupdated.
     */
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

    //Setter für die Höhe
    public void SetHeight(int height) {
        tvHeight.setText("Größe: " + String.valueOf(height));
        sbHeight.setProgress(height - 100);
    }

    /**
     * @return current selected Weight
     */
    public int GetWeight() {
        /*

         */
        return sbWeight.getProgress() + 40;
    }

    public void SetWeight(int weight) {
        tvWeight.setText("Gewicht: " + String.valueOf(weight));
        sbWeight.setProgress(weight - 40);

    }

    /**
     * Initialisiert das repo und die Layout elemente
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kvr = KeyValueRepository.getInstance();

        sbHeight = (SeekBar) getActivity().findViewById(R.id.sbHeight);
        sbWeight = (SeekBar) getActivity().findViewById(R.id.sbWeight);
        tvHeight = (TextView) getActivity().findViewById(R.id.tvSize);
        tvWeight = (TextView) getActivity().findViewById(R.id.tvWeight);
        ivBody = (ImageView) getActivity().findViewById(R.id.ivBody);

        //Setzt den OnSeekBarChangeListener für sbHeight, dieser aktualisiert den dargestellten Wert bei einer Änderung des SeekBars
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
        //Setzt den OnSeekBarChangeListener für sbWeight, dieser aktualisiert den dargestellten Wert bei einer Änderung des SeekBars
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

    /**
     * Setzt die input felder auf den aktuellen Wert, falls einer vorhanden ist.
     * @throws SQLException
     */
    private void setSettings() throws SQLException {
        Log.wtf("setSettings(2)", "Height:" + kvr.getKeyValue("userHeight").getString(1));
        Log.wtf("setSettings(2)", "Weight:" + kvr.getKeyValue("userWeight").getString(1));

        SetWeight(Integer.parseInt(kvr.getKeyValue("userWeight").getString(1)));
        SetHeight(Integer.parseInt(kvr.getKeyValue("userHeight").getString(1)));

        ivBody.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.userbody));
    }
}
