package htl_leonding.fiplyteam.fiply.Trainingsplan;

/**
 * Created by daniel on 30.01.16.
 */
public class RepMax {
    private int wiederholungen = 0;
    private int repMax = 0;

    public RepMax(){}

    public RepMax(int repMax, int wiederholungen){
        this.setRepMax(repMax);
        this.setWiederholungen(wiederholungen);
    }

    public int getTrainingsgewicht(){
        if (wiederholungen == 0 || repMax == 0)
            return 0;

        int result = (int)Math.round((getRepMax()*(102.78-(2.78*wiederholungen)))/100);
        return result;
    }

    public int getWiederholungen() {
        return wiederholungen;
    }

    public void setWiederholungen(int wiederholungen) {
        this.wiederholungen = wiederholungen;
    }

    public int getRepMax() {
        return repMax;
    }

    public void setRepMax(int repMax) {
        this.repMax = repMax;
    }
}
