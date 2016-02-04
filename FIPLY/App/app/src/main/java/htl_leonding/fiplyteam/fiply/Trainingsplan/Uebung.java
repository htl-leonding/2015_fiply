package htl_leonding.fiplyteam.fiply.Trainingsplan;

/**
 * Created by daniel on 30.01.16.
 */
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
