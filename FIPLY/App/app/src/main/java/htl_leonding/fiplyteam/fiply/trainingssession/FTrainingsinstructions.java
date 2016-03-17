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
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;

import java.sql.SQLException;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;
import htl_leonding.fiplyteam.fiply.menu.MainActivity;

public class FTrainingsinstructions extends Fragment {

    TextView tvUebungName, tvUebungSchwierigkeit, tvUebungMuskelgruppe, tvUebungBeschreibung, tvUebungAnleitung,
            tvLinkVideo, tvUebungEquipment, tvUebungGewicht, tvUebungSaetze, tvUebungWiederholungen;
    ImageButton ibLinkVideo;
    Button btnNextUeb, btnLastUeb, btnHideClocks, btnHideMusic, btnEndTraining;
    ScrollView scrollView;

    /**
     * Dieses Runnable stellt sicher dass das Scrolldown hinten auf die Liste von UI-Änderungen gesetzt wird
     * und so erst ausgeführt wird nach den anderen UI-Änderungen
     */
    public Runnable scrDown = new Runnable() {
        @Override
        public void run() {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };
    Cursor aktUebung, aktPhase;
    int aktUebungNr, maxUebungNr;
    String videoLink = "-";
    UebungenRepository ueRep;
    PhasenRepository phRep;
    PlaylistSongsRepository psr;

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
        PlaylistSongsRepository.setContext(getActivity());
        ueRep = UebungenRepository.getInstance();
        phRep = PhasenRepository.getInstance();
        psr = PlaylistSongsRepository.getInstance();

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
        btnEndTraining = (Button) getActivity().findViewById(R.id.btnEndTraining);
        scrollView = (ScrollView) getActivity().findViewById(R.id.scrollViewInstructions);

        aktUebungNr = 1;
        maxUebungNr = getArguments().getInt("uebungAnzahl");

        btnHideClocks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeClocksVisible();
            }
        });

        btnHideMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMusicVisible();
                scrollView.post(scrDown); //Das Scrolldown muss so aufgerufen werden um sicherzustellen dass es nach dem invalidate() der Views aufgerufen wird
            }
        });

        btnNextUeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktUebungNr++;
                if (!btnLastUeb.isEnabled()) {
                    btnLastUeb.setEnabled(true);
                    btnLastUeb.setAlpha(1f);
                }
                if (aktUebungNr == maxUebungNr) {
                    btnNextUeb.setEnabled(false);
                    btnNextUeb.setAlpha(0.25f);

                    btnEndTraining.setVisibility(View.VISIBLE);
                }
                updateUebungsfields(aktUebungNr);
                scrollView.post(scrDown);
            }
        });

        btnLastUeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aktUebungNr--;
                if (!btnNextUeb.isEnabled()) {
                    btnNextUeb.setEnabled(true);
                    btnNextUeb.setAlpha(1f);
                }
                if (aktUebungNr == 1) {
                    btnLastUeb.setEnabled(false);
                    btnLastUeb.setAlpha(0.25f);
                }
                if (btnEndTraining.getVisibility() == View.VISIBLE) {
                    btnEndTraining.setVisibility(View.GONE);
                }
                updateUebungsfields(aktUebungNr);
                scrollView.post(scrDown);
            }
        });

        btnEndTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFeedback();
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
        updateUebungsfields(aktUebungNr);
        updatePhaseAndGewicht();
    }

    private void changeClocksVisible() {
        if (getActivity().findViewById(R.id.clocksLayout).getVisibility() != View.GONE) {
            getActivity().findViewById(R.id.clocksLayout).setVisibility(View.GONE);
            getActivity().findViewById(R.id.fraTsUebung).invalidate();
        } else {
            getActivity().findViewById(R.id.clocksLayout).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.fraTsUebung).invalidate();
        }
        scrollView.post(scrDown); //Das Scrolldown muss so aufgerufen werden um sicherzustellen dass es nach dem invalidate() der Views aufgerufen wird
    }

    private void changeMusicVisible() {
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

    public void updateUebungsfields(int ueId) {
        try {
            setAktUebung(ueRep.getUebung(Long.valueOf(getArguments().getString("uebung" + ueId))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvUebungName.setText(getAktUebung().getString(1));
        tvUebungBeschreibung.setText(getAktUebung().getString(2));
        tvUebungAnleitung.setText(getAktUebung().getString(3));
        tvUebungMuskelgruppe.setText(getAktUebung().getString(4));
        tvUebungSchwierigkeit.setText(getAktUebung().getString(5));
        tvUebungGewicht.setText(getArguments().getDouble("gewicht" + ueId) + " kg");
        setVideoLink(getAktUebung().getString(6));
        tvUebungEquipment.setText(getAktUebung().getString(7));
    }

    public void updatePhaseAndGewicht() {
        try {
            setAktPhase(phRep.getPhase(Long.valueOf(getArguments().getString("phase"))));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tvUebungSaetze.setText(getAktPhase().getString(6));
        tvUebungWiederholungen.setText(getAktPhase().getString(7));
    }

    public void showFeedback(){
        FFeedback fFeedback = new FFeedback();
        fFeedback.setArguments(getArguments());
        displayFragment.displayTSFeedback(fFeedback, getFragmentManager());
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
