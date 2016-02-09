package htl_leonding.fiplyteam.fiply.music;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;

public class FPlaylist extends Fragment {

    ReadMusic rm;
    PlaylistSongsRepository psrep;
    List<String> songStrings;
    ArrayList<HashMap<String, String>> songs;
    ArrayList<HashMap<String, String>> checkedSongs;
    ListView lvSongs;
    ImageButton btAdd, btDelete;
    Button btnSave;
    Switch listSwitch;
    EditText etName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.fragment_playlist, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvSongs = (ListView) getActivity().findViewById(R.id.lvPlSongs);
        btAdd = (ImageButton) getActivity().findViewById(R.id.btPlAdd);
        btDelete = (ImageButton) getActivity().findViewById(R.id.btPlDelete);
        listSwitch = (Switch) getActivity().findViewById(R.id.switchPl);
        etName = (EditText) getActivity().findViewById(R.id.etPlName);
        btnSave = (Button) getActivity().findViewById(R.id.btnPlaylistSave);

        rm = ReadMusic.getInstance();
        PlaylistSongsRepository.setContext(getActivity());
        psrep = PlaylistSongsRepository.getInstance();

        songs = new ArrayList<>(rm.getSongs());
        checkedSongs = new ArrayList<>();
        songStrings = new LinkedList<>();

        for (int i = 0; i < songs.size(); i++) {
            songStrings.add(songs.get(i).get("songTitle"));
        }

//        lvSongs.setAdapter(new SimpleAdapter(getActivity(), songs,
//                android.R.layout.simple_list_item_multiple_choice,
//                new String[]{"songTitle"}, new int[]{R.id.songTitle}));

        lvSongs.setAdapter(new ArrayAdapter<String>(getActivity(),
                R.layout.music_item_checkable, songStrings));

        lvSongs.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                if (lvSongs.isItemChecked(position)) {
//                    lvSongs.setItemChecked(position,false);
//                } else {
//                    lvSongs.setItemChecked(position,true);
//                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checked = lvSongs.getCheckedItemPositions();
                checkedSongs.clear();
                for (int i = 0; i < songs.size(); i++) {
                    if (checked.get(i)) {
                        //System.out.println(songs.get(i).get("songPath"));
                        checkedSongs.add(songs.get(i));
                    }
                }

                psrep.insertMany("Swag", checkedSongs);

                ArrayList<HashMap<String, String>> s = new ArrayList<>(psrep.getByPlaylistName("Swag"));

                for (int i = 0; i < s.size(); i++) {
                    if (checked.get(i)) {
                        System.out.println(s.get(i).get("songPath"));
                    }
                }
            }
        });
    }


}