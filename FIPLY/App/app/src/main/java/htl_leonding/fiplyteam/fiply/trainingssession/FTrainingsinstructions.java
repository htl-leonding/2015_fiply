package htl_leonding.fiplyteam.fiply.trainingssession;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import htl_leonding.fiplyteam.fiply.R;

public class FTrainingsinstructions extends Fragment {

    String videoLink = "-";
    TextView tvUebungName, tvUebungSchwierigkeit, tvUebungMuskelgruppe, tvUebungBeschreibung, tvUebungAnleitung, tvLinkVideo;
    ImageButton ibLinkVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_trainingsinstructions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvUebungName = (TextView) getActivity().findViewById(R.id.detailUebungName);
        tvUebungMuskelgruppe = (TextView) getActivity().findViewById(R.id.detailMuskelGruppe);
        tvUebungSchwierigkeit = (TextView) getActivity().findViewById(R.id.detailSchwierigkeit);
        tvUebungBeschreibung = (TextView) getActivity().findViewById(R.id.detailBeschreibung);
        tvUebungAnleitung = (TextView) getActivity().findViewById(R.id.detailAnleitung);

        /*
        tvUebungName.setText(getArguments().getString("name"));
        tvUebungSchwierigkeit.setText(getArguments().getString("zielgruppe"));
        tvUebungMuskelgruppe.setText(getArguments().getString("muskelgruppe"));
        tvUebungBeschreibung.setText(getArguments().getString("beschreibung"));
        tvUebungAnleitung.setText(getArguments().getString("anleitung"));
        videoLink = getArguments().getString("video");
        */
        videoLink="";
        if (videoLink != null && !videoLink.equals("-")) {
            tvLinkVideo = (TextView) getActivity().findViewById(R.id.tvLinkVideo);
            ibLinkVideo = (ImageButton) getActivity().findViewById(R.id.ibLinkVideo);

            View.OnClickListener clickVideo = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showFullVideo = new Intent("fiply.VIDEO");
                    showFullVideo.putExtra("videoLink", videoLink);
                    startActivity(showFullVideo);
                }
            };

            tvLinkVideo.setOnClickListener(clickVideo);
            ibLinkVideo.setOnClickListener(clickVideo);
        }
    }
}
