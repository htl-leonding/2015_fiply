package htl_leonding.fiplyteam.fiply.trainingssession;

import android.database.Cursor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import htl_leonding.fiplyteam.fiply.data.FiplyContract.InstruktionenEntry;
import htl_leonding.fiplyteam.fiply.data.FiplyContract.PhasenEntry;
import htl_leonding.fiplyteam.fiply.data.InstruktionenRepository;
import htl_leonding.fiplyteam.fiply.data.KeyValueRepository;
import htl_leonding.fiplyteam.fiply.data.PhasenRepository;
import htl_leonding.fiplyteam.fiply.trainingsplan.Trainingsphase;
import htl_leonding.fiplyteam.fiply.trainingsplan.Uebung;

public class PlanSessionPort {

    private static PhasenRepository repPhasen;
    private static InstruktionenRepository repInstruktionen;
    private static KeyValueRepository keyv;

    private Cursor cPhasen;
    private Cursor cInstruktion;
    private List<Uebung> instruktListe;
    private List<Trainingsphase> phasenListe = new LinkedList<Trainingsphase>();
    private static PlanSessionPort instance = null;

    public static PlanSessionPort getInstance(){
        if (instance == null) {
            instance = new PlanSessionPort();
        }
        return instance;
    }

    public boolean isGenerated(){
        if (getPhasenListe().size() > 0){
            return true;
        }
        return false;
    }

    public boolean isAnyUebungToday(){
        if (howManyUebungToday() > 0)
            return true;
        return false;
    }

    public int howManyUebungToday(){
        int count = 0;
        for (Trainingsphase phase : getPhasenListe()) {
            if (phase.isActive()) {
                count = phase.getUebungListOfToday().size();
            }
        }
        return count;
    }

    public String getProgress(){
        String result = "Aktuelle Trainingsphase: ";
        int cnt = 0;
        for (Trainingsphase phase : getPhasenListe()){
            cnt++;
            if (phase.isActive()){
                result += cnt + " von ";
            }
        }
        return result + cnt + ".";
    }

    public int getPhaseIndex(){
        int cnt = 0;
        for (Trainingsphase phase : getPhasenListe()){
            cnt++;
            if (phase.isActive()){
                return cnt;
            }
        }
        return 0;
    }

    public void init(){
        repPhasen = PhasenRepository.getInstance();
        cPhasen = repPhasen.getAllPhasen();

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

        String instruktWochentag;
        String instruktRepMax;
        String instruktUebungsId;
        String instruktPhasenId;

        instruktListe = new LinkedList<Uebung>();
        Uebung uebung;
        for (cInstruktion.moveToFirst(); !cInstruktion.isAfterLast(); cInstruktion.moveToNext()){
            instruktWochentag = cInstruktion.getString(iInstruktionWochentag);
            instruktRepMax = cInstruktion.getString(iInstruktionRepMax);
            instruktUebungsId = cInstruktion.getString(iInstruktionUebungsId);
            instruktPhasenId = cInstruktion.getString(iInstruktionPhasenId);

            uebung = new Uebung();
            uebung.setWochenTag(instruktWochentag);
            uebung.setRepmax(Integer.valueOf(instruktRepMax));
            uebung.setUebungsID(instruktUebungsId);
            uebung.setPhasenId(instruktPhasenId);
            instruktListe.add(uebung);
        }

        DateFormat format = new SimpleDateFormat("dd. MMMM yyyy", Locale.ENGLISH);
        Date convertedStartDate;
        Date convertedEndDate;

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
            phasenWiederholungen  = cPhasen.getString(iPhasenWiederholungen);

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

    private List<Uebung> getInstruktFromPhasenId(String phasenId){
        List<Uebung> resultList = new LinkedList<Uebung>();
        String id;
        for (Uebung element: instruktListe){
            id = element.getPhasenId();
            if (id.equals(phasenId)){
                resultList.add(element);
            }
        }
        return resultList;
    }

    public String[] getDays() {
        for (Trainingsphase phase : getPhasenListe()){
            if (phase.isActive()){
                return phase.getWochentage();
            }
        }
        return null;
    }

    public Trainingsphase getCurrentPhase(){
        for (Trainingsphase phase : getPhasenListe()){
            if (phase.isActive()){
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
