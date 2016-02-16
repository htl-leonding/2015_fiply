package htl_leonding.fiplyteam.fiply.trainingsplan;

/**
 * Created by daniel on 14.02.16.
 */
public class Trainingsplanlistitem {

    private String name;
    private String startDate;
    private String endDate;
    private String ziel;

    public Trainingsplanlistitem(String name, String startDate, String endDate, String ziel){
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.ziel = ziel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }


    public String getDauer(){
        return getStartDate() + " - " + getEndDate();
    }

    public String getZiel() {
        return ziel;
    }


}
