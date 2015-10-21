package htl_leonding.fiplyteam.fiply;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by daniel on 21.10.15.
 * Espresso Tests
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    //Testet, ob sich die MainActivity geöffnet ist - am Anfang - und ob die korrekte Überschrift angezeigt wird.
    @Test
    public void test01_mainActivity(){
        String title = getInstrumentation().getTargetContext().getResources().getString(R.string.welcome);
        onView(withText(title)).check(ViewAssertions.matches(isDisplayed()));
    }

    //Testet, ob sich der Trainingskatalog öffnen lässt und ob die korrekte Überschrift angezeigt wird.
    @Test
    public void test02_katalogOeffnen(){
        onView(withId(R.id.btStartUe)).perform(click());
        String title = getInstrumentation().getTargetContext().getResources().getString(R.string.titleUe);
        onView(withText(title)).check(ViewAssertions.matches(isDisplayed()));
    }

    //Testet, ob sich die Trainingssession öffnen lässt und ob die korrekte Überschrift angezeigt wird.
    @Test
    public void test03_trainingsSessionOeffnen() {
        onView(withId(R.id.btStartTr)).perform(click());
        String title = getInstrumentation().getTargetContext().getResources().getString(R.string.titleTS);
        onView(withText(title)).check(ViewAssertions.matches(isDisplayed()));
    }

    //Testet, ob sich die Usererstellung öffnen lässt und ob die korrekte Überschrift angezeigt wird.
    @Test
    public void test04_userErstellungOeffnen() {
        onView(withId(R.id.btStartEU)).perform(click());
        String title = getInstrumentation().getTargetContext().getResources().getString(R.string.titleEU);
        onView(withText(title)).check(ViewAssertions.matches(isDisplayed()));
    }

}
