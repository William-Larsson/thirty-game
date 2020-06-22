package se.umu.oi17wln.thirty_game.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Random;

/**
 * Class representing a single die.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class Die implements Parcelable {
    private Random rand;
    private boolean isLocked;
    private int value;

    /**
     * Create a new die.
     */
    public Die(){
        this.rand = new Random();
        this.isLocked = false;
    }


    /**
     * Alt. constructor for restoring state
     * from a given parcel.
     * @param in = parcel with previous state.
     */
    protected Die(Parcel in) {
        isLocked = in.readByte() != 0;
        value = in.readInt();
    }


    /**
     * Generates instances of Die class from a Parcel.
     */
    public static final Creator<Die> CREATOR = new Creator<Die>() {
        @Override
        public Die createFromParcel(Parcel in) {
            return new Die(in);
        }

        @Override
        public Die[] newArray(int size) {
            return new Die[size];
        }
    };


    /**
     * Roll a new integer ranging from 1 to 6
     * @return = int: 1, 2, ..., 6
     */
    public int roll() {
        if (!isLocked) {
            this.value = this.rand.nextInt(6)+1;
        }
        return this.value;
    }


    /**
     * Get the current value of the die.
     * @return = current die integer value.
     */
    public int getValue() {
        return this.value;
    }


    /**
     * Get isLocked value.
     * @return = false if unlocked, else true
     */
    public boolean isLocked() {
        return isLocked;
    }


    /**
     * Toggle current state for locked.
     */
    public void toogleLockedState() {
        isLocked = !isLocked;
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
        dest.writeByte((byte) (isLocked ? 1 : 0));
        dest.writeInt(value);
    }
}