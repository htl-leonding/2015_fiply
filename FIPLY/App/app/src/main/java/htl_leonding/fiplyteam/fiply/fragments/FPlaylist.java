package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.ReadMusic;

public class FPlaylist extends Fragment {

    ReadMusic rm;
    ArrayList<HashMap<String, String>> songs;

    ListView lvSongs;
    ImageButton btAdd, btDelete;
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

        rm = ReadMusic.getInstance();

        lvSongs.setAdapter(new SimpleAdapter(getActivity(), rm.getSongs(),
                R.layout.music_item_checkable,
                new String[]{"songTitle"}, new int[]{R.id.songTitle}));

        lvSongs.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });

    }
}