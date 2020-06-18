package se.umu.oi17wln.thirty_game.model;

/**
 * Class used to save data from a turn of
 * the game.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameTurn {
    private String scoreMode;
    private int turnScore;
    private int turnNr;

    /**
     * Constructor, builds the object.
     * @param scoreMode = players chosen score mode
     * @param turnScore = the score of this turn
     * @param turnNr = which turn it was.
     */
    public GameTurn(String scoreMode, int turnScore, int turnNr){
        this.scoreMode = scoreMode;
        this.turnScore = turnScore;
        this.turnNr = turnNr;
    }

    public String getScoreMode() {
        return scoreMode;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public int getTurnNr() {
        return turnNr;
    }
}
