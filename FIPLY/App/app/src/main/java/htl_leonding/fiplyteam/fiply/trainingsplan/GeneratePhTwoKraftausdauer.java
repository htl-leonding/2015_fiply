package htl_leonding.fiplyteam.fiply.trainingsplan;


import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import htl_leonding.fiplyteam.fiply.data.UebungenRepository;

public class GeneratePhTwoKraftausdauer {
    String[] wochentage;
    UebungenRepository rep;
    List<Uebung> uebungen;
    private Trainingsphase tPhase;
    private Date startDate;
    private Date endDate;

    // Initialisiert den Generierungsvorgang
    public GeneratePhTwoKraftausdauer(String[] wochentage, Date startDate, boolean muscle) {
        this.wochentage = wochentage;
        this.setStartDate(startDate);
        tPhase = new Trainingsphase("Phase 2: Kraftausdauer", 60, 8, 3, 20, 80, getStartDate());
        setFirstConcept();
        if (muscle) {
            setMuscleConcept();
        } else {
            setStabilizedConcept();
        }
        tPhase.setUebungList(uebungen);
    }

    // Erstes Phasenkonzept wird generiert
    private void setFirstConcept() {
        String[] wedays = {"Montag", "Dienstag", "Mittwoch"};
        GenerateAllgemein gAlg = new GenerateAllgemein(false, 1, wedays, getStartDate());
        Trainingsphase tPhase = gAlg.getTPhase();
        Uebung udel1 = null, udel2 = null, udel3 = null;
        boolean del1 = false, del2 = false, del3 = false;
        uebungen = new LinkedList<Uebung>();
        uebungen = tPhase.getUebungList();
        for (Uebung element : uebungen) {
            if (!del1 && element.getWochenTag() == wedays[0]) {
                del1 = true;
                udel1 = element;
            }
            if (!del2 && element.getWochenTag() == wedays[1]) {
                del2 = true;
                udel2 = element;
            }
            if (!del3 && element.getWochenTag() == wedays[2]) {
                del3 = true;
                udel3 = element;
            }
        }
        uebungen.remove(udel1);
        uebungen.remove(udel2);
        uebungen.remove(udel3);
        Collections.shuffle(uebungen);
        int cnt = 0;
        for (Uebung element : uebungen) {
            if (cnt < 3) {
                element.setWochenTag(wochentage[0]);
            } else {
                element.setWochenTag(wochentage[1]);
            }
            cnt++;
        }
    }

    // Muskelphasenkonzept wird generiert
    private void setMuscleConcept() {
        String[] wedays = {"Montag", "Dienstag", "Mittwoch"};
        GenerateAllgemein gAlg = new GenerateAllgemein(false, 2, wedays, getStartDate());
        Trainingsphase tPhase = gAlg.getTPhase();
        List<Uebung> uebs = tPhase.getUebungList();
        Uebung udel1 = null, udel2 = null, udel3 = null;
        boolean del1 = false, del2 = false, del3 = false;
        uebs = tPhase.getUebungList();
        for (Uebung element : uebs) {
            if (!del1 && element.getWochenTag() == wedays[0]) {
                del1 = true;
                udel1 = element;
            }
            if (!del2 && element.getWochenTag() == wedays[1]) {
                del2 = true;
                udel2 = element;
            }
            if (!del3 && element.getWochenTag() == wedays[2]) {
                del3 = true;
                udel3 = element;
            }
        }
        uebs.remove(udel1);
        uebs.remove(udel2);
        uebs.remove(udel3);
        Collections.shuffle(uebs);
        for (Uebung element : uebs) {
            element.setWochenTag(wochentage[2]);
            element.setRepmax(80);
        }
        uebungen.addAll(uebs);
    }

    private void setStabilizedConcept() {
        String[] wedays = {"Montag", "Dienstag", "Mittwoch"};
        GenerateAllgemein gAlg = new GenerateAllgemein(false, 3, wedays, getStartDate());
        Trainingsphase tPhase = gAlg.getTPhase();
        List<Uebung> uebs = tPhase.getUebungList();
        for (Uebung element : uebs) {
            element.setWochenTag(wochentage[2]);
            element.setRepmax(0);
        }
        uebungen.addAll(uebs);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Trainingsphase getTPhase() {
        return tPhase;
    }
}
