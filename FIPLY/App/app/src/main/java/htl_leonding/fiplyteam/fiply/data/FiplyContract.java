package htl_leonding.fiplyteam.fiply.data;

import android.provider.BaseColumns;

public class FiplyContract {

    /**
     * Struktur der Datenbanktabelle "Uebungen"
     */
    public static final class UebungenEntry implements BaseColumns {

        public static final String TABLE_NAME = "uebungen";

        public static final String COLUMN_ROWID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_MUSKELGRUPPE = "muskelgruppe";
        public static final String COLUMN_BESCHREIBUNG = "beschreibung";
        public static final String COLUMN_ANLEITUNG = "anleitung";
        public static final String COLUMN_ZIELGRUPPE = "zielgruppe";
        public static final String COLUMN_VIDEO = "video";
    }

    public static final class KeyValueEntry implements BaseColumns {
        public static final String TABLE_NAME = "keyvalue";

        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_KEY = "key";

    }
}
