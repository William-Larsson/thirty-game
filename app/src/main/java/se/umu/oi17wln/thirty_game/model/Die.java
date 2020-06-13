package se.umu.oi17wln.thirty_game.model;

import java.util.Random;

/**
 * Class representing a single die.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class Die {
    private Random rand;
    private int value;

    /**
     * Create a new die.
     */
    public Die(){
        this.rand = new Random();
    }

    /**
     * Roll a new integer ranging from 1 to 6
     * @return = int: 1, 2, ..., 6
     */
    public int roll() {
        this.value = this.rand.nextInt(6)+1;
        return this.value;
    }

    /**
     * Get the current value of the die.
     * @return = current die integer value.
     */
    public int getValue() {
        return this.value;
    }
}
