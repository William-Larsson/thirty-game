package se.umu.oi17wln.thirty_game.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;

import java.util.ArrayList;

import se.umu.oi17wln.thirty_game.R;
import se.umu.oi17wln.thirty_game.model.GameTurn;

/**
 * Controller class for the result screen.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class ResultActivity extends AppCompatActivity {
    // Global variables
    private static final String EXTRA_TURN_DATA = "se.umu.oi17wln.extra_turn_data";
    private static final String SAVE_TURN_DATA = "se.umu.oi17wln.save_turn_data";
    private static final String SAVE_FINAL_SCORE = "se.umu.oi17wln.save_final_score";
    // Views

    // State
    private ArrayList<GameTurn> gameTurns;
    private int finalScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        if (savedInstanceState != null) {
            gameTurns = savedInstanceState.getParcelableArrayList(SAVE_TURN_DATA);
            finalScore = savedInstanceState.getInt(SAVE_FINAL_SCORE);
        } else {
            gameTurns = getIntent().getParcelableArrayListExtra(EXTRA_TURN_DATA);
        }
    }


    /**
     * Used to pass data to this activity from the
     * GameActivity, such as the final score and the
     * values/scores for each turn.
     * @param packageContext = package context
     * @param turnData = data for each turn of the game.
     * @return = the intent.
     */
    public static Intent createIntent(Context packageContext, ArrayList<GameTurn> turnData) {
        Intent intent = new Intent(packageContext, ResultActivity.class);
        intent.putParcelableArrayListExtra(EXTRA_TURN_DATA, turnData);
        return intent;
    }


    /**
     * Save the current state, for example in case of
     * screen rotation.
     * @param outState = the bundle to state save to.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_TURN_DATA, gameTurns);
        outState.putInt(SAVE_FINAL_SCORE, finalScore);
    }
}