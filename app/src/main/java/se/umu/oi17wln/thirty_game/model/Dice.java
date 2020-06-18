package se.umu.oi17wln.thirty_game.model;

import java.util.ArrayList;

/**
 * Class for operations on the dice
 * used in the game.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class Dice {
    private ArrayList<Die> dice;

    public Dice(){
        dice = new ArrayList<>();

        for(int i = 0; i < 6; i++){
            dice.add(new Die());
        }
    }


    /**
     * Roll the dice that are not locked in place.
     * @return = list of die values after rolling.
     */
    public ArrayList<Integer> roll(){
        ArrayList<Integer> diceValues = new ArrayList<>();
        for (Die die : dice){
            int value;
            if (die.isLocked()){
                value = die.getValue();
            } else {
                value = die.roll();
            }
            diceValues.add(value);
        }
        return diceValues;
    }


    /**
     * Toggle locked/unlocked status on die in
     * dice ArrayList
     * @param index = index of the die.
     */
    public void toggleDieLockOnIndex(int index){
        dice.get(index).toogleLockedState();
    }


    /**
     * Return die locked state for die on given index.
     * @param index = which die in list to scrutinize
     * @return = true if locked, else false.
     */
    public boolean dieIsLocked(int index){
        return dice.get(index).isLocked();
    }


    /**
     * Get value of die on given index
     * @param index = which die to scrutinize
     * @return = the die value.
     */
    public int getDieValue(int index){
        return dice.get(index).getValue();
    }


    /**
     * Returns ArrayList "dice".
     * Used for testing by using Java reflections.
     * @return = the list of Die objects.
     */
    private ArrayList<Die> getListOfDice(){
        return this.dice;
    }
}
