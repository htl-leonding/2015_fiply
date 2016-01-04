package htl_leonding.fiplyteam.fiply;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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
}
