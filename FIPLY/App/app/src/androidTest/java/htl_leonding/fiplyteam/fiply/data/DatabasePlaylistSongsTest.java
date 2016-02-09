package htl_leonding.fiplyteam.fiply.data;

import android.database.Cursor;
import android.test.AndroidTestCase;

import java.sql.SQLException;

public class DatabasePlaylistSongsTest extends AndroidTestCase{
    PlaylistSongsRepository rep;

    @Override
    protected void setUp() throws Exception {
        PlaylistSongsRepository.setContext(mContext);
        rep = PlaylistSongsRepository.getInstance();
        rep.reCreatePlaylistSongsTable();
    }

    @Override
        protected void tearDown() throws Exception {
    }


}
