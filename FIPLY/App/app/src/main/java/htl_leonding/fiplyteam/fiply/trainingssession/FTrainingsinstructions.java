package htl_leonding.fiplyteam.fiply.trainingssession;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class FTrainingsinstructions extends Fragment {

    TextView tvUebungName, tvUebungSchwierigkeit, tvUebungMuskelgruppe, tvUebungBeschreibung, tvUebungAnleitung,
            tvLinkVideo, tvUebungEquipment, tvUebungGewicht, tvUebungSaetze, tvUebungWiederholungen;
    ImageButton ibLinkVideo;
    Button btnNextUeb, btnLastUeb, btnHideClocks, btnHideMusic;

    Cursor aktUebung, aktPhase;
    String videoLink = "-", aktUebungsId = "";
    UebungenRepository ueRep;
    PhasenRepository phRep;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_trainingsinstructions, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        UebungenRepository.setContext(getActivity());
        PhasenRepository.setContext(getActivity());
        ueRep = UebungenRepository.getInstance();
        phRep = PhasenRepository.getInstance();

        tvUebungName = (TextView) getActivity().findViewById(R.id.detailUebungName);
        tvUebungMuskelgruppe = (TextView) getActivity().findViewById(R.id.detailMuskelGruppe);
        tvUebungSchwierigkeit = (TextView) getActivity().findViewById(R.id.detailSchwierigkeit);
        tvUebungBeschreibung = (TextView) getActivity().findViewById(R.id.detailBeschreibung);
        tvUebungAnleitung = (TextView) getActivity().findViewById(R.id.detailAnleitung);
        tvUebungEquipment = (TextView) getActivity().findViewById(R.id.detailEquipment);
        tvUebungGewicht = (TextView) getActivity().findViewById(R.id.detailGewicht);
        tvUebungSaetze = (TextView) getActivity().findViewById(R.id.detailSaetze);
        tvUebungWiederholungen = (TextView) getActivity().findViewById(R.id.detailWiederholungen);
        btnNextUeb = (Button) getActivity().findViewById(R.id.btnUebNext);
        btnLastUeb = (Button) getActivity().findViewById(R.id.btnUebLast);
        btnHideClocks = (Button) getActivity().findViewById(R.id.btnClocksHide);
        btnHideMusic = (Button) getActivity().findViewById(R.id.btnMusicHide);

        btnHideClocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.clocksLayout).getVisibility() != View.GONE) {
                    getActivity().findViewById(R.id.clocksLayout).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.fraTsUebung).invalidate();
                } else {
                    getActivity().findViewById(R.id.clocksLayout).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.fraTsUebung).invalidate();
                }
            }
        });

        btnHideMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity().findViewById(R.id.musicLayout).getVisibility() != View.GONE) {
                    getActivity().findViewById(R.id.musicLayout).setVisibility(View.GONE);
                    getActivity().findViewById(R.id.clocksLayout).invalidate();
                    getActivity().findViewById(R.id.fraTsUebung).invalidate();
                } else {
                    getActivity().findViewById(R.id.musicLayout).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.clocksLayout).invalidate();
                    getActivity().findViewById(R.id.fraTsUebung).invalidate();
                }
            }
        });

        videoLink = "";
        if (videoLink != null && !videoLink.equals("-")) {
            tvLinkVideo = (TextView) getActivity().findViewById(R.id.tvLinkVideo);
            ibLinkVideo = (ImageButton) getActivity().findViewById(R.id.ibLinkVideo);

            View.OnClickListener clickVideo = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent showFullVideo = new Intent("fiply.VIDEO");
                    showFullVideo.putExtra("videoLink", getVideoLink());
                    startActivity(showFullVideo);
                }
            };

            tvLinkVideo.setOnClickListener(clickVideo);
            ibLinkVideo.setOnClickListener(clickVideo);
        }
        updateUebungsfields(1);
        //updatePhaseAndGewicht();
    }

    public void updateUebungsfields(int ueId) {
        try {
            //setAktUebung(ueRep.getUebung(Long.valueOf(getArguments().getString("uebungsid" + ueId))));
            setAktUebung(ueRep.getUebung(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvUebungName.setText(getAktUebung().getString(1));
        tvUebungBeschreibung.setText(getAktUebung().getString(2));
        tvUebungAnleitung.setText(getAktUebung().getString(3));
        tvUebungMuskelgruppe.setText(getAktUebung().getString(4));
        tvUebungSchwierigkeit.setText(getAktUebung().getString(5));
        setVideoLink(getAktUebung().getString(6));
        tvUebungEquipment.setText(getAktUebung().getString(7));
    }

    public void updatePhaseAndGewicht(Double gewicht, int phId) {
        try {
            setAktPhase(phRep.getPhase(Long.valueOf(getArguments().getString("phasenid" + phId))));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tvUebungGewicht.setText(String.valueOf(gewicht) + " kg");
        tvUebungSaetze.setText(getAktPhase().getString(6));
        tvUebungWiederholungen.setText(getAktPhase().getString(7));
    }

    public Cursor getAktUebung() {
        return aktUebung;
    }

    public void setAktUebung(Cursor c) {
        this.aktUebung = c;
    }

    public Cursor getAktPhase() {
        return aktPhase;
    }

    public void setAktPhase(Cursor aktPhase) {
        this.aktPhase = aktPhase;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }
}
