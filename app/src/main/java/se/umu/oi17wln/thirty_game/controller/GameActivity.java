package se.umu.oi17wln.thirty_game.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;

import se.umu.oi17wln.thirty_game.R;
import se.umu.oi17wln.thirty_game.model.Dice;
import se.umu.oi17wln.thirty_game.model.GameLogic;

/**
 * Controller class for the main game screen and its
 * underlying logic.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameActivity extends AppCompatActivity {
    private Button endTurnBtn;
    private Button rollDiceBtn;
    private ArrayList<ImageButton> diceButtons;
    private GameLogic gameLogic;
    private Dice dice;

    /**
     * Set up all the things needed when this activity
     * is created, e.g Views, listeners etc.
     *
     * @param savedInstanceState = previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        diceButtons = new ArrayList<>();
        setUpViewInstances();
        setUpButtonListeners();
        setUpDiceListeners();

        // this will need changing to fix rotate resetting
        startNewGame();
    }


    /**
     * Set up all view instances on the game screen.
     */
    private void setUpViewInstances(){
        endTurnBtn = findViewById(R.id.end_turn_btn);
        rollDiceBtn = findViewById(R.id.roll_dice_btn);
        diceButtons.add(findViewById(R.id.diceButton0));
        diceButtons.add(findViewById(R.id.diceButton1));
        diceButtons.add(findViewById(R.id.diceButton2));
        diceButtons.add(findViewById(R.id.diceButton3));
        diceButtons.add(findViewById(R.id.diceButton4));
        diceButtons.add(findViewById(R.id.diceButton5));
    }


    /**
     * Set up listeners for the buttons used to
     * play the game
     */
    private void setUpButtonListeners(){
        endTurnBtn.setOnClickListener((view) -> endGameRound());
        rollDiceBtn.setOnClickListener((view) -> makeNewDiceRoll());
    }


    /**
     * Set up listeners for the dice ImageButtons.
     */
    private void setUpDiceListeners(){
        // set up listeners here.
    }


    /**
     * Start a new game.
     */
    private void startNewGame(){
        this.gameLogic = new GameLogic();
        this.dice = new Dice();
    }


    /**
     * ...
     */
    private void makeNewDiceRoll() {
        ArrayList<Integer> diceValues = dice.roll();
        gameLogic.incrementThrow();
        // update images for the dice Imagebuttons.
        // if current turn == 3, make this button non-clickable

        String output = "Roll: " + diceValues.toString();
        Toast.makeText(GameActivity.this, output, Toast.LENGTH_LONG).show();
    }


    /**
     * ...
     */
    private void endGameRound() {
        // increment current turn
        // save player choice, score etc for this turn.
        // check if game is end:
        //      calc total score and move on to ResultActivity
        // else:
        //      reset dice to be unlocked
        //      reset throw count.

        Toast.makeText(GameActivity.this, "End Turn pressed", Toast.LENGTH_SHORT).show();
    }
}