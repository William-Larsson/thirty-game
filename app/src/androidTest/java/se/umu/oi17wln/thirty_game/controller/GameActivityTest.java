package se.umu.oi17wln.thirty_game.controller;

import androidx.test.core.app.ActivityScenario;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import se.umu.oi17wln.thirty_game.R;
import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;

/**
 * Espresso UI tests for the GameActivity screen.
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class GameActivityTest {
    ActivityScenario scenario;

    @Before
    public void setUp() throws Exception {
        this.scenario = ActivityScenario.launch(GameActivity.class);
    }

    @Test
    public void shouldLaunchActivityToView(){
        // check the view, assert that view with ID is displayed on screen
        onView(withId(R.id.game_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void shouldContainRollDiceButton(){
        onView(withId(R.id.roll_dice_btn))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldContainEndTurnButton(){
        onView(withId(R.id.end_turn_btn))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldContainDiceLockHelpText(){
        onView(withId(R.id.dice_lock_help))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDisplayCorrectDiceLockHelpTextSentence(){
        onView(withId(R.id.dice_lock_help))
                .check(matches(withText(R.string.help_text)));
    }
}