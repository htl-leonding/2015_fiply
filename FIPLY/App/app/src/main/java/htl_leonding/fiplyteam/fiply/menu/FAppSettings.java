package htl_leonding.fiplyteam.fiply.menu;

import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.PreferenceFragmentCompat;

import htl_leonding.fiplyteam.fiply.R;

public class FAppSettings extends PreferenceFragmentCompat {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onPause() {
        super.onPause();
        getFragmentManager().beginTransaction().hide(FAppSettings.this).commit();
        getFragmentManager().beginTransaction().replace(R.id.fraPlace, new FMain()).commit();
    }
}
