package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import htl_leonding.fiplyteam.fiply.R;

public class UebungsAdapter extends ArrayAdapter<Uebung> {

    public UebungsAdapter(Context context, ArrayList<Uebung> uebungen){
        super(context, 0, uebungen);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Uebung ueb = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.trainingsplan_uebungenitem, parent, false);
        }

        TextView uebungsName = (TextView) convertView.findViewById(R.id.uebungsName);
        TextView uebungsID = (TextView) convertView.findViewById(R.id.uebungsID);
        TextView wochentag = (TextView) convertView.findViewById(R.id.wochentag);
        TextView muskelgruppe = (TextView) convertView.findViewById(R.id.muskelgruppe);

        uebungsName.setText(ueb.getUebungsName());
        uebungsID.setText(ueb.getUebungsID());
        wochentag.setText(ueb.getWochenTag());
        muskelgruppe.setText(ueb.getMuskelgruppe());

        return convertView;
    }

}
