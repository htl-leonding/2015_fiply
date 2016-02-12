package htl_leonding.fiplyteam.fiply.music;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.data.PlaylistSongsRepository;

public class FPlaylist extends Fragment {

    PlaylistSongsRepository psrep;
    List<String> songTitleStrings, songPathStrings, selectedSongPathStrings;
    ArrayList<HashMap<String, String>> songs, checkedSongs;
    Boolean listPlaylistMode;
    String aktListName = "";

    ListView lvSongs;
    LinearLayout linearLayout;
    Button btnSave, btnSelectAll, btnSelectNone, btBack;
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
        btBack = (Button) getActivity().findViewById(R.id.btPlBack);
        etName = (EditText) getActivity().findViewById(R.id.etPlName);
        btnSave = (Button) getActivity().findViewById(R.id.btnPlaylistSave);
        btnSelectAll = (Button) getActivity().findViewById(R.id.btnPlaylistSelectAll);
        btnSelectNone = (Button) getActivity().findViewById(R.id.btnPlaylistSelectNone);
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.linearLayoutPlaylist);

        PlaylistSongsRepository.setContext(getActivity());
        psrep = PlaylistSongsRepository.getInstance();

        setUpPlaylistAdapter();
        refreshSongStringLists();

        lvSongs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listPlaylistMode) {
                    listPlaylistMode = false;
                    refreshSongStringLists();
                    linearLayout.setVisibility(View.VISIBLE);
                    etName.setVisibility(View.VISIBLE);
                    aktListName = psrep.getPlaylists().get(position);
                    etName.setText(aktListName);

                    lvSongs.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.music_item_checkable, songTitleStrings));
                    lvSongs.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

                    ArrayList<HashMap<String, String>> selectedList = new ArrayList<>(psrep.getByPlaylistName(etName.getText().toString()));
                    for (HashMap<String, String> item : selectedList) {
                        selectedSongPathStrings.add(item.get("songPath"));
                    }
                    for (int i = 0; i < songPathStrings.size(); i++) {
                        if (selectedSongPathStrings.contains(songPathStrings.get(i))) {
                            lvSongs.setItemChecked(i, true);
                        } else {
                            lvSongs.setItemChecked(i, false);
                        }
                    }
                }
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etName.getText().toString().equals("All")) {
                    SparseBooleanArray checked = lvSongs.getCheckedItemPositions();
                    checkedSongs.clear();
                    refreshSongStringLists();

                    for (int i = 0; i < songs.size(); i++) {
                        if (checked.get(i)) {
                            checkedSongs.add(songs.get(i));
                        }
                    }

                    if (checkedSongs.isEmpty()) {
                        Toast.makeText(getActivity(), "Playlist " + etName.getText().toString() + " wurde gelöscht!", Toast.LENGTH_SHORT).show();
                    } else if (psrep.getPlaylists().contains(etName.getText().toString())) {
                        Toast.makeText(getActivity(), "Playlist " + etName.getText().toString() + " wurde gespeichert!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "Playlist " + etName.getText().toString() + " wurde erstellt!", Toast.LENGTH_SHORT).show();
                    }
                    psrep.reenterPlaylist(etName.getText().toString(), checkedSongs);
                }
                hideKeyboard();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                setUpPlaylistAdapter();
            }
        });

        btnSelectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSongStringLists();
                for (int i = 0; i < lvSongs.getCount(); i++) {
                    lvSongs.setItemChecked(i, true);
                }
            }
        });

        btnSelectNone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshSongStringLists();
                for (int i = 0; i < lvSongs.getCount(); i++) {
                    lvSongs.setItemChecked(i, false);
                }
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imms = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imms.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    public void refreshSongStringLists() {
        selectedSongPathStrings.clear();
        songTitleStrings.clear();
        songPathStrings.clear();
        for (int i = 0; i < songs.size(); i++) {
            songTitleStrings.add(songs.get(i).get("songTitle"));
            songPathStrings.add(songs.get(i).get("songPath"));
        }
    }

    public void setUpPlaylistAdapter() {
        songs = new ArrayList<>(psrep.getByPlaylistName("All"));
        checkedSongs = new ArrayList<>();
        songTitleStrings = new LinkedList<>();
        songPathStrings = new LinkedList<>();
        selectedSongPathStrings = new LinkedList<>();

        lvSongs.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.playlist_item, psrep.getPlaylists()));
        lvSongs.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listPlaylistMode = true;
        etName.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);
    }
}