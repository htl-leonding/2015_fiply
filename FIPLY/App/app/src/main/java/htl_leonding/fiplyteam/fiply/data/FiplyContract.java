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
        public static final String COLUMN_SCHWIERIGKEIT = "schwierigkeit";
        public static final String COLUMN_VIDEO = "video";
        public static final String COLUMN_EQUIPMENT = "equipment";
    }

    public static final class KeyValueEntry implements BaseColumns {
        public static final String TABLE_NAME = "keyvalue";
        public static final String COLUMN_VALUE = "value";
        public static final String COLUMN_KEY = "key";

    }

    public static final class PhasenEntry implements  BaseColumns {
        public static final String TABLE_NAME = "phasen";
        public static final String COLUMN_ROWID = "_id";

        public static final String COLUMN_PHASENNAME = "phasenname";
        public static final String COLUMN_PHASENDAUER = "phasendauer";
        public static final String COLUMN_PAUSENDAUER = "pausendauer";
        public static final String COLUMN_SAETZE = "saetze";
        public static final String COLUMN_WIEDERHOLUNGEN = "wiederholungen";
    }

    public static final class InstruktionenEntry implements BaseColumns {

        public static final String TABLE_NAME = "instruktionen";

        public static final String COLUMN_ROWID = "_id";
        public static final String COLUMN_WOCHENTAG = "wochentag";
        public static final String COLUMN_REPMAX = "repmax";

        public static final String COLUMN_UEBUNGSID = "uebungsid";
        public static final String COLUMN_PHASENID = "phasenid";

    }
}
