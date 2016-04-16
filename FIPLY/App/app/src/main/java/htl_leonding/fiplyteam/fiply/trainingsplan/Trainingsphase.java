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
    private List<Uebung> uebungList = new LinkedList<Uebung>();
    private int pausenDauer; // In Sekunden
    private int phasenDauer; // In Wochen
    private int saetze;
    private int wiederholungen;
    private int repmax;

    private Date startDate;
    private Date endDate;

    public Trainingsphase(Date startDate, int phasenDauer) {
        this.startDate = startDate;
        this.phasenDauer = phasenDauer;
        setEndingDate(startDate);
    }

    // Initialisiert eine Trainingsphase mit ihren Parametern.
    public Trainingsphase(String phasenName, int pausenDauer, int phasenDauer, int saetze, int wiederholungen, int repmax, Date startDate) {
        this.setPhasenName(phasenName);
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
        this.setEndDate(calendar.getTime());
    }

    // Holt sich die Übungen von dem aktuellen Tag
    public List<Uebung> getUebungListOfToday() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE", Locale.GERMAN);
        String dayofweek = dateFormat.format(now);
        //Mögliche Werte:
        //Montag, Dienstag, Mittwoch, Donnerstag, Freitag, Samstag, Sonntag
        return getUebungByDay(dayofweek);
    }

    // Holt sich die Übungen von einem bestimmten Tag
    public List<Uebung> getUebungByDay(String day) {
        List<Uebung> uebungen = new LinkedList<Uebung>();
        List<Uebung> uebungen2 = getUebungList();
        for (Uebung element : getUebungList()) {
            if (element.getWochenTag().equals(day))
                uebungen.add(element);
        }
        return uebungen;
    }

    // Holt sich die gesamte Übungsliste der Trainingsphase
    public List<Uebung> getUebungList() {
        return uebungList;
    }

    public void setUebungList(List<Uebung> uebungList) {
        this.uebungList = uebungList;
    }

    public ArrayList<Uebung> getUebungenAsArrayList() {
        ArrayList<Uebung> uebungen = new ArrayList<Uebung>();
        for (Uebung uebung : getUebungList()) {
            uebungen.add(uebung);
        }
        return uebungen;
    }

    // Überprüft ob die Trainingsphase aktiv ist.
    public boolean isActive() {
        Date newDate = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(newDate);

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(getEndDate());
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(getStartDate());

        boolean isTodayOne = (endDate.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                endDate.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                endDate.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));

        boolean isTodayTwo = (startDate.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                startDate.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                startDate.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));
        if ((newDate.after(getStartDate()) && newDate.before(getEndDate()) || isTodayOne || isTodayTwo)) {
            return true;
        }
        return false;
    }

    public int upcoming(){
        System.out.println(getStartDate().toString());
        System.out.println(getEndDate().toString());


        Date newDate = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(newDate);
        Calendar endDate = Calendar.getInstance();
        endDate.setTime(getEndDate());
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(getStartDate());

        boolean isTodayOne = (endDate.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                endDate.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                endDate.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));

        boolean isTodayTwo = (startDate.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                startDate.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                startDate.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR));

        if (isTodayOne || isTodayTwo)
            return 0;

        if (cal2.before(startDate))
            return 1;
        if (cal2.after(endDate))
            return -1;
        return 0;
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

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getPhasenName() {
        return phasenName;
    }

    public void setPhasenName(String phasenName) {
        this.phasenName = phasenName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public String[] getWochentage() {
        List<String> wochentage = new LinkedList<String>();
        for (Uebung element : uebungList) {
            if (!wochentage.contains(element.getWochenTag())) {
                wochentage.add(element.getWochenTag());
            }
        }
        int cnt = 0;
        String[] tage = null;
        for (String element : wochentage) {
            tage[cnt] = element;
            cnt++;
        }
        return tage;
    }
}
