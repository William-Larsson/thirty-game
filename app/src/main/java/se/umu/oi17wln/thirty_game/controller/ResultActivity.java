package se.umu.oi17wln.thirty_game.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import se.umu.oi17wln.thirty_game.R;
import se.umu.oi17wln.thirty_game.model.GameLogic;
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
    private TextView finalScoreView;
    private ArrayList<TextView> turnViews;
    private Button newGameBtn;
    // State
    private ArrayList<GameTurn> gameTurns;
    private int finalScore;


    /**
     * Set up all the things needed in state when this activity
     * is created, e.g Views, listeners etc.
     * If previous state exists, use old values instead of new
     *
     * @param savedInstanceState = previously saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.turnViews = new ArrayList<>();
        setUpViewInstances();
        setUpViewListeners();

        if (savedInstanceState != null) {
            gameTurns = savedInstanceState.getParcelableArrayList(SAVE_TURN_DATA);
            finalScore = savedInstanceState.getInt(SAVE_FINAL_SCORE);
        } else {
            gameTurns = getIntent().getParcelableArrayListExtra(EXTRA_TURN_DATA);
            setFinalScore();
        }

        setTextOnUIViews();
    }


    /**
     * Used to pass GameTurn to this activity from the
     * GameActivity.
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


    /**
     * Set up all view instances on the results screen.
     */
    private void setUpViewInstances() {
        this.finalScoreView = findViewById(R.id.final_score_value);
        this.newGameBtn = findViewById(R.id.new_game_btn);
        this.turnViews.add(findViewById(R.id.turn_1));
        this.turnViews.add(findViewById(R.id.turn_2));
        this.turnViews.add(findViewById(R.id.turn_3));
        this.turnViews.add(findViewById(R.id.turn_4));
        this.turnViews.add(findViewById(R.id.turn_5));
        this.turnViews.add(findViewById(R.id.turn_6));
        this.turnViews.add(findViewById(R.id.turn_7));
        this.turnViews.add(findViewById(R.id.turn_8));
        this.turnViews.add(findViewById(R.id.turn_9));
        this.turnViews.add(findViewById(R.id.turn_10));
    }


    /**
     * Set up listeners for the buttons used to
     * interact with the result screen.
     */
    private void setUpViewListeners() {
        this.newGameBtn.setOnClickListener((view) -> startNewGame());
    }


    /**
     * Sets the final score by calling GameLogic to
     * sum the scores for each turn.
     */
    private void setFinalScore() {
        GameLogic gameLogic = new GameLogic();
        ArrayList<Integer> scores = new ArrayList<>();

        for (GameTurn turn : gameTurns) {
            scores.add(turn.getTurnScore());
        }

        this.finalScore = gameLogic.calcSum(scores);
    }


    /**
     * "Prints" results to the screen.
     */
    private void setTextOnUIViews() {
        finalScoreView.setText(finalScore + " points");

        if (gameTurns.size() == turnViews.size()){
            for (int i = 0; i < gameTurns.size(); i++) {
                GameTurn turn = gameTurns.get(i);
                String output = "Turn " + turn.getTurnNr() + ":    "
                        + turn.getTurnScore() + " points     ("
                        + turn.getScoreMode() + ")";
                turnViews.get(i).setText(output);
            }
        } else {
            throw new IllegalStateException("The nr of turns do not match the nr of text views.");
        }
    }


    /**
     * Start a new game by calling GameActivity.
     */
    private void startNewGame() {
        startActivity(GameActivity.startNewGameIntent(this));
    }
}