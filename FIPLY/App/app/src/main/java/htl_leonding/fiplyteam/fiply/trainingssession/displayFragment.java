package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import htl_leonding.fiplyteam.fiply.R;

public class displayFragment {
    public static void displayTSUebung(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsUebung, display);
        fragmentTransaction.commit();
    }

    public static void displayTSClock(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsClocks, display);
        fragmentTransaction.commit();
    }

    public static void displayTSMusic(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsMusic, display, "TSMusic");
        fragmentTransaction.commit();
    }

    public static void displayTSClocksNav(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsClocksNav, display);
        fragmentTransaction.commit();
    }

    public static void displayFullscreenVideo(Fragment display, FragmentManager fManager, String videoLink) {
        Bundle args = new Bundle();
        args.putString("videoLink", videoLink);
        display.setArguments(args);
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }
}
