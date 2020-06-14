package se.umu.oi17wln.thirty_game.modelTest;

import org.junit.Test;
import se.umu.oi17wln.thirty_game.model.ScoreMode;
import static org.junit.Assert.assertEquals;

public class ScoreModeTest {

    @Test
    public void shouldReturnThreeOnLOW(){
        assertEquals(3, ScoreMode.LOW);
    }
}
