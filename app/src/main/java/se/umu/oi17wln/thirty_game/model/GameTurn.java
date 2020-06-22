package se.umu.oi17wln.thirty_game.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class used to save data from a turn of
 * the game.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameTurn implements Parcelable {
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


    /**
     * Alt. constructor for restoring state
     * from a given parcel.
     * @param in = parcel with previous state.
     */
    protected GameTurn(Parcel in) {
        scoreMode = in.readString();
        turnScore = in.readInt();
        turnNr = in.readInt();
    }


    /**
     * Generates instances of GameTurn class from a Parcel.
     */
    public static final Creator<GameTurn> CREATOR = new Creator<GameTurn>() {
        @Override
        public GameTurn createFromParcel(Parcel in) {
            return new GameTurn(in);
        }

        @Override
        public GameTurn[] newArray(int size) {
            return new GameTurn[size];
        }
    };


    public String getScoreMode() {
        return scoreMode;
    }

    public int getTurnScore() {
        return turnScore;
    }

    public int getTurnNr() {
        return turnNr;
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
        dest.writeString(scoreMode);
        dest.writeInt(turnScore);
        dest.writeInt(turnNr);
    }
}