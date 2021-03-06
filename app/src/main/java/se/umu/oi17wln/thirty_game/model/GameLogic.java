package se.umu.oi17wln.thirty_game.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A class which holds all the data about the current game.
 * Also contains functionality to calculate all necessary
 * aspects of the games logic.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameLogic implements Parcelable {
    private static final int MAX_TURNS = 10;
    private static final int MAX_THROWS = 3;
    // State
    private int currentTurn;
    private int currentThrow;
    private boolean gameIsEnded;

    /**
     * Constructor, builds the game logic
     */
    public GameLogic(){
        this.currentTurn = 1;
        this.currentThrow = 0;
        this.gameIsEnded = false;
    }


    /**
     * Alt. constructor for restoring state
     * from a given parcel.
     * @param in = parcel with previous state.
     */
    protected GameLogic(Parcel in) {
        this.currentTurn = in.readInt();
        this.currentThrow = in.readInt();
        this.gameIsEnded = in.readByte() != 0;
    }


    /**
     * Generates instances of GameLogoc class from a Parcel.
     */
    public static final Creator<GameLogic> CREATOR = new Creator<GameLogic>() {
        @Override
        public GameLogic createFromParcel(Parcel in) {
            return new GameLogic(in);
        }

        @Override
        public GameLogic[] newArray(int size) {
            return new GameLogic[size];
        }
    };


    /**
     * Get the current game turn
     * @return = the current turn
     */
    public int getCurrentTurn() {
        return this.currentTurn;
    }


    /**
     * Get the current throw for the turn
     * @return = the current throw.
     */
    public int getCurrentThrow() {
        return this.currentThrow;
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
     * Check if the game is on its final turn.
     * @return = true is game is ended.
     */
    public boolean gameIsOnFinalTurn(){
        return this.currentTurn >= MAX_TURNS;
    }


    /**
     * Set game is end status
     * @param ended = if game should be ended
     */
    public void setGameIsEnded(boolean ended){
        this.gameIsEnded = ended;
    }


    /**
     * Get game is ended status
     * @return = true if ended.
     */
    public boolean getGameIsEnded(){
        return this.gameIsEnded;
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
            // i < diceValue.size() requires items in reverse order or risk IndexOutOfBounds.
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


    /**
     * Describe contents for Parcelable interface
     * @return = method not used.
     */
    @Override
    public int describeContents() {
        return 0;
    }


    /**
     * Save state of the object instance.
     * @param dest = the parcel to save to.
     * @param flags = not used.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(currentTurn);
        dest.writeInt(currentThrow);
        dest.writeByte((byte) (gameIsEnded ? 1 : 0));
    }
}