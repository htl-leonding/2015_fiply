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
    String uebungsEquip;
    String uebungsMG;
    String uebungsDesc;
    String uebungsAnl;
    String uebungsDiff;

    Boolean videoInitialized = false;

    TextView tvUebungName;
    TextView tvUebungEquip;
    TextView tvUebungMG;
    TextView tvUebungDesc;
    TextView tvUebungAnl;
    TextView tvUebungDiff;

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
        uebungsEquip = getArguments().getString("equipment");
        uebungsMG = getArguments().getString("muskelgruppe");
        uebungsDesc = getArguments().getString("beschreibung");
        uebungsAnl = getArguments().getString("anleitung");
        uebungsDiff = getArguments().getString("schwierigkeit");


        tvUebungName = (TextView) getActivity().findViewById(R.id.detailUebungName);
        tvUebungMG = (TextView) getActivity().findViewById(R.id.detailMuskelGruppe);
        tvUebungEquip = (TextView) getActivity().findViewById(R.id.detailEquipment);
        tvUebungDesc = (TextView) getActivity().findViewById(R.id.detailBeschreibung);
        tvUebungAnl = (TextView) getActivity().findViewById(R.id.detailAnleitung);
        tvUebungDiff = (TextView) getActivity().findViewById(R.id.detailSchwierigkeit);
        checkBox = (CheckBox) getActivity().findViewById(R.id.cbVideo);

        tvUebungName.setText(uebungsName);
        tvUebungEquip.setText(uebungsEquip);
        tvUebungMG.setText(uebungsMG);
        tvUebungDesc.setText(uebungsDesc);
        tvUebungAnl.setText(uebungsAnl);
        tvUebungDiff.setText(uebungsDiff);

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
