package se.umu.oi17wln.thirty_game.model;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class DiceTest {
    private Dice dice;

    @Before
    public void setUp() throws Exception {
        this.dice = new Dice();
    }

    @Test
    public void shouldReturnNonZeroDieValuesOnRoll(){
        ArrayList<Integer> diceValues = dice.roll();

        for (Integer i : diceValues){
            assertNotEquals(0, i.intValue());
        }
    }

    @Test
    public void shouldLockDieOnToggle()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        int index = 0;
        dice.toggleDieLockOnIndex(index);

        Method getDice = Dice.class.getDeclaredMethod("getListOfDice", null);
        getDice.setAccessible(true);
        ArrayList<Die> dieList = (ArrayList<Die>) getDice.invoke(dice, null);

        assertTrue(dieList.get(index).isLocked());
    }

    @Test
    public void shouldUnLockDieOnDoubleToggle()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        int index = 0;
        dice.toggleDieLockOnIndex(index);
        dice.toggleDieLockOnIndex(index);

        Method getDice = Dice.class.getDeclaredMethod("getListOfDice", null);
        getDice.setAccessible(true);
        ArrayList<Die> dieList = (ArrayList<Die>) getDice.invoke(dice, null);

        assertFalse(dieList.get(index).isLocked());
    }

    @Test
    public void shouldNotRollLockedDice(){
        int index = 0;
        dice.toggleDieLockOnIndex(index);

        ArrayList<Integer> diceValues = dice.roll();
        assertEquals(0, diceValues.get(index).intValue());

    }

}