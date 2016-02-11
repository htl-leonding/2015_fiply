package htl_leonding.fiplyteam.fiply.trainingssession;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private static PlanSessionPort  instance = null;

    public static PlanSessionPort getInstance(){
        if (instance == null) {
            instance = new PlanSessionPort();
        }
        return instance;
    }

    public boolean isGenerated(){
        if (phasenListe.size() > 0){
            return true;
        }
        return false;
    }

    public void setUebungenForToday(){
        FTrainingssession trainingssession = new FTrainingssession();
        Bundle args = new Bundle();
        Calendar c = Calendar.getInstance();

        for (Trainingsphase phase : phasenListe){
            if (phase.isActive()){
                List<Uebung> uebs = phase.getUebungListOfToday();
                args.putInt("uebungsanzahl", uebs.size());

                int cnt = 0;
                for (Uebung ueb : uebs){
                    args.putString("ueb" + cnt, ueb.getUebungsID());
                    args.putString("repmax" + cnt, String.valueOf(ueb.getRepmax()));
                    cnt++;
                }
                args.putString("pausendauer", String.valueOf(phase.getPausenDauer()));
                args.putString("saetze", String.valueOf(phase.getSaetze()));
                args.putString("wiederholungen", String.valueOf(phase.getWiederholungen()));
            }
        }
        trainingssession.setArguments(args);
    }

    public boolean isAnyUebungToday(){
        for (Trainingsphase phase : phasenListe) {
            if (phase.isActive()) {
                if(phase.getUebungListOfToday().size() > 0){
                    return true;
                }
            }
        }
        return false;
    }

    public int howManyUebungToday(){
        int count = 0;
        for (Trainingsphase phase : phasenListe) {
            if (phase.isActive()) {
                count = phase.getUebungListOfToday().size();
            }
        }
        return count;
    }

    public String getProgress(){
        int count = 0;
        String result = "";
        for (Trainingsphase phase : phasenListe){
            count++;
            if (phase.isActive()){
                result += "Sie sind gerade in der " + count + ". von ";
            }
        }
        result += count + " Trainingsphasen.";
        return result;
    }

    public void init(){
        repPhasen = PhasenRepository.getInstance();
        cPhasen = repPhasen.getAllPhasen();

        repInstruktionen = InstruktionenRepository.getInstance();
        cInstruktion = repInstruktionen.getAllInstructions();

        int iPhasenRowId = cPhasen.getColumnIndex(PhasenEntry.COLUMN_ROWID);
        int iPhasenStartDate = cPhasen.getColumnIndex(PhasenEntry.COLUMN_STARTDATE);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        Date convertedDate = new Date();

        Trainingsphase tPhase;
        phasenListe = new LinkedList<Trainingsphase>();
        for (cPhasen.moveToFirst(); !cPhasen.isAfterLast(); cPhasen.moveToNext()) {
            phasenRowId = cPhasen.getString(iPhasenRowId);
            phasenStartDate = cPhasen.getString(iPhasenStartDate);
            phasenName = cPhasen.getString(iPhasenName);
            phasenDauer = cPhasen.getString(iPhasenDauer);
            phasenPausenDauer = cPhasen.getString(iPhasenPausenDauer);
            phasenSaetze = cPhasen.getString(iPhasenSaetze);
            phasenWiederholungen  = cPhasen.getString(iPhasenWiederholungen);

            try {
                convertedDate = dateFormat.parse(phasenStartDate);
            } catch (Exception e) {
            }

            tPhase = new Trainingsphase(phasenName, Integer.valueOf(phasenPausenDauer), Integer.valueOf(phasenDauer),
                    Integer.valueOf(phasenSaetze), Integer.valueOf(phasenWiederholungen), 0, convertedDate);
            tPhase.setUebungList(getInstruktFromPhasenId(phasenRowId));

            phasenListe.add(tPhase);
        }
    }

    private List<Uebung> getInstruktFromPhasenId(String phasenId){
        List<Uebung> resultList = new LinkedList<Uebung>();
        for (Uebung element: instruktListe){
            if (element.getPhasenId() == phasenId){
                resultList.add(element);
            }
        }
        return resultList;
    }

    public String[] getDays() {
        for (Trainingsphase phase : phasenListe){
            if (phase.isActive()){
                return phase.getWochentage();
            }
        }
        return null;
    }
}
