package htl_leonding.fiplyteam.fiply.menu;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;

public class FAppSettings extends PreferenceFragmentCompat {
    private Preference pref;
    PlaylistSongsRepository psr;

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preferences);
        PlaylistSongsRepository.setContext(getActivity());
        psr = PlaylistSongsRepository.getInstance();
        pref = getPreferenceManager().findPreference("preferencesMusic");
        pref.setSummary(psr.getByPlaylistName("All").size() + " Songs eingelesen");
    }
}
