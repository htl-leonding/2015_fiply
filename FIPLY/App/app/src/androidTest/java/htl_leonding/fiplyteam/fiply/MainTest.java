package htl_leonding.fiplyteam.fiply;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
/**
 * Created by daniel on 20.10.15
 */
public class MainTest {
    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void elementLooker(){
        //Sucht nach einem geraden angezeigten Element mit dem Text "Willkommen in der FIPLY APP!"
        onView(withText("Willkommen in der FIPLY APP!")).check(ViewAssertions.matches(isDisplayed()));
    }
}
