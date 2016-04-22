package htl_leonding.fiplyteam.fiply.trainingsplan;

public class RepMax {

    // REPMAX wird berechnet.
    public static int getTrainingsgewicht(int wiederholungen, int repMax) {
        if (wiederholungen == 0 || repMax == 0)
            return 0;

        int result = (int) Math.round((repMax * (102.78 - (2.78 * wiederholungen))) / 100);

        return repMax;
    }
}
