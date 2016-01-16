package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
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
    Button btAdd, btSave;
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
        btAdd = (Button) getActivity().findViewById(R.id.btPlAdd);
        btSave = (Button) getActivity().findViewById(R.id.btPlSave);
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