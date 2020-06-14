package se.umu.oi17wln.thirty_game.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameLogicTest {

    private GameLogic logic;

    @Before
    public void setUp() throws Exception {
        this.logic = new GameLogic();
    }

    @Test
    public void smartSumLogicTest(){
        ArrayList arr = new ArrayList();
        arr.add(7);
        arr.add(4);
        arr.add(1);
        arr.add(1);
        int plz = calcHighestScore(arr, 0, 5);
        System.out.println("plz score: " + plz);
    }

    /**
     * This method recursively finds the highest possible score by
     * choosing the value closest to the ScoreMode and loops through
     * each value in descending order to sum up to the targeted
     * value.
     * If the sum of a set of values adds up to the given
     * ScoreMode target method will step back in the recursive stack.
     *
     * @param diceValues = A list of sorted numbers in descending order.
     *                   Cannot contain values higher than the given
     *                   ScoreMode target value.
     * @param sum = the current sum of a given subset.
     * @param scoreTarget = the target value chosen by the player.
     * @return = the total score.
     */
    private int calcHighestScore(ArrayList<Integer> diceValues, int sum, int scoreTarget) {
        int returnSum = 0;

        if (sum == scoreTarget) {
            return returnSum + sum;
        }

        for (int i = 0; i < diceValues.size(); i++) {
            if (sum + diceValues.get(i) <= scoreTarget) {
                sum += diceValues.get(i);
                diceValues.remove(i);
                returnSum += calcHighestScore(diceValues, sum, scoreTarget);
                i = -1; // restart loop (in order to start from beginning of diceValues again)
                sum = 0;
            } else if (diceValues.get(i) > scoreTarget){
                throw new IllegalArgumentException("Array contains values greater than target");
            }
        }
        return returnSum;
    }
}