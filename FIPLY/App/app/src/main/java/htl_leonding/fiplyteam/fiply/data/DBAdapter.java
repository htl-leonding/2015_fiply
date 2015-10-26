package htl_leonding.fiplyteam.fiply.data;

public class DBAdapter {

    //Gibt eine Uebung in die Datenbank ein
    public long insertUebung(String name, String beschreibung, String anleitung,
                            String muskelgruppe, String tipp, String video) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(UebungsEntry.COLUMN_NAME, name);
        initialValues.put(UebungsEntry.COLUMN_BESCHREIBUNG, beschreibung);
        initialValues.put(UebungsEntry.COLUMN_ANLEITUNG, anleitung);
        initialValues.put(UebungsEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        initialValues.put(UebungsEntry.COLUMN_TIPP, tipp);
        initialValues.put(UebungsEntry.COLUMN_VIDEO, video);
        return db.insert(UebungsEntry.DATABASE_TABLE, null, initialValues);
    }

    //Löscht eine Uebung
    public boolean deleteUebung(long rowId) {
        return db.delete(UebungsEntry.DATABASE_TABLE, UebungsEntry.COLUMN_ROWID + "=" + rowId, null) > 0;
    }

    //Löscht alle Uebungen
    public boolean deleteAllUebungen() {
        return db.delete(DATABASE_TABLE, null, null) > 0;
    }

    //Liefert alle Uebungen zurück
    public Cursor getAllUebungen() {
        return db.query(DATABASE_TABLE, new String[]{
                        UebungsEntry.COLUMN_ROWID,
                        UebungsEntry.COLUMN_NAME,
                        UebungsEntry.COLUMN_BESCHREIBUNG,
                        UebungsEntry.COLUMN_ANLEITUNG,
                        UebungsEntry.COLUMN_MUSKELGRUPPE,
                        UebungsEntry.COLUMN_TIPP,
                        UebungsEntry.COLUMN_VIDEO},
                null, null, null, null, UebungsEntry.COLUMN_NAME + " ASC");
    }

    //Liefert eine bestimmte Uebung zurück
    public Cursor getUebung(long rowId) throws SQLException {
        Cursor myCursor = db.query(true, DATABASE_TABLE, new String[]{
                        UebungsEntry.COLUMN_ROWID,
                        UebungsEntry.COLUMN_NAME,
                        UebungsEntry.COLUMN_BESCHREIBUNG,
                        UebungsEntry.COLUMN_ANLEITUNG,
                        UebungsEntry.COLUMN_MUSKELGRUPPE,
                        UebungsEntry.COLUMN_TIPP,
                        UebungsEntry.COLUMN_VIDEO},
                UebungsEntry.COLUMN_ROWID + "=" + rowId,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    //Updated eine bestimmte Uebung
    public boolean updateUebung(long rowId, String name, String beschreibung, String anleitung,
                                String muskelgruppe, String tipp, String video) {
        ContentValues updatedValues = new ContentValues();
        updatedValues.put(UebungsEntry.COLUMN_NAME, name);
        updatedValues.put(UebungsEntry.COLUMN_BESCHREIBUNG, beschreibung);
        updatedValues.put(UebungsEntry.COLUMN_ANLEITUNG, anleitung);
        updatedValues.put(UebungsEntry.COLUMN_MUSKELGRUPPE, muskelgruppe);
        updatedValues.put(UebungsEntry.COLUMN_TIPP, tipp);
        updatedValues.put(UebungsEntry.COLUMN_VIDEO, video);
        return db.update(DATABASE_TABLE, updatedValues, UebungsEntry.COLUMN_ROWID + "=" + rowId, null) > 0;
    }

    //Liefert die Anzahl aller Uebungen zurück
    public int getUebungCount() {
        return (int) DatabaseUtils.queryNumEntries(db, "uebungen");
    }

    //Liefert die Übung mit dem passenden Namen Zurück
    public Cursor getUebungByName(String name) throws SQLException {
        Cursor myCursor = db.query(true, DATABASE_TABLE, new String[]{
                        UebungsEntry.COLUMN_ROWID,
                        UebungsEntry.COLUMN_NAME,
                        UebungsEntry.COLUMN_BESCHREIBUNG,
                        UebungsEntry.COLUMN_ANLEITUNG,
                        UebungsEntry.COLUMN_MUSKELGRUPPE,
                        UebungsEntry.COLUMN_TIPP,
                        UebungsEntry.COLUMN_VIDEO},
                UebungsEntry.COLUMN_NAME + "=" + "'" + name + "'",
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    //Liefert alle Uebungen für eine bestimmte Muskelgruppe zurück
    public Cursor getUebungenByMuskelgruppe(String Muskelgruppe) {
        return db.query(DATABASE_TABLE, new String[]{
                        UebungsEntry.COLUMN_ROWID,
                        UebungsEntry.COLUMN_NAME,
                        UebungsEntry.COLUMN_BESCHREIBUNG,
                        UebungsEntry.COLUMN_ANLEITUNG,
                        UebungsEntry.COLUMN_MUSKELGRUPPE,
                        UebungsEntry.COLUMN_TIPP,
                        UebungsEntry.COLUMN_VIDEO},
                UebungsEntry.COLUMN_MUSKELGRUPPE + "=" + "'" + Muskelgruppe + "'",
                null, null, null, UebungsEntry.COLUMN_NAME + " ASC", null);
    }
}
