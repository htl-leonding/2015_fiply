package htl_leonding.fiplyteam.fiply.trainingssession;

import android.database.Cursor;

import java.security.Key;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.data.FiplyContract;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PhasenEntry;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.data.UebungenRepository;
import htl_leonding.fiplyteam.fiply.trainingsplan.Trainingsphase;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;

public class PlanSessionPort {

    private static PhasenRepository repPhasen;
    private static InstruktionenRepository repInstruktionen;
    private static KeyValueRepository keyv;
    private static UebungenRepository uebungRep;
    private static PlanSessionPort instance = null;
    private Cursor cPhasen;
    private Cursor cInstruktion;
    private Cursor cUebungen;
    private List<Uebung> instruktListe;
    private List<Trainingsphase> phasenListe = new LinkedList<Trainingsphase>();

    public static PlanSessionPort getInstance() {
        if (instance == null) {
            instance = new PlanSessionPort();
        }
        return instance;
    }

    public boolean isGenerated() {
        if (getPhasenListe().size() > 0) {
            return true;
        }
        return false;
    }

    public boolean isAnyUebungToday() {
        if (howManyUebungToday() > 0)
            return true;
        return false;
    }

    public int howManyUebungToday() {
        int count = 0;
        for (Trainingsphase phase : getPhasenListe()) {
            if (phase.isActive()) {
                count = phase.getUebungListOfToday().size();
            }
        }
        return count;
    }

