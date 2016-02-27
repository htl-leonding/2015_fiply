package htl_leonding.fiplyteam.fiply.menu;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import htl_leonding.fiplyteam.fiply.R;

public class FAppSettings extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }
}
