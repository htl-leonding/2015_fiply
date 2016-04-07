package htl_leonding.fiplyteam.fiply.trainingssession;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import htl_leonding.fiplyteam.fiply.R;

/**
 * Diese Klasse beinhaltet mehrer Methoden die alle dazu gedacht sind das ANzeigen von Fragments übersichtlich zu machen
 */
public class displayFragment {

    /**
     * Erstellt das Trainingsinstructions Fragment
     * @param display
     * @param fManager
     */
    public static void displayTSInstruktion(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraTsUebung, display, "TSInstruktion");
        fragmentTransaction.commit();
    }

    /**
     * Zeigt das Trainingsinstructions Fragment anstatt des MusicList Fragment an
     * @param fManager
     */
    public static void displayTSInstruktionAgain(FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.remove(fManager.findFragmentByTag("TSMusicList"));
        fragmentTransaction.show(fManager.findFragmentByTag("TSInstruktion"));
        fragmentTransaction.commit();
    }

    /**
     * Zeigt das MusicList Fragment anstatt des Trainingsinstructions Fragment an
     * @param display
     * @param fManager
     */
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
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }

    public static void displayMainMenu(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }

    public static void displayTrainingsession(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }

    @Deprecated
    public static void displayDialogChooseDay(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }

    @Deprecated
    public static void displayGeneratePlan(Fragment display, FragmentManager fManager) {
        FragmentTransaction fragmentTransaction = fManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fraPlace, display);
        fragmentTransaction.commit();
    }
}
