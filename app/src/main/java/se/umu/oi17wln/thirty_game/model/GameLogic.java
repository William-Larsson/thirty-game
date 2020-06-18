package se.umu.oi17wln.thirty_game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * ...
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameLogic {
    private static final int MAX_TURNS = 10;
    private static final int MAX_THROWS = 3;

    private int currentTurn;
    private int currentThrow;

    /**
     * Constructor, builds the game logic
     */
    public GameLogic(){
        this.currentTurn = 1;
        this.currentThrow = 0;
    }


    /**
     * Get the current game turn
     * @return = the current turn
     */
    public int getCurrentTurn() {
        return this.currentTurn;
    }


    /**
     * Increment current turn and reset current throw
     * in order to "move" on to the next turn.
     */
    public void beginNewTurn() {
        this.currentTurn++;
        this.currentThrow = 0;
    }


    /**
     * Check if the game is ended.
     * @return = true is game is ended.
     */
    public boolean gameIsEnd(){
        return this.currentTurn >= MAX_TURNS;
    }


    /**
     * +1 to the throw count.
     */
    public void incrementThrow(){
        this.currentThrow++;
    }


    /**
     * Check if player can make another throw this turn
     * @return = true if can throw.
     */
    public boolean canThrowAgain(){
        return this.currentThrow < MAX_THROWS;
    }


    /**
     * Calculates the score achieved during a
     * game round.
     *
     * @param diceValues = list of dice values.
     * @param scoreMode = the score mode chosen
     * @return = the score for this round.
     */
    public int calcTurnScore(ArrayList<Integer> diceValues, int scoreMode){
        ArrayList<Integer> tempDiceValues = (ArrayList<Integer>) diceValues.clone();
        int score = 0;

        filterScores(tempDiceValues, scoreMode);

        if (scoreMode > ScoreMode.LOW){
            sortInDescendingOrder(tempDiceValues);
            score = calcHighestScore(
                    tempDiceValues,
                    0,
                    0,
                    scoreMode,
                    new ArrayList<Integer>()
            );
        } else {
            score = calcSum(tempDiceValues);
        }
        return score;
    }


    /**
     * Sorts the given array in descending order, from highest
     * value to lowest.
     * @param arr = array to sort
     */
    private void sortInDescendingOrder(ArrayList<Integer> arr) {
        Collections.sort(arr, Collections.<Integer>reverseOrder());
    }


    /**
     * Filter the array so that is only contains values
     * that are equal to or smaller than given score mode.
     */
    private void filterScores(ArrayList<Integer> arr, int scoreMode) {
        ArrayList<Integer> filterRemove = new ArrayList<>();
        for (Integer i : arr) {
            if (i > scoreMode) {
                filterRemove.add(i);
            }
        }
        arr.removeAll(filterRemove);
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
     * @param usedValueIndices =
     * @return = the total score.
     */
    private int calcHighestScore(
            ArrayList<Integer> diceValues,
            int startIndex,
            int sum,
            int scoreTarget,
            ArrayList<Integer> usedValueIndices
    ) throws IllegalArgumentException {
        int returnSum = 0;

        if (sum == scoreTarget) {
            // i< diceValue.size() requires items in reverse order or risk IndexOutOfBounds.
            Collections.sort(usedValueIndices, Collections.reverseOrder());
            for (Integer i : usedValueIndices) {
                if (!diceValues.isEmpty() && i < diceValues.size()) {
                    diceValues.remove(i.intValue());
                }
            }
            usedValueIndices.clear();
            return sum;
        }

        for (int i = startIndex; i < diceValues.size(); i++) {
            if (sum + diceValues.get(i) <= scoreTarget) {
                sum += diceValues.get(i);
                usedValueIndices.add(i);
                returnSum += calcHighestScore(
                        diceValues,
                        i+1,
                        sum,
                        scoreTarget,
                        usedValueIndices
                );
                i = -1; // restart loop (in order to start from beginning of diceValues again)
                sum = 0;
            } else if (diceValues.get(i) > scoreTarget){
                throw new IllegalArgumentException("Array contains values greater than target");
            }
        }
        if (returnSum == 0 && !diceValues.isEmpty()) {
            diceValues.remove(0); // remove first item -> avoid eternal loop
            usedValueIndices.clear(); // make sure first (or any) item is reset from "used"
        }
        return returnSum;
    }


    /**
     * Calculated the sum  of all values in the
     * given array.
     *
     * @return = the sum.
     */
    public int calcSum(ArrayList<Integer> arr) {
        int sum = 0;
        for (Integer i : arr) sum += i;
        return sum;
    }
}
