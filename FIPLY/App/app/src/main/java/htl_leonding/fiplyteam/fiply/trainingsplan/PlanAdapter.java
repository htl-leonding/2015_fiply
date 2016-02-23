package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import htl_leonding.fiplyteam.fiply.R;


public class PlanAdapter extends ArrayAdapter<Trainingsplanlistitem> {

    public PlanAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public PlanAdapter(Context context, int resource, ArrayList<Trainingsplanlistitem> plans) {
        super(context, resource, plans);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.trainingsplan_item, null);
        }

        Trainingsplanlistitem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.plannametv);
            TextView tt2 = (TextView) v.findViewById(R.id.trainingszieltv);
            TextView tt3 = (TextView) v.findViewById(R.id.phasendauerbereichtv);
            ImageButton imgButton = (ImageButton) v.findViewById(R.id.imageButtonInfo);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }

            if (tt2 != null) {
                tt2.setText(p.getZiel());
            }

            if (tt3 != null) {
                tt3.setText(p.getDauer());
            }
            if (imgButton != null){
                imgButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        }

        return v;
    }
}
