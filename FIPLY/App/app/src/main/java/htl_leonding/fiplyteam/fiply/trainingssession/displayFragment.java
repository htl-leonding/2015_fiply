package htl_leonding.fiplyteam.fiply.trainingssession;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import htl_leonding.fiplyteam.fiply.R;

public class displayFragment {

    public static void displayTSInstruktion(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsUebung, display, "TSInstruktion");
        fragmentTransaction.commit();
    }

    public static void displayTSInstruktionAgain(FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.remove(fManager.findFragmentByTag("TSMusicList"));
        fragmentTransaction.show(fManager.findFragmentByTag("TSInstruktion"));
        fragmentTransaction.commit();
    }

    public static void displayTSMusicList(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.hide(fManager.findFragmentByTag("TSInstruktion"));
        fragmentTransaction.add(R.id.fraTsUebung, display, "TSMusicList");
        fragmentTransaction.commit();
    }

    public static void displayTSClock(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsClocks, display, "TSClocks");
        fragmentTransaction.commit();
    }

    public static void displayTSMusic(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsMusic, display, "TSMusic");
        fragmentTransaction.commit();
    }

    public static void displayTSClocksNav(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsClocksNav, display, "TSClocksNav");
        fragmentTransaction.commit();
    }

    public static void displayTSFeedback(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }

    public static void displayMainMenu(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }
}
