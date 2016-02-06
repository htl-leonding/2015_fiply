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

public class FTrainingsinstructions extends Fragment{

    String uebungsName;
    String uebungsVideo;
    String uebungsZG;
    String uebungsMG;
    String uebungsDesc;
    String uebungsAnl;

    TextView tvUebungName;
    TextView tvUebungZG;
    TextView tvUebungMG;
    TextView tvUebungDesc;
    TextView tvUebungAnl;
    TextView tvLinkVideo;
    ImageButton ibLinkVideo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_trainingsinstructions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uebungsName = getArguments().getString("name");
        uebungsVideo = getArguments().getString("video");
        uebungsZG = getArguments().getString("zielgruppe");
        uebungsMG = getArguments().getString("muskelgruppe");
        uebungsDesc = getArguments().getString("beschreibung");
        uebungsAnl = getArguments().getString("anleitung");

        tvUebungName = (TextView) getView().findViewById(R.id.detailUebungName);
        tvUebungMG = (TextView) getView().findViewById(R.id.detailMuskelGruppe);
        tvUebungZG = (TextView) getView().findViewById(R.id.detailZielGruppe);
        tvUebungDesc = (TextView) getView().findViewById(R.id.detailBeschreibung);
        tvUebungAnl = (TextView) getView().findViewById(R.id.detailAnleitung);
        tvLinkVideo = (TextView) getView().findViewById(R.id.tvLinkVideo);
        ibLinkVideo = (ImageButton) getView().findViewById(R.id.ibLinkVideo);

        tvUebungName.setText(uebungsName);
        tvUebungZG.setText(uebungsZG);
        tvUebungMG.setText(uebungsMG);
        tvUebungDesc.setText(uebungsDesc);
        tvUebungAnl.setText(uebungsAnl);

        View.OnClickListener clickVideo = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent showFullVideo = new Intent("fiply.VIDEO");
                //Bundle extras = new Bundle();
                //extras.putString("videoLink", "asdasdasdadadas");
                //showFullVideo.putExtras(extras);
                showFullVideo.putExtra("videoLink",uebungsVideo);
                startActivity(showFullVideo);
            }
        };

        tvLinkVideo.setOnClickListener(clickVideo);
        ibLinkVideo.setOnClickListener(clickVideo);
    }
}
