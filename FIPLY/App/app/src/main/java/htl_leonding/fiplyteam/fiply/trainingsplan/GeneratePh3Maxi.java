package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.database.Cursor;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class GeneratePh3Maxi {

    private Date startDate;
    private String[] wochentage;
    private Trainingsphase tPhas;
    private int phasenDauer;
    private int saetze;
    private int wiederholungen;
    private int pausenDauer;
    private int repMax;
    private List<Uebung> uebungen;
    private UebungenRepository rep;

    public GeneratePh3Maxi(Date startDate, String[] wochentage) {
        this.setStartDate(startDate);
        this.setWochentage(wochentage);
        this.setPausenDauer(180);
        this.setPhasenDauer(8);
        this.setSaetze(5);
        this.setWiederholungen(2);
        this.setRepMax(95);
        tPhas = new Trainingsphase("Phase 3: Maximalkraft", getPausenDauer(), getPhasenDauer(), getSaetze(), getWiederholungen(), getRepMax(), getStartDate());
        fetchUebungen();
        tPhas.setUebungList(uebungen);
    }

    private void fetchUebungen() {
        rep = UebungenRepository.getInstance();
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        int iEquip = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_EQUIPMENT);

        String rowId;
        String muskelgruppe;
        String uebungsName;
        String equip;

        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            equip = c.getString(iEquip);
            if (equip.contains("seilzug") || equip.contains("hantel")) {
                rowId = c.getString(iRowId);
                muskelgruppe = c.getString(iMuskelGruppe);
                uebungsName = c.getString(iUebungsName);
                String[] ueb = {rowId, uebungsName, muskelgruppe};
                uebungsList.add(ueb);
            }
        }

        boolean arme = false, ruecken = false, schulter = false, beine = false, brust = false, arme2 = false;
        Uebung ueb;
        Collections.shuffle(uebungsList);
        List<Uebung> uebs = new LinkedList<Uebung>();
        for (String[] element : uebungsList) {
            if (element[2].toUpperCase().contains("ARME") && !arme) {
                arme = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[0]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("RÜCKEN") && !ruecken) {
                ruecken = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[0]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("SCHULTER") && !schulter) {
                schulter = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[0]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("BEINE") && !beine) {
                beine = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[1]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("BRUST") && !brust) {
                brust = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[1]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("ARME") && !arme2) {
                arme2 = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[1]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            }
        }
        uebungen = new LinkedList<Uebung>();
        uebungen.addAll(uebs);
    }


    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setWochentage(String[] wochentage) {
        this.wochentage = wochentage;
    }

    public Trainingsphase getTPhase() {
        return tPhas;
    }

    public void setTPhase(Trainingsphase tPhas) {
        this.tPhas = tPhas;
    }

    public int getPhasenDauer() {
        return phasenDauer;
    }

    public void setPhasenDauer(int phasenDauer) {
        this.phasenDauer = phasenDauer;
    }

    public int getSaetze() {
        return saetze;
    }

    public void setSaetze(int saetze) {
        this.saetze = saetze;
    }

    public int getWiederholungen() {
        return wiederholungen;
    }

    public void setWiederholungen(int wiederholungen) {
        this.wiederholungen = wiederholungen;
    }

    public int getPausenDauer() {
        return pausenDauer;
    }

    public void setPausenDauer(int pausenDauer) {
        this.pausenDauer = pausenDauer;
    }

    public List<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(List<Uebung> uebungen) {
        this.uebungen = uebungen;
    }

    public int getRepMax() {
        return repMax;
    }

    public void setRepMax(int repMax) {
        this.repMax = repMax;
    }
}
