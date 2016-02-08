package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.database.Cursor;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

import static htl_leonding.fiplyteam.fiply.data.FiplyContract.*;

public class GeneratePhTwoMaxiPh3Muskel {
    private Date startDate;
    private String[] wochentage;
    private int phasenDauer;
    private int saetze;
    private int wiederholungen;
    private int pausendauer;
    private List<Uebung> uebungen;
    private Trainingsphase tPhas;
    private UebungenRepository rep;


    public GeneratePhTwoMaxiPh3Muskel(Date startDate, String ziel, String[] wochentage){
        this.setStartDate(startDate);
        this.setWochentage(wochentage);

        if (ziel == "Muskelaufbau"){
            phasenDauer = 4;
        }else
            phasenDauer = 6;

        fetchUebungen();

        tPhas = new Trainingsphase("Phase 2: Maximalkraft; Phase 3: Muskelaufbau", 120, phasenDauer, 3, 5, 95, startDate);
        tPhas.setUebungList(uebungen);

    }

    private void fetchUebungen() {
        rep = UebungenRepository.getInstance();
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(UebungenEntry.COLUMN_NAME);
        int iEquip = c.getColumnIndex(UebungenEntry.COLUMN_EQUIPMENT);

        String rowId;
        String muskelgruppe;
        String uebungsName;
        String equip;

        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            equip = c.getString(iEquip);
            if (equip.contains("seilzug") || equip.contains("hantel")){
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
        for (String[] element : uebungsList){
            if (element[2].toUpperCase().contains("ARME") && !arme){
                arme = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[0]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("RÜCKEN") && !ruecken){
                ruecken = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[0]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("SCHULTER") && !schulter){
                schulter = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[1]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("BEINE") && !beine){
                beine = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[1]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("BRUST") && !brust){
                brust = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[2]);
                ueb.setUebungsName(element[1]);
                ueb.setMuskelgruppe(element[2]);
                uebs.add(ueb);
            } else if (element[2].toUpperCase().contains("ARME") && !arme2){
                arme2 = true;
                ueb = new Uebung();
                ueb.setRepmax(95);
                ueb.setUebungsID(element[0]);
                ueb.setWochenTag(wochentage[2]);
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

    public String[] getWochentage() {
        return wochentage;
    }

    public void setWochentage(String[] wochentage) {
        this.wochentage = wochentage;
    }

    public int getPhasenDauer() {
        return phasenDauer;
    }

    public void setPhasenDauer(int phasendauer) {
        this.phasenDauer = phasendauer;
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

    public int getPausendauer() {
        return pausendauer;
    }

    public void setPausendauer(int pausendauer) {
        this.pausendauer = pausendauer;
    }

    public List<Uebung> getUebungen() {
        return uebungen;
    }

    public void setUebungen(List<Uebung> uebungen) {
        this.uebungen = uebungen;
    }

    public Trainingsphase getTPhase() {
        return tPhas;
    }

    public void setTPhase(Trainingsphase tPhas) {
        this.tPhas = tPhas;
    }
}
