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

    public static void displayTSWatch(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsWatch, display);
        fragmentTransaction.commit();
    }

    public static void displayTSMusic(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsMusic, display, "Music");
        fragmentTransaction.commit();
    }

}