    public int getPhaseIndex() {
        int cnt = 0;
        int ind = 0;
        for (Trainingsphase phase : getPhasenListe()) {
            cnt = cnt + phase.upcoming();
            ind++;
            if (ind == 3)
                break;
        }
        if (cnt > 0)
            return 1;
        else if (cnt < 0)
            return 3;
        return 2;
    }
    public void init() {
        repPhasen = PhasenRepository.getInstance();
        cPhasen = repPhasen.getAllPhasen();
        uebungRep = UebungenRepository.getInstance();
        keyv = KeyValueRepository.getInstance();

        repInstruktionen = InstruktionenRepository.getInstance();
        cInstruktion = repInstruktionen.getAllInstructions();

        int iPhasenRowId = cPhasen.getColumnIndex(PhasenEntry.COLUMN_ROWID);
        int iPhasenStartDate = cPhasen.getColumnIndex(PhasenEntry.COLUMN_STARTDATE);
        int iPhasenEndDate = cPhasen.getColumnIndex(PhasenEntry.COLUMN_ENDDATE);
        int iPhasenName = cPhasen.getColumnIndex(PhasenEntry.COLUMN_PHASENNAME);
        int iPhasenDauer = cPhasen.getColumnIndex(PhasenEntry.COLUMN_PHASENDAUER);
        int iPhasenPausenDauer = cPhasen.getColumnIndex(PhasenEntry.COLUMN_PAUSENDAUER);
        int iPhasenSaetze = cPhasen.getColumnIndex(PhasenEntry.COLUMN_SAETZE);
        int iPhasenWiederholungen = cPhasen.getColumnIndex(PhasenEntry.COLUMN_WIEDERHOLUNGEN);
        int iPhasenPlanId = cPhasen.getColumnIndex(PhasenEntry.COLUMN_PLANID);

        int iInstruktionWochentag = cInstruktion.getColumnIndex(InstruktionenEntry.COLUMN_WOCHENTAG);
        int iInstruktionRepMax = cInstruktion.getColumnIndex(InstruktionenEntry.COLUMN_REPMAX);
        int iInstruktionUebungsId = cInstruktion.getColumnIndex(InstruktionenEntry.COLUMN_UEBUNGSID);
        int iInstruktionPhasenId = cInstruktion.getColumnIndex(InstruktionenEntry.COLUMN_PHASENID);

        String phasenRowId;
        String phasenStartDate;
        String phasenEndDate;
        String phasenName;
        String phasenDauer;
        String phasenPausenDauer;
        String phasenSaetze;
        String phasenWiederholungen;
        String phasenPlanId;

        String instruktWochentag;
        String instruktRepMax;
        String instruktUebungsId;
        String instruktPhasenId;

        instruktListe = new LinkedList<Uebung>();
        Uebung uebung;

        int count = 0;

        for (cInstruktion.moveToFirst(); !cInstruktion.isAfterLast(); cInstruktion.moveToNext()) {
            instruktWochentag = cInstruktion.getString(iInstruktionWochentag);
            instruktRepMax = cInstruktion.getString(iInstruktionRepMax);
            instruktUebungsId = cInstruktion.getString(iInstruktionUebungsId);
            instruktPhasenId = cInstruktion.getString(iInstruktionPhasenId);

            uebung = new Uebung();
            uebung.setWochenTag(instruktWochentag);
            uebung.setRepmax(Integer.valueOf(instruktRepMax));
            uebung.setUebungsID(instruktUebungsId);
            uebung.setPhasenId(instruktPhasenId);
            try {
                cUebungen = uebungRep.getUebung(Long.valueOf(instruktUebungsId));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int iUebungsName = cUebungen.getColumnIndex(FiplyContract.UebungenEntry.COLUMN_NAME);
            cUebungen.moveToFirst();
            String uebungsName = cUebungen.getString(iUebungsName);
            uebung.setUebungsName(uebungsName);
            instruktListe.add(uebung);
        }

        DateFormat format = new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH);
        Date convertedStartDate;
        Date convertedEndDate;
        int chosen = 0;
        try {
             chosen = Integer.valueOf(keyv.getKeyValue("selectedPlan").getString(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Trainingsphase tPhase;
        setPhasenListe(new LinkedList<Trainingsphase>());
        for (cPhasen.moveToFirst(); !cPhasen.isAfterLast(); cPhasen.moveToNext()) {
            phasenRowId = cPhasen.getString(iPhasenRowId);
            phasenStartDate = cPhasen.getString(iPhasenStartDate);
            phasenEndDate = cPhasen.getString(iPhasenEndDate);
            phasenName = cPhasen.getString(iPhasenName);
            phasenDauer = cPhasen.getString(iPhasenDauer);
            phasenPausenDauer = cPhasen.getString(iPhasenPausenDauer);
            phasenSaetze = cPhasen.getString(iPhasenSaetze);
            phasenWiederholungen = cPhasen.getString(iPhasenWiederholungen);
            phasenPlanId = cPhasen.getString(iPhasenPlanId);
            if (Integer.valueOf(phasenPlanId) - 1 == chosen) {
                try {
                    convertedStartDate = format.parse(phasenStartDate);
                    convertedEndDate = format.parse(phasenEndDate);
                } catch (Exception e) {
                    convertedStartDate = null;
                    convertedEndDate = null;
                }

                tPhase = new Trainingsphase(phasenName, Integer.valueOf(phasenPausenDauer), Integer.valueOf(phasenDauer),
                        Integer.valueOf(phasenSaetze), Integer.valueOf(phasenWiederholungen), 0, convertedStartDate);
                tPhase.setEndDate(convertedEndDate);
                tPhase.setUebungList(getInstruktFromPhasenId(phasenRowId));

                getPhasenListe().add(tPhase);
            }
        }
    }

    private List<Uebung> getInstruktFromPhasenId(String phasenId) {
        List<Uebung> resultList = new LinkedList<Uebung>();
        String id;
        for (Uebung element : instruktListe) {
            id = element.getPhasenId();
            if (id.equals(phasenId)) {
                resultList.add(element);
            }
        }
        return resultList;
    }

    public String[] getDays() {
        for (Trainingsphase phase : getPhasenListe()) {
            if (phase.isActive()) {
                return phase.getWochentage();
            }
        }
        return null;
    }

    public Trainingsphase getCurrentPhase() {
        for (Trainingsphase phase : getPhasenListe()) {
            if (phase.isActive()) {
                return phase;
            }
        }
        return null;
    }

    public List<Trainingsphase> getPhasenListe() {
        return phasenListe;
    }

    public void setPhasenListe(List<Trainingsphase> phasenListe) {
        this.phasenListe = phasenListe;
    }
}
