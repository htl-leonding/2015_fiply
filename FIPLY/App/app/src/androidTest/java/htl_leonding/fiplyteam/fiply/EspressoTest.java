package htl_leonding.fiplyteam.fiply;

import android.support.test.espresso.NoMatchingViewException;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by daniel on 21.10.15.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public final ActivityRule<MainActivity> main = new ActivityRule<>(MainActivity.class);

    @Test
    public void test01_showcase(){
        onView(withText("Willkommen in der FIPLY APP!")).check(ViewAssertions.matches(isDisplayed()));
    }

}
