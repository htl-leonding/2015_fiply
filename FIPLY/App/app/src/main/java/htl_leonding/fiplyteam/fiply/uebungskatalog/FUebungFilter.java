package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

public class FUebungFilter extends Fragment {
    //Layout Elemente
    KeyValueRepository kvr;
    ImageView bodyFilter;
    ImageView bodyFilterMask;
    Button resetFilter;
    Button back;
    Context context;

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
        context = getContext();
        resetFilter = (Button) getView().findViewById(R.id.btResetFilter);
        back = (Button) getView().findViewById(R.id.btMuskelGruppeFilterBack);

        bodyFilterMask = (ImageView) getView().findViewById(R.id.ivBodyFilterMask);
        bodyFilter = (ImageView) getView().findViewById(R.id.ivBodyFilter);



        bodyFilterMask.setImageDrawable(getResources().getDrawable(R.drawable.userbodycolored));
        bodyFilter.setImageDrawable(getResources().getDrawable(R.drawable.userbody));

        bodyFilter.bringToFront();


        //Setzt den OnClickListener für den Button resetFilter, dieser löscht den Filter
        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kvr.updateKeyValue("filterMuskelGruppe", "");
                Toast.makeText(getContext(), "Muskelgruppenfilter zurückgesetzt", Toast.LENGTH_SHORT);
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
                    Toast.makeText(context,"Armübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Arme");
                }
                //BLACK area is Breast
                else if (closeMatch(getResources().getInteger(R.integer.blackInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Brust");
                    Toast.makeText(context,"Brustübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Brust");
                }
                //GREEN area is shoulders
                else if (closeMatch(getResources().getInteger(R.integer.greenInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Schultern");
                    Toast.makeText(context,"Schulterübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Schultern");
                }
                //BLUE are is legs
                else if (closeMatch(getResources().getInteger(R.integer.blueInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Beine");
                    Toast.makeText(context,"Beinübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Beine");
                }
                //YELLOW area is core(stomach)
                else if (closeMatch(getResources().getInteger(R.integer.yellowInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Bauch");
                    Toast.makeText(context,"Bauchübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Bauch");
                }
                //YELLOW-GREEN are is back
                else if (closeMatch(getResources().getInteger(R.integer.yelGreenInt), clickedColor, tolerance)) {
                    Log.wtf("Area clicked: ", "Rücken");
                    Toast.makeText(context,"Rückenübungen ausgewählt",Toast.LENGTH_SHORT).show();
                    kvr.updateKeyValue("filterMuskelGruppe", "Rücken");
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
