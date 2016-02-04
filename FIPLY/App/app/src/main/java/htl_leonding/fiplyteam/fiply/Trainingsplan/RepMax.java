package htl_leonding.fiplyteam.fiply.Trainingsplan;

/**
 * Created by daniel on 30.01.16.
 */
public class RepMax {

    public static int getTrainingsgewicht(int wiederholungen, int repMax){
        if (wiederholungen == 0 || repMax == 0)
            return 0;

        int result = (int)Math.round((repMax*(102.78-(2.78*wiederholungen)))/100);
        return result;
    }
}
