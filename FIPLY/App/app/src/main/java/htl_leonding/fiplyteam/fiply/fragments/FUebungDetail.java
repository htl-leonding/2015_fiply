package htl_leonding.fiplyteam.fiply.fragments;

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

/**
 * Created by Gerildo on 08.12.2015.
 */
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


        tvUebungName = (TextView) getView().findViewById(R.id.detailUebungName);
        tvUebungMG = (TextView) getView().findViewById(R.id.detailMuskelGruppe);
        tvUebungZG = (TextView) getView().findViewById(R.id.detailZielGruppe);
        tvUebungDesc = (TextView) getView().findViewById(R.id.detailBeschreibung);
        tvUebungAnl = (TextView) getView().findViewById(R.id.detailAnleitung);
        checkBox = (CheckBox) getView().findViewById(R.id.cbVideo);

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
                    if(!getVideoInitialized())
                    {
                        setupVideo();
                    }
                    wvUebungVideo.setVisibility(View.VISIBLE);
                } else{
                    wvUebungVideo.setVisibility(View.GONE);
                }
            }
        });

        if(getArguments().getBoolean("showVideo") == true) {
            setupVideo();
            checkBox.setChecked(true);
            wvUebungVideo.setVisibility(View.VISIBLE);
        }
    }
    public void setupVideo()
    {
        wvUebungVideo = (WebView) getView().findViewById(R.id.detailUebungVideo);
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
