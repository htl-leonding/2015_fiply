package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.graphics.Bitmap;
import android.graphics.Color;
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

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;

public class FUebungFilter extends Fragment {
    //Layout Elemente
    KeyValueRepository kvr;
    EditText filterName;
    ImageView bodyFilter;
    ImageView bodyFilterMask;
    Button applyFilter;
    Button resetFilter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_uebungfilter, container, false);

    }

    /**
     * Initialisiert das Repo und die Layout Elemente
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        kvr = KeyValueRepository.getInstance();
        filterName = (EditText) getView().findViewById(R.id.etFilterUebungName);
        applyFilter = (Button) getView().findViewById(R.id.btApplyFilter);
        resetFilter = (Button) getView().findViewById(R.id.btResetFilter);
        bodyFilter = (ImageView) getView().findViewById(R.id.ivBodyFilter);
        bodyFilterMask = (ImageView) getView().findViewById(R.id.ivBodyFilterMask);

        bodyFilterMask.setImageDrawable(getResources().getDrawable(R.drawable.userbodycolored));
        bodyFilter.setImageDrawable(getResources().getDrawable(R.drawable.userbody));

        //Setzt den OnClickListener für den Button applyFilter, dieser setzt den Filter.
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kvr.updateKeyValue("filterName", filterName.getText().toString());
            }
        });
        //Setzt den OnClickListener für den Button resetFilter, dieser löscht den Filter
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kvr.updateKeyValue("filterName", "");
                kvr.updateKeyValue("filterMuskelGruppe", "");
            }
        });

        //überprüft auf welchen Bereich der User geklickt hat und setzt den Filter.
        bodyFilterMask.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int tolerance = 25;
                int evX = (int) event.getX();
                int evY = (int) event.getY();
                int clickedColor = getHotspotColor(evX, evY);
                //RED area is arms
                if (closeMatch(getResources().getInteger(R.integer.redInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Arme");
                    kvr.updateKeyValue("filterMuskelGruppe", "Arme");
                }
                //BLACK area is Breast
                else if (closeMatch(getResources().getInteger(R.integer.blackInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Brust");
                    kvr.updateKeyValue("filterMuskelGruppe", "Brust");
                }
                //GREEN area is shoulders
                else if (closeMatch(getResources().getInteger(R.integer.greenInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Schultern");
                    kvr.updateKeyValue("filterMuskelGruppe", "Schultern");
                }
                //BLUE are is legs
                else if (closeMatch(getResources().getInteger(R.integer.blueInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Beine");
                    kvr.updateKeyValue("filterMuskelGruppe", "Beine");
                }
                //YELLOW area is core(stomach)
                else if (closeMatch(getResources().getInteger(R.integer.yellowInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Bauch");
                    kvr.updateKeyValue("filterMuskelGruppe", "Bauch");
                }
                return true;
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    //Überprüft ob 2 Farben ähnlich sind um zu überprüfen welchen Bereich er gedrückt hat.
    public boolean closeMatch(int color1, int color2, int tolerance) {
        if (Math.abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false;
        if (Math.abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false;
        if (Math.abs(Color.blue(color1) - Color.blue(color2)) > tolerance)
            return false;
        return true;
    }

    //Gibt die Farbe des gedrückten Punktes zurück
    public int getHotspotColor(int x, int y) {

        bodyFilterMask.setDrawingCacheEnabled(true);
        Bitmap hotspots = Bitmap.createBitmap(bodyFilterMask.getDrawingCache());
        bodyFilterMask.setDrawingCacheEnabled(false);
        return hotspots.getPixel(x, y);
    }

}
