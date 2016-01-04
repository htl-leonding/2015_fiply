package htl_leonding.fiplyteam.fiply.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import htl_leonding.fiplyteam.fiply.R;
import htl_leonding.fiplyteam.fiply.ReadMusic;

public class FMusicList extends ListFragment {

    ArrayList<HashMap<String, String>> songs;
    //ReadMusic rm = ReadMusic.getInstance();
    ListView listView;
    ReadMusic rm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        return inflater.inflate(R.layout.music_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rm = ReadMusic.getInstance();
        //songs = new ArrayList<>();
        //this.songs = rm.getSongs();

        ListAdapter adapter = new SimpleAdapter(getActivity(), rm.getSongs(), R.layout.music_item,
                new String[]{"songTitle"}, new int[]{R.id.songTitle});
        setListAdapter(adapter);

        listView = getListView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FMusic fm = (FMusic) getFragmentManager().findFragmentByTag("TSMusic");
                fm.changeSong(position);
                //fm.changeSong(songs.get(position).get("songTitle"));
                //Toast.makeText(getActivity(), getSongsData().get(position).get("songTitle"), Toast.LENGTH_LONG).show();
                fm.play();
            }
        });
    }

    public ArrayList<HashMap<String, String>> getSongs() {
        return songs;
    }
}
