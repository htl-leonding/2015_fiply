package htl_leonding.fiplyteam.fiply.trainingsplan;

import android.database.Cursor;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class GenerateAllgemein {
    boolean trainiert;
    int schema; // 1 = Bauch-Beine-Po; 2 = Oberkörper-Arme; 3 = Stabilisation(Gesundheit-Rücken)
    String[] wochentage;
    UebungenRepository rep;
    int repmax = 55;
    int wiederholungen;
    private Trainingsphase tPhase;
    private int uebungenAnzahl;

    // Initialierst die angegebenen Daten
    public GenerateAllgemein(boolean trainiert, int schema, String[] wochentage, Date startDate) {
        String phasenName;
        int pausenDauer;
        int phasenDauer;
        int saetze;
        int wiederholungen;
        int repmax;

        //Setting phase configs
        phasenName = "Phase 1: Allgemein";
        pausenDauer = 60;
        this.trainiert = trainiert;
        if (this.trainiert) {
            phasenDauer = 4;
            saetze = 3;
            wiederholungen = 25;
        } else {
            phasenDauer = 8;
            saetze = 2;
            wiederholungen = 20;
        }
        repmax = 55;
        this.schema = schema;
        if (!trainiert) {
            wiederholungen = 20;
            switch (schema) {
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
        } else {
            wiederholungen = 25;
            switch (schema) {
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
        tPhase = new Trainingsphase(phasenName, pausenDauer, phasenDauer, saetze, wiederholungen, repmax, startDate);
        fetchUebungen();
    }

    // Holt sich alle Übungen aus dem Übungskatalog
    private void fetchUebungen() {
        rep = UebungenRepository.getInstance();
        switch (schema) {
            case 1:
                grabIntoUebungListSchema1(fetchSchema1());
                break;
            case 2:
                grabIntoUebungListSchema2(fetchSchema2());
                break;
            case 3:
                grabIntoUebungListSchema3(fetchSchema3());
                break;
        }
    }

    // Organisiert die Übungen zum Schema 1
    private List<String[]> fetchSchema1() { // Schema: Bauch-Beine-Po
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;
        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            muskelGruppe = c.getString(iMuskelGruppe);
            if (muskelGruppe.contains("Bauch") || muskelGruppe.contains("Beine") || muskelGruppe.contains("Po")) {
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
        for (String[] uebung : uebungsList) {
            if (uebung[2].toUpperCase().contains("PO")) {
                actualUebungen.add(uebung);
            } else {
                if (uebung[2].toUpperCase().contains("BEINE")) {
                    if (legCnt < 4) {
                        actualUebungen.add(uebung);
                        legCnt++;
                    }
                } else if (uebung[2].toUpperCase().contains("BAUCH")) {
                    if (bellyCnt < 4) {
                        actualUebungen.add(uebung);
                        bellyCnt++;
                    }
                }
            }
        }
        return actualUebungen;
    }

    // Organisiert die Übungen zum Schema 2
    private List<String[]> fetchSchema2() { // Schema: Oberkörper - Arme
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;

        boolean obereBrust = false;
        boolean mittlereBrust = false;
        boolean untereBrust = false;
        boolean ruecken1 = false;
        boolean ruecken2 = false;
        boolean hintereSchulter = false;
        boolean vordereSchulter = false;
        boolean bizeps = false;
        boolean trizeps = false;

        List<String[]> uebungsList = new LinkedList<String[]>();

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            muskelGruppe = c.getString(iMuskelGruppe);
            if (muskelGruppe.contains("Obere Brust") && !obereBrust) {
                obereBrust = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if (muskelGruppe.contains("Untere Brust") && !untereBrust) {
                untereBrust = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if (muskelGruppe.contains("Mittlere Brust") && !mittlereBrust) {
                mittlereBrust = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if (muskelGruppe.contains("Rücken") && !ruecken1) {
                ruecken1 = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if ((muskelGruppe.contains("Rücken") && !ruecken2)) {
                ruecken2 = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if ((muskelGruppe.contains("Hintere Schulter") && !hintereSchulter)) {
                hintereSchulter = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if ((muskelGruppe.contains("Vordere Schulter") && !vordereSchulter)) {
                vordereSchulter = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if ((muskelGruppe.contains("Bizeps") && !bizeps)) {
                bizeps = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            } else if ((muskelGruppe.contains("Trizeps") && !trizeps)) {
                trizeps = true;
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            }
        }
        return uebungsList;
    }

    // Organisiert die Übungen zum Schema 3
    public List<String[]> fetchSchema3() { // Schema: Stabilisation (Gesundheit, Rücken)
        Cursor c = rep.getAllUebungen();

        int iRowId = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_ROWID);
        int iMuskelGruppe = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_MUSKELGRUPPE);
        int iUebungsName = c.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
        String rowId;
        String muskelGruppe;
        String uebungsName;
        List<String[]> uebungsList = new LinkedList<String[]>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            muskelGruppe = c.getString(iMuskelGruppe);
            if (muskelGruppe.contains("Stabilisation")) {
                rowId = c.getString(iRowId);
                uebungsName = c.getString(iUebungsName);
                String[] newUebung = {rowId, uebungsName, muskelGruppe};
                uebungsList.add(newUebung);
            }
        }
        Collections.shuffle(uebungsList);
        return uebungsList;
    }

    // Setzt die Übungen für Schema 1 in die Liste
    private void grabIntoUebungListSchema1(List<String[]> uebungen) {
        List<Uebung> finalUebungslist = new LinkedList<Uebung>();
        Boolean legbool = null;
        Boolean bellybool = null;
        for (String[] element : uebungen) {
            Uebung ueb = new Uebung();
            ueb.setUebungsID(element[0]);
            ueb.setUebungsName(element[1]);
            ueb.setRepmax(repmax);
            if (element[2].toUpperCase().contains("PO")) {
                ueb.setWochenTag(wochentage[0]);
            } else {
                if (element[2].toUpperCase().contains("BEINE")) {
                    if (legbool == null) {
                        ueb.setWochenTag(wochentage[0]);
                        legbool = true;
                    } else if (legbool) {
                        ueb.setWochenTag(wochentage[1]);
                        legbool = false;
                    } else {
                        ueb.setWochenTag(wochentage[2]);
                        legbool = true;
                    }
                } else if (element[2].toUpperCase().contains("BAUCH")) {
                    if (bellybool == null) {
                        ueb.setWochenTag(wochentage[0]);
                        bellybool = false;
                    } else if (bellybool) {
                        ueb.setWochenTag(wochentage[1]);
                        bellybool = false;
                    } else {
                        ueb.setWochenTag(wochentage[2]);
                        bellybool = true;
                    }
                }
            }
            finalUebungslist.add(ueb);
        }
        getTPhase().setUebungList(finalUebungslist);
    }

    // Setzt die Übungen für Schema 2 in die Liste
    public void grabIntoUebungListSchema2(List<String[]> uebungen) {
        List<Uebung> finalUebungslist = new LinkedList<Uebung>();
        for (String[] element : uebungen) {
            Uebung ueb = new Uebung();
            ueb.setUebungsID(element[0]);
            ueb.setUebungsName(element[1]);
            ueb.setRepmax(repmax);

            if (element[2].contains("Brust")) {
                ueb.setWochenTag(wochentage[0]);
            } else if (element[2].contains("Rücken") || element[1].contains("Hintere")) {
                ueb.setWochenTag(wochentage[1]);
            } else {
                ueb.setWochenTag(wochentage[2]);
            }
            finalUebungslist.add(ueb);
        }
        getTPhase().setUebungList(finalUebungslist);
    }

    // Setzt die Übungen für Schema 3 in die Liste
    public void grabIntoUebungListSchema3(List<String[]> uebungen) {
        List<Uebung> finalUebungslist = new LinkedList<Uebung>();
        Collections.shuffle(uebungen);
        Uebung ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(0)[0]);
        ueb.setUebungsName(uebungen.get(0)[1]);
        ueb.setWochenTag(wochentage[0]);
        ueb.setRepmax(0);
        finalUebungslist.add(ueb);
        ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(1)[0]);
        ueb.setUebungsName(uebungen.get(1)[1]);
        ueb.setWochenTag(wochentage[0]);
        finalUebungslist.add(ueb);
        ueb.setRepmax(0);
        ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(2)[0]);
        ueb.setUebungsName(uebungen.get(2)[1]);
        ueb.setWochenTag(wochentage[1]);
        finalUebungslist.add(ueb);
        ueb.setRepmax(0);
        ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(3)[0]);
        ueb.setUebungsName(uebungen.get(3)[1]);
        ueb.setWochenTag(wochentage[1]);
        finalUebungslist.add(ueb);
        ueb.setRepmax(0);
        ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(4)[0]);
        ueb.setUebungsName(uebungen.get(4)[1]);
        ueb.setWochenTag(wochentage[2]);
        finalUebungslist.add(ueb);
        ueb.setRepmax(0);
        ueb = new Uebung();
        ueb.setUebungsID(uebungen.get(5)[0]);
        ueb.setUebungsName(uebungen.get(5)[1]);
        ueb.setWochenTag(wochentage[2]);
        finalUebungslist.add(ueb);
        getTPhase().setUebungList(finalUebungslist);
    }

    // getter für die Trainingsphase
    public Trainingsphase getTPhase() {
        return tPhase;
    }
}
