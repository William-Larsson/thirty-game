package se.umu.oi17wln.thirty_game.model;

import android.text.GetChars;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit tests for the GameLogic class.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameLogicTest {

    private GameLogic logic;
    private ArrayList<Integer> diceValues;

    /**
     * Runs before each individual test
     * @throws Exception = any exception.
     */
    @Before
    public void setUp() throws Exception {
        this.logic = new GameLogic();
        this.diceValues = new ArrayList(Arrays.asList(4, 4, 2, 1, 1, 1));
    }

    @Test
    public void shouldGetScoreTenWhenScoreModeFIVE(){
        assertEquals(10, logic.calcTurnScore(diceValues, ScoreMode.FIVE));
    }

    @Test
    public void shouldGetScoreFiveWhenScoreModeLOW(){
        assertEquals(5, logic.calcTurnScore(diceValues, ScoreMode.LOW));
    }

    @Test
    public void shouldFilterOutValuesGreaterThanScoreMode()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        ArrayList<Integer> expectedArr = new ArrayList<>(Arrays.asList(2,1,1,1));

        Class[] args = new Class[2];
        args[0] = ArrayList.class;
        args[1] = int.class;

        Method filter = GameLogic.class.getDeclaredMethod("filterScores", args);
        filter.setAccessible(true);
        filter.invoke(logic, diceValues, 2);

        assertEquals(expectedArr, diceValues);
    }

    @Test
    public void shouldSortArrayInDescendingOrder()
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        ArrayList<Integer> arr = new ArrayList<>(Arrays.asList(2, 1, 4, 1, 1, 4));

        Class[] args = new Class[1];
        args[0] = ArrayList.class;

        Method sort = GameLogic.class.getDeclaredMethod("sortInDescendingOrder", args);
        sort.setAccessible(true);
        sort.invoke(logic, arr);

        assertEquals(diceValues, arr);
    }

    @Test
    public void shouldNotAlterOriginalArray(){
        ArrayList arrClone = (ArrayList) diceValues.clone();
        logic.calcTurnScore(diceValues, ScoreMode.LOW);
        assertEquals(arrClone, diceValues);
    }

    @Test(expected = InvocationTargetException.class)
    public void shouldThrowExceptionIfFaultyDiceValueInput()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        // Declare method input parameter types
        Class[] args = new Class[3];
        args[0] = ArrayList.class;
        args[1] = int.class;
        args[2] = int.class;

        Method calc = GameLogic.class.getDeclaredMethod("calcHighestScore", args);
        calc.setAccessible(true); // make method "public"
        int result = (int) calc.invoke(logic, diceValues, 0, ScoreMode.LOW);
    }

    @Test
    public void shouldSumNumbersFromArray()
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException
    {
        Method calc = GameLogic.class.getDeclaredMethod("calcSumOfValues", ArrayList.class);
        calc.setAccessible(true);
        assertEquals(13, calc.invoke(logic, diceValues));
    }
}