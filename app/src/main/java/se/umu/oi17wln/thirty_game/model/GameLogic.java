package se.umu.oi17wln.thirty_game.model;

import java.util.ArrayList;

/**
 * ...
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameLogic {
    private int roundScore;

    public void calcRoundScore(){
        /**
         * 1. Remove all dice with a higher value than ScoreMode.
         * 2. Sort the remaining dice values in descending order
         * 3. make a function that take the list of sorted value, a int sum and the ScoreMode int
         * 3.1 if Sum == target ScoreMode -> add to round score; return;
         * 3.2 else loop from i = to size of dice value list
         * 3.2 if sum + current dice value <= ScoreMode
         * 3.3 add current dice value to sum, remove dice value from list
         * 3.4 recursively call method with new arraylist for values, sum and the ScoreMode
         * 3.5 i -= 1 and sum = 0
         *
         */
    }

    private void calcHighestScore(ArrayList<Integer> diceValues, int sum, int scoreMode) {
        if (sum == scoreMode) {
            // TODO: something like this looks better:
            // getCurrentRound().addScore(sum);

            this.roundScore += sum;
            return;
        }

        for (int i = 0; i < diceValues.size(); i++) {
            if (sum + diceValues.get(i) <= scoreMode) {
                sum += diceValues.get(i);
                diceValues.remove(i);
                calcHighestScore(diceValues, sum, scoreMode);
                i = -1; // restart loop (in order to start from beginning of diceValues again)
                sum = 0;
            }
        }
    }
}
