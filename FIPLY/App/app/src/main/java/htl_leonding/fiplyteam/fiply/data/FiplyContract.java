package htl_leonding.fiplyteam.fiply.data;

import android.provider.BaseColumns;

/**
 * Created by Andreas on 26.10.2015.
 */
public class FiplyContract {

    public static final class UebungsEntry implements BaseColumns {

        public static final String TABLE_NAME = "uebungen";

        public static final String COLUMN_ROWID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MUSKELGRUPPE = "muskelgruppe";
        public static final String COLUMN_BESCHREIBUNG = "beschreibung";
        public static final String COLUMN_ANLEITUNG = "anleitung";
        public static final String COLUMN_TIPP = "tipp";
        public static final String COLUMN_VIDEO = "video";
    }

}
