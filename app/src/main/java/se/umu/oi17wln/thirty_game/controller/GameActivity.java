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

        setUpViewInstances();
        setUpButtonListeners();
        setUpDiceListeners();
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
        endTurnBtn.setOnClickListener((view) -> {
            Toast.makeText(GameActivity.this, "End Turn pressed", Toast.LENGTH_SHORT).show();
        });
        rollDiceBtn.setOnClickListener((view) -> {
            Toast.makeText(GameActivity.this, "Roll dice pressed", Toast.LENGTH_SHORT).show();
        });
    }


    /**
     * Set up listeners for the dice ImageButtons.
     */
    private void setUpDiceListeners(){
        // set up listeners here.
    }
}