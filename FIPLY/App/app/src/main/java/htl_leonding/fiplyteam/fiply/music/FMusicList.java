package htl_leonding.fiplyteam.fiply.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;

public class FMusicList extends Fragment {

    ArrayList<HashMap<String, String>> songs;
    ListView listView;
    String aktPlaylist;
    Spinner spinner;
    PlaylistSongsRepository psrep;

    /**
     * Hier wird das music_list fragment angezeigt
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.music_list, container, false);
    }

    /**
     * Hier werden alle Viewelemente gesetzt
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PlaylistSongsRepository.setContext(getActivity());
        psrep = PlaylistSongsRepository.getInstance();

        listView = (ListView) getActivity().findViewById(R.id.listViewMusicList);
        spinner = (Spinner) getActivity().findViewById(R.id.spinnerPlaylist);

        changeAdapter("All");

        spinner.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, psrep.getPlaylists()));
        spinner.setSelection(psrep.getPlaylists().indexOf("All"));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /**
             * Ein Klick auf ein Element des Spinners ändert den Adapter der ListView (wechselt die Liste aus)
             * @param parent
             * @param view
             * @param position
             * @param id
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeAdapter((String) spinner.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FMusic fm = (FMusic) getFragmentManager().findFragmentByTag("TSMusic");
                fm.changeSong(position, aktPlaylist);
                fm.play();
            }
        });
    }

    /**
     * Ändert den Afapter der ListView
     * @param playlist
     */
    private void changeAdapter(String playlist) {
        listView.setAdapter(new SimpleAdapter(getActivity(), psrep.getByPlaylistName(playlist), R.layout.music_item,
                new String[]{"songTitle"}, new int[]{R.id.songTitle}));
        aktPlaylist = playlist;
    }

    public ArrayList<HashMap<String, String>> getSongs() {
        return songs;
    }
}
