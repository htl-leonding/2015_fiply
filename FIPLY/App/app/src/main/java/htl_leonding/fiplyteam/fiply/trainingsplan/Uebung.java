package htl_leonding.fiplyteam.fiply.trainingsplan;

public class Uebung {
    private String uebungsID; // UebungsID = Rowid in der DB
    private String wochenTag; // Mo
    private String uebungsName;

    public int getTrainingsgewicht(int wiederholungen, int rm, String uebungsID, String uebungsName){
        return 1;
    }

    public String getUebungsName() {
        return uebungsName;
    }

    public void setUebungsName(String uebungsName) {
        this.uebungsName = uebungsName;
    }

    public String getWochenTag() {
        return wochenTag;
    }

    public void setWochenTag(String wochenTag) {
        this.wochenTag = wochenTag;
    }

    public String getUebungsID() {
        return uebungsID;
    }

    public void setUebungsID(String uebungsID) {
        this.uebungsID = uebungsID;
    }
}
