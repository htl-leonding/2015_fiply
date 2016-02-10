package htl_leonding.fiplyteam.fiply.data;

import android.test.AndroidTestCase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DatabasePlaylistSongsTest extends AndroidTestCase {
    PlaylistSongsRepository rep;
    ArrayList<HashMap<String, String>> classicList, rapList, rapListNew;

    @Override
    protected void setUp() throws Exception {
        PlaylistSongsRepository.setContext(mContext);
        rep = PlaylistSongsRepository.getInstance();
        rep.reCreatePlaylistSongsTable();

        String[] songs = new String[]{"Space Oddity", "Thunderstruck", "The Final Countdown", "Down Under", "Hells Bells", "Fortunate Son"};
        String[] paths = new String[]{"PATH/Space Oddity", "PATH/Thunderstruck", "PATH/The Final Countdown", "PATH/Down Under", "PATH/Hells Bells", "PATH/Fortunate Son"};
        classicList = new ArrayList<>();
        HashMap<String, String> item;
        for (int i = 0; i < songs.length; i++) {
            item = new HashMap<>();
            item.put("songTitle", songs[i]);
            item.put("songPath", paths[i]);
            classicList.add(item);
        }

        songs = new String[]{"The Next Episode", "Still D.R.E.", "Lose Yourself", "Rap God", "Straight Outta Compton"};
        paths = new String[]{"PATH/The Next Episode", "PATH/Still D.R.E.", "PATH/Lose Yourself", "PATH/Rap God", "PATH/Straight Outta Compton"};
        rapList = new ArrayList<>();
        for (int i = 0; i < songs.length; i++) {
            item = new HashMap<>();
            item.put("songTitle", songs[i]);
            item.put("songPath", paths[i]);
            rapList.add(item);
        }

        songs = new String[]{"The Next Episode", "Still D.R.E.", "Lose Yourself", "Rap God"};
        paths = new String[]{"PATH/The Next Episode", "PATH/Still D.R.E.", "PATH/Lose Yourself", "PATH/Rap God"};
        rapListNew = new ArrayList<>();
        for (int i = 0; i < songs.length; i++) {
            item = new HashMap<>();
            item.put("songTitle", songs[i]);
            item.put("songPath", paths[i]);
            rapListNew.add(item);
        }
    }

    @Override
    protected void tearDown() throws Exception {
    }

    public void testGetSongsByPlaylist() throws SQLException {
        rep.reenterPlaylist("Classic", classicList);
        assertEquals(6, classicList.size());
        assertEquals(6, rep.getByPlaylistName("Classic").size());
    }

    public void testDeleteSongsByPlaylist() throws SQLException {
        rep.reenterPlaylist("Classic", classicList);
        rep.reenterPlaylist("Rap", rapList);
        assertEquals(6, rep.getByPlaylistName("Classic").size());
        assertEquals(5, rep.getByPlaylistName("Rap").size());
        assertEquals(0, rep.getByPlaylistName("Pop").size());

        rep.deleteByPlaylistName("Classic");
        assertEquals(0, rep.getByPlaylistName("Classic").size());
        assertEquals(5, rep.getByPlaylistName("Rap").size());

        rep.reenterPlaylist("Classic", classicList);
        assertEquals(6, rep.getByPlaylistName("Classic").size());

        rep.deleteAll();
        assertEquals(0, rep.getByPlaylistName("Classic").size());
        assertEquals(0, rep.getByPlaylistName("Rap").size());
    }

    public void testReenterPlaylist() throws SQLException {
        rep.reenterPlaylist("Classic", classicList);
        rep.reenterPlaylist("Rap", rapList);
        assertEquals(6, rep.getByPlaylistName("Classic").size());
        assertEquals(5, rep.getByPlaylistName("Rap").size());

        rep.reenterPlaylist("Classic", rapList);
        assertEquals(5, rep.getByPlaylistName("Classic").size());
        assertEquals(5, rep.getByPlaylistName("Rap").size());
    }

    public void testGetUniquePlaylistNames() {
        rep.reenterPlaylist("Classic", classicList);
        rep.reenterPlaylist("Rap", rapList);
        assertEquals(2, rep.getPlaylists().size());
        assertEquals("Classic", rep.getPlaylists().get(0));

        rep.deleteByPlaylistName("Classic");
        assertEquals(1, rep.getPlaylists().size());
        assertEquals("Rap", rep.getPlaylists().get(0));

        rep.reenterPlaylist("Classic", classicList);
        assertEquals(2, rep.getPlaylists().size());
        assertEquals("Classic", rep.getPlaylists().get(0));
    }

    public void testSortSongs() {
        rep.reenterPlaylist("Classic", classicList);
        assertEquals(classicList.get(0).get("songTitle"), "Space Oddity");
        assertEquals(rep.getByPlaylistName("Classic").get(0).get("songTitle"), "Down Under");
    }

    public void testDeleteBySongPath() {
        rep.reenterPlaylist("Classic", classicList);
        rep.reenterPlaylist("Rap", rapList);
        rep.reenterPlaylist("Rap2", rapListNew);
        assertEquals(6, rep.getByPlaylistName("Classic").size());
        assertEquals(5, rep.getByPlaylistName("Rap").size());
        assertEquals(4, rep.getByPlaylistName("Rap2").size());

        for (HashMap<String, String> itemAlt : rapList) {
            if (!rapListNew.contains(itemAlt)) {
                rep.deleteBySongPath(itemAlt.get("songPath"));
            }
        }
        assertEquals(6, rep.getByPlaylistName("Classic").size());
        assertEquals(4, rep.getByPlaylistName("Rap").size());
        assertEquals(4, rep.getByPlaylistName("Rap2").size());
    }
}
