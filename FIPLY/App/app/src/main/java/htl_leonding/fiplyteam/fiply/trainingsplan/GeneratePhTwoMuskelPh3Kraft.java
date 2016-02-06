package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.database.Cursor;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class GeneratePhTwoMuskelPh3Kraft {

    private Date startDate;
    private String[] wochentage;
    private Trainingsphase tphas;
    private int uebungsanzahl;
    private String ziel;
    private int phasendauer;
    private int saetze;
    private int repmax;
    private int pausendauer;
    private int wiederholungen;
    private List<Uebung> uebungen;
    private UebungenRepository rep;
    private String[] muskelgruppen;

    public GeneratePhTwoMuskelPh3Kraft(String[] wochentage, Date startDate, String ziel, String[] muskelgruppen) {
        this.setStartDate(startDate);
        this.setWochentage(wochentage);
        this.setUebungsanzahl(muskelgruppen.length * 3);
        this.setZiel(ziel);
        if (getZiel().equals("Muskelaufbau")) {
            this.phasendauer = 8;
        } else {
            this.phasendauer = 4;
        }
        this.setSaetze(3);
        this.setWiederholungen(12);
        this.setRepmax(80);
        this.setPausendauer(120);
        this.setMuskelgruppen(muskelgruppen);
        rep = UebungenRepository.getInstance();
        setUebungen(new LinkedList<Uebung>());
        List<String[]> list = setUebungen();
        fetchIntoUebungen(list);
        tphas = new Trainingsphase("Phase 2: Muskelaufbau; Phase 3: Kraftausdauer", PhasenTyp.PHASE2MUSKELAUFBAUPHASE3KRAFTAUSDAUER, getPausendauer(), getPhasendauer(), getSaetze(), getWiederholungen(), getRepmax(), getStartDate());
        tphas.setUebungList(uebungen);
    }

    private List<String[]> setUebungen() {
        Cursor c = rep.getAllUebungen();
        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;
        List<String[]> uebungsList = new LinkedList<String[]>();

        int count1 = 0;
        int count2 = 0;
        int count3 = 0;

        String[] mgruppe = null;
        String mgrup;
        String lolgrup;
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            muskelGruppe = c.getString(iMuskelGruppe);
            mgrup = getMuskelgruppen()[0];
            if (getUebungsanzahl() == 6) {
                if (muskelGruppe.toUpperCase().contains(getMuskelgruppen()[0].toUpperCase()) && count1 < 3) {
                    rowId = c.getString(iRowId);
                    uebungsName = c.getString(iUebungsName);
                    String[] newUebung = {rowId, uebungsName, wochentage[count1], muskelGruppe};
                    uebungsList.add(newUebung);
                    count1++;
                } else if (muskelGruppe.toUpperCase().contains(getMuskelgruppen()[1].toUpperCase()) && count2 < 3) {
                    rowId = c.getString(iRowId);
                    uebungsName = c.getString(iUebungsName);
                    String[] newUebung = {rowId, uebungsName, wochentage[count2], muskelGruppe};
                    uebungsList.add(newUebung);
                    count2++;
                }
            } else {
                lolgrup = muskelGruppe.toUpperCase();
                if (lolgrup.contains(mgrup.toUpperCase()) && count1 < 3) {
                    rowId = c.getString(iRowId);
                    uebungsName = c.getString(iUebungsName);
                    String[] newUebung = {rowId, uebungsName, wochentage[count1], muskelGruppe};
                    uebungsList.add(newUebung);
                    count1++;
                } else if (muskelGruppe.toUpperCase().contains(getMuskelgruppen()[1].toUpperCase()) && count2 < 3) {
                    rowId = c.getString(iRowId);
                    uebungsName = c.getString(iUebungsName);
                    String[] newUebung = {rowId, uebungsName, wochentage[count2], muskelGruppe};
                    uebungsList.add(newUebung);
                    count2++;
                } else if (muskelGruppe.toUpperCase().contains(getMuskelgruppen()[2].toUpperCase()) && count3 < 3) {
                    rowId = c.getString(iRowId);
                    uebungsName = c.getString(iUebungsName);
                    String[] newUebung = {rowId, uebungsName, wochentage[count3], muskelGruppe};
                    uebungsList.add(newUebung);
                    count3++;
                }
            }
        }
        return uebungsList;
    }

    private void fetchIntoUebungen(List<String[]> uebungsListe) {
        Uebung ueb;
        for (String[] element : uebungsListe) {
            ueb = new Uebung();
            ueb.setUebungsID(element[0]);
            ueb.setUebungsName(element[1]);
            ueb.setRepmax(80);
            ueb.setWochenTag(element[2]);
            ueb.setMuskelgruppe(element[3]);
            uebungen.add(ueb);
        }
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String[] getWochentage() {
        return wochentage;
    }

    public void setWochentage(String[] wochentage) {
        this.wochentage = wochentage;
    }

    public Trainingsphase getTphas() {
        return tphas;
    }

    public void setTphas(Trainingsphase tphas) {
        this.tphas = tphas;
    }

    public int getUebungsanzahl() {
        return uebungsanzahl;
    }

    public void setUebungsanzahl(int uebungsanzahl) {
        this.uebungsanzahl = uebungsanzahl;
    }

    public String getZiel() {
        return ziel;
    }

    public void setZiel(String ziel) {
        this.ziel = ziel;
    }

    public int getPhasendauer() {
        return phasendauer;
    }

    public void setPhasendauer(int phasendauer) {
        this.phasendauer = phasendauer;
    }

    public int getSaetze() {
        return saetze;
    }

    public void setSaetze(int saetze) {
        this.saetze = saetze;
    }

    public int getRepmax() {
        return repmax;
    }

    public void setRepmax(int repmax) {
        this.repmax = repmax;
    }

    public int getPausendauer() {
        return pausendauer;
    }

    public void setPausendauer(int pausendauer) {
        this.pausendauer = pausendauer;
    }

    public int getWiederholungen() {
        return wiederholungen;
    }

    public void setWiederholungen(int wiederholungen) {
        this.wiederholungen = wiederholungen;
    }

    public String[] getMuskelgruppen() {
        return muskelgruppen;
    }

    public void setMuskelgruppen(String[] muskelgruppen) {
        this.muskelgruppen = muskelgruppen;
    }

    public List<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(List<Uebung> uebungen) {
        this.uebungen = uebungen;
    }

    public Trainingsphase getTPhase() {
        return this.tphas;
    }
}
