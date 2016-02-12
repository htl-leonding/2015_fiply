package htl_leonding.fiplyteam.fiply.trainingsplan;

public class Uebung {
    private String uebungsID; // UebungsID = Rowid in der DB
    private String wochenTag; //
    private String uebungsName;
    private int repmax;
    private String muskelgruppe;
    private String phasenId;

    public int getTrainingsgewicht(int wiederholungen, int rm) {
        return (int) (rm * ((102.78) - 2.78 * wiederholungen)) / 100;
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

    public int getRepmax() {
        return repmax;
    }

    public void setRepmax(int repmax) {
        this.repmax = repmax;
    }

    public String getMuskelgruppe() {
        return muskelgruppe;
    }

    public void setMuskelgruppe(String muskelgruppe) {
        this.muskelgruppe = muskelgruppe;
    }

    public String getPhasenId() {
        return phasenId;
    }

    public void setPhasenId(String phasenId) {
        this.phasenId = phasenId;
    }
}
