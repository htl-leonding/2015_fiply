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
    TextView tvUebungName, tvUebungZG, tvUebungMG, tvUebungDesc, tvUebungAnl, tvLinkVideo;
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
        tvUebungMG = (TextView) getActivity().findViewById(R.id.detailMuskelGruppe);
        tvUebungZG = (TextView) getActivity().findViewById(R.id.detailZielGruppe);
        tvUebungDesc = (TextView) getActivity().findViewById(R.id.detailBeschreibung);
        tvUebungAnl = (TextView) getActivity().findViewById(R.id.detailAnleitung);

        tvUebungName.setText(getArguments().getString("name"));
        tvUebungZG.setText(getArguments().getString("zielgruppe"));
        tvUebungMG.setText(getArguments().getString("muskelgruppe"));
        tvUebungDesc.setText(getArguments().getString("beschreibung"));
        tvUebungAnl.setText(getArguments().getString("anleitung"));

        videoLink = getArguments().getString("video");
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
