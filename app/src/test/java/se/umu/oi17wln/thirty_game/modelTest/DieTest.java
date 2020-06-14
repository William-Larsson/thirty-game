package se.umu.oi17wln.thirty_game.modelTest;

import org.junit.Before;
import org.junit.Test;
import se.umu.oi17wln.thirty_game.model.Die;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DieTest {
    private Die die;

    @Before
    public void setUp(){
        this.die = new Die();
    }

    @Test
    public void shouldReturnIntegerLargerThanZero(){
        int subject = die.roll();
        boolean largerThanZero = 0 < subject;
        assertTrue(largerThanZero);
    }

    @Test
    public void shouldReturnIntegerSmallerThanSeven(){
        int subject = die.roll();
        boolean smallerThanSeven = subject < 7;
        assertTrue(smallerThanSeven);
    }

    @Test
    public void shouldGetSameIntegerFromRollAndGet(){
        assertEquals(die.roll(), die.getValue());
    }
}
