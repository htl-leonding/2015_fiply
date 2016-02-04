package htl_leonding.fiplyteam.fiply.Trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import htl_leonding.fiplyteam.fiply.Music.FMusic;
import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.displayFragment;
import htl_leonding.fiplyteam.fiply.fragments.FClocksNav;
import htl_leonding.fiplyteam.fiply.fragments.FUebungDetail;
import htl_leonding.fiplyteam.fiply.fragments.FWatch;

public class FTrainingssession extends Fragment {

    /**
     * Lädt das fragment_trainingssession in das FrameLayout der MainActivity
     *
     * @param inflater           default
     * @param container          default
     * @param savedInstanceState default
     * @return View die ins FrameLayout geladen wird
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FUebungDetail fragmentUebung = new FUebungDetail();
        Bundle args = new Bundle();
        args.putString("name", "TestName");
        args.putString("beschreibung", "TestBeschreibung");
        args.putString("anleitung", "TestAnleitung");
        args.putString("muskelgruppe", "TestMuskelgruppe");
        args.putString("zielgruppe", "TestZielgruppe");
        args.putString("video", "https://www.youtube.com/embed/ykJmrZ5v0Oo");
        args.putBoolean("showVideo", false);
        fragmentUebung.setArguments(args);

        displayFragment.displayTSUebung(fragmentUebung, getFragmentManager());
        displayFragment.displayTSClocksNav(new FClocksNav(), getFragmentManager());
        displayFragment.displayTSClock(new FWatch(), getFragmentManager());
        displayFragment.displayTSMusic(new FMusic(), getFragmentManager());

        return inflater.inflate(R.layout.fragment_trainingssession, container, false);
    }
}
