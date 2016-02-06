package htl_leonding.fiplyteam.fiply.trainingsplan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Trainingsphase {
    private String phasenName;
    private PhasenTyp phasenTyp;
    private List<Uebung> uebungList = new LinkedList<Uebung>();
    private int pausenDauer; // In Sekunden
    private int phasenDauer; // In Wochen
    private int saetze;
    private int wiederholungen;
    private int repmax;

    private Date startDate;
    private Date endDate;

    public Trainingsphase(Date startDate, int phasenDauer){
        this.startDate = startDate;
        this.phasenDauer = phasenDauer;
        setEndingDate(startDate);
    }

    public Trainingsphase(String phasenName, PhasenTyp phasenTyp, int pausenDauer, int phasenDauer, int seatze, int wiederholungen, int repmax, Date startDate){
        this.setPhasenName(phasenName);
        this.setPhasenTyp(phasenTyp);
        this.setPausenDauer(pausenDauer);
        this.setPhasenDauer(phasenDauer);
        this.setSaetze(saetze);
        this.setWiederholungen(wiederholungen);
        this.setRepmax(repmax);
        this.startDate = startDate;
        setEndingDate(this.getStartDate());
    }

    private void setEndingDate(Date startDate) {
        int noOfDays = phasenDauer * 7;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.DAY_OF_YEAR, noOfDays);
        this.endDate = calendar.getTime();
    }

    public List<Uebung> getUebungListOfToday(){
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.GERMAN);
        String dayofweek = dateFormat.format(now);
        //Mögliche Werte:
        //Montag, Dienstag, Mittwoch, Donnerstag, Freitag, Samstag, Sonntag
        return getUebungByDay(dayofweek);
    }

    public List<Uebung> getUebungByDay(String day){
        List<Uebung> uebungen = new LinkedList<Uebung>();
        for (Uebung element : getUebungList()){
            if (element.getWochenTag() == day)
                uebungen.add(element);
        }
        return uebungen;
    }

    public List<Uebung> getUebungList() {
        return uebungList;
    }

    public ArrayList<Uebung> getUebungenAsArrayList(){
        ArrayList<Uebung> uebungen = new ArrayList<Uebung>();
        for (Uebung uebung : getUebungList()){
            uebungen.add(uebung);
        }
        return uebungen;
    }

    public void setUebungList(List<Uebung> uebungList) {
        this.uebungList = uebungList;
    }

    public int getPausenDauer() {
        return pausenDauer;
    }

    public void setPausenDauer(int pausenDauer) {
        this.pausenDauer = pausenDauer;
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

    public int getRepmax() {
        return repmax;
    }

    public void setRepmax(int repmax) {
        this.repmax = repmax;
    }

    public int getPhasenDauer() {
        return phasenDauer;
    }

    public void setPhasenDauer(int phasenDauer) {
        this.phasenDauer = phasenDauer;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getPhasenName() {
        return phasenName;
    }

    public void setPhasenName(String phasenName) {
        this.phasenName = phasenName;
    }

    public PhasenTyp getPhasenTyp() {
        return phasenTyp;
    }

    public void setPhasenTyp(PhasenTyp phasenTyp) {
        this.phasenTyp = phasenTyp;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String[] getWochentage(){
        List<String> wochentage = new LinkedList<String>();
        for (Uebung element : uebungList){
            if (!wochentage.contains(element.getWochenTag())){
                wochentage.add(element.getWochenTag());
            }
        }
        int cnt = 0;
        String[] tage = null;
        for (String element : wochentage){
            tage[cnt] = element;
            cnt++;
        }
        return tage;
    }
}
