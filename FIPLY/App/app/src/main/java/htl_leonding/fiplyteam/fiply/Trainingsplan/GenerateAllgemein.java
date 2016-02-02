package htl_leonding.fiplyteam.fiply.Trainingsplan;

import android.database.Cursor;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

/**
 * Created by daniel on 30.01.16.
 */
public class GenerateAllgemein {
    boolean trainiert;
    int schema; // 1 = Bauch-Beine-Po; 2 = Oberkörper-Arme; 3 = Stabilisation(Gesundheit-Rücken)
    String[] wochentage;
    private Trainingsphase tPhase;
    private int uebungenAnzahl;
    UebungenRepository rep;
    int repmax = 55;
    int wiederholungen;

    public GenerateAllgemein(boolean trainiert, int schema, String[] wochentage, Date startDate){
        String phasenName;
        PhasenTyp phasenTyp;
        int pausenDauer;
        int phasenDauer;
        int saetze;
        int wiederholungen;
        int repmax;

        //Setting phase configs
        phasenName = "Phase 1: Allgemein";
        phasenTyp = PhasenTyp.ALLGEMEIN;
        pausenDauer = 60;
        this.trainiert = trainiert;
        if (this.trainiert) {
            phasenDauer = 4;
            saetze = 3;
            wiederholungen = 25;
        }
        else {
            phasenDauer = 8;
            saetze = 2;
            wiederholungen = 20;
        }
        repmax = 55;
        this.schema = schema;
        if (!trainiert){
            wiederholungen = 20;
            switch (schema){
                case 1:
                    uebungenAnzahl = 9;
                    break;
                case 2:
                    uebungenAnzahl = 6;
                    break;
                case 3:
                    uebungenAnzahl = 8;
                    break;
            }
        }
        else{
            wiederholungen = 25;
            switch (schema){
                case 1:
                    uebungenAnzahl = 9;
                    break;
                case 2:
                    uebungenAnzahl = 8;
                    break;
                case 3:
                    uebungenAnzahl = 8;
                    break;
            }
        }
        this.wochentage = wochentage;
        tPhase = new Trainingsphase(phasenName, phasenTyp, pausenDauer, phasenDauer, saetze, wiederholungen, repmax, startDate);
        fetchUebungen();
    }

    private void fetchUebungen(){
        rep = UebungenRepository.getInstance();
        switch(schema){
            case 1: grabIntoUebungListSchema1(fetchSchema1());
                break;
            case 2:fetchSchema2();
                break;
            case 3:fetchSchema3();
                break;
        }
    }

    private List<String[]> fetchSchema1(){ // Schema: Bauch-Beine-Po
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;
        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            muskelGruppe = c.getString(iMuskelGruppe);
            if (muskelGruppe.contains("Bauch") || muskelGruppe.contains("Beine") || muskelGruppe.contains("Po")){
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            }
        }
        Collections.shuffle(uebungsList);
        List<String[]> actualUebungen = new LinkedList<String[]>();

        int bellyCnt = 0;
        int legCnt = 0;
        // AND Schwierigkeitstufe noch berücksichtigen!!!
        for(String[] uebung : uebungsList) {
            if (uebung[2].toUpperCase().contains("PO")){
                actualUebungen.add(uebung);
            }else{
                if (uebung[2].toUpperCase().contains("BEINE")){
                    if (legCnt < 4) {
                        actualUebungen.add(uebung);
                        legCnt++;
                    }
                } else if (uebung[2].toUpperCase().contains("BAUCH")){
                    if (bellyCnt < 4) {
                        actualUebungen.add(uebung);
                        bellyCnt++;
                    }
                }
            }
        }
        return actualUebungen;
    }

    //TODO
    private void fetchSchema2(){ // Schema: Oberkörper - Arme
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;
        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            muskelGruppe = c.getString(iMuskelGruppe);
            if (muskelGruppe.contains("Bauch") || muskelGruppe.contains("Beine") || muskelGruppe.contains("Po")){
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            }
        }
    }

    //TODO
    private void fetchSchema3(){ // Schema: Stabilisation (Gesundheit, Rücken)
    }

    private void grabIntoUebungListSchema1(List<String[]> uebungen){
        List<Uebung> finalUebungslist = new LinkedList<Uebung>();
        Boolean legbool = null;
        Boolean bellybool = null;
        for (String[] element : uebungen){
            Uebung ueb = new Uebung();
            ueb.setUebungsID(element[0]);
            ueb.setUebungsName(element[1]);

            if (element[2].toUpperCase().contains("PO")){
                ueb.setWochenTag(wochentage[0]);
            }else{
                if (element[2].toUpperCase().contains("BEINE")){
                    if (legbool == null){
                        ueb.setWochenTag(wochentage[0]);
                        legbool = true;
                    }else if (legbool){
                        ueb.setWochenTag(wochentage[1]);
                        legbool = false;
                    }else{
                        ueb.setWochenTag(wochentage[2]);
                        legbool = true;
                    }
                }else if (element[2].toUpperCase().contains("BAUCH")){
                    if (bellybool == null){
                        ueb.setWochenTag(wochentage[0]);
                        bellybool = false;
                    }else if (bellybool){
                        ueb.setWochenTag(wochentage[1]);
                        bellybool = false;
                    }else{
                        ueb.setWochenTag(wochentage[2]);
                        bellybool = true;
                    }
                }
            }
            finalUebungslist.add(ueb);
        }
        getTPhase().setUebungList(finalUebungslist);
    }

    public Trainingsphase getTPhase() {
        return tPhase;
    }
}
