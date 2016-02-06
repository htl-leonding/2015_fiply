package htl_leonding.fiplyteam.fiply.uebungskatalog;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.TextView;

import htl_leonding.fiplyteam.fiply.R;

public class FUebungDetail extends Fragment {

    String uebungsName;
    String uebungsVideo;
    String uebungsZG;
    String uebungsMG;
    String uebungsDesc;
    String uebungsAnl;
    Boolean videoInitialized = false;

    TextView tvUebungName;
    TextView tvUebungZG;
    TextView tvUebungMG;
    TextView tvUebungDesc;
    TextView tvUebungAnl;
    WebView wvUebungVideo;
    CheckBox checkBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_uebungdetail, container, false);
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


        tvUebungName = (TextView) getActivity().findViewById(R.id.detailUebungName);
        tvUebungMG = (TextView) getActivity().findViewById(R.id.detailMuskelGruppe);
        tvUebungZG = (TextView) getActivity().findViewById(R.id.detailZielGruppe);
        tvUebungDesc = (TextView) getActivity().findViewById(R.id.detailBeschreibung);
        tvUebungAnl = (TextView) getActivity().findViewById(R.id.detailAnleitung);
        checkBox = (CheckBox) getActivity().findViewById(R.id.cbVideo);

        tvUebungName.setText(uebungsName);
        tvUebungZG.setText(uebungsZG);
        tvUebungMG.setText(uebungsMG);
        tvUebungDesc.setText(uebungsDesc);
        tvUebungAnl.setText(uebungsAnl);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = checkBox.isChecked();
                if (checked) {
                    if (!getVideoInitialized()) {
                        setupVideo();
                    }
                    wvUebungVideo.setVisibility(View.VISIBLE);
                } else {
                    wvUebungVideo.setVisibility(View.GONE);
                }
            }
        });

        if (getArguments().getBoolean("showVideo")) {
            setupVideo();
            checkBox.setChecked(true);
            wvUebungVideo.setVisibility(View.VISIBLE);
        }
    }

    public void setupVideo() {
        wvUebungVideo = (WebView) getActivity().findViewById(R.id.detailUebungVideo);
        wvUebungVideo.getSettings().setJavaScriptEnabled(true);

        wvUebungVideo.setWebChromeClient(new WebChromeClient());
        //wvUebungVideo.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        wvUebungVideo.loadUrl(uebungsVideo);


        setVideoInitialized(true);
    }

    public Boolean getVideoInitialized() {
        return videoInitialized;
    }

    public void setVideoInitialized(Boolean videoInitialized) {
        this.videoInitialized = videoInitialized;
    }
}
