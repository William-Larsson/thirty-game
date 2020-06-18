package se.umu.oi17wln.thirty_game.controller;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import se.umu.oi17wln.thirty_game.R;
import se.umu.oi17wln.thirty_game.model.Dice;
import se.umu.oi17wln.thirty_game.model.GameLogic;
import se.umu.oi17wln.thirty_game.model.GameTurn;
import se.umu.oi17wln.thirty_game.model.ScoreMode;

/**
 * Controller class for the main game screen and its
 * underlying logic.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class GameActivity extends AppCompatActivity {
    // Views
    private Button endTurnBtn;
    private Button rollDiceBtn;
    private TextView diceHelpText;
    private TextView scoreHelpText;
    private Spinner scoreModeSpinner;
    // State
    private ArrayList<ImageButton> diceButtons;
    private ArrayList<String> availableScoreModes;
    private ArrayList<GameTurn> gameTurns;
    private GameLogic gameLogic;
    private Dice dice;
    private boolean hasRolledDice;

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
        gameTurns = new ArrayList<>();

        setUpViewInstances();
        setUpViewListeners();
        setUpScoreModeSpinner();

        // TODO: will this need changing to fix rotate resetting?
        initNewGame();
        endTurnBtn.setEnabled(false);
    }


    /**
     * Set up all view instances on the game screen.
     */
    private void setUpViewInstances(){
        // Gameplay UI
        endTurnBtn = findViewById(R.id.end_turn_btn);
        rollDiceBtn = findViewById(R.id.roll_dice_btn);
        scoreModeSpinner = findViewById(R.id.score_Spinner);
        // Text
        diceHelpText = findViewById(R.id.dice_lock_help);
        scoreHelpText = findViewById(R.id.score_mode_help);
        // Dice buttons
        diceButtons.add(findViewById(R.id.dice_button_0));
        diceButtons.add(findViewById(R.id.dice_button_1));
        diceButtons.add(findViewById(R.id.dice_Button_2));
        diceButtons.add(findViewById(R.id.dice_button_3));
        diceButtons.add(findViewById(R.id.dice_button_4));
        diceButtons.add(findViewById(R.id.dice_button_5));
    }


    /**
     * Set up listeners for the buttons used to
     * play the game
     */
    private void setUpViewListeners(){
        endTurnBtn.setOnClickListener((view) -> endOfGameTurn());
        rollDiceBtn.setOnClickListener((view) -> makeNewDiceRoll());

        for (ImageButton btn : diceButtons){
            btn.setOnClickListener((view) -> {
                if (hasRolledDice) {
                    toggleDiceState(view);
                } else {
                    Toast.makeText(this, "Roll dice first", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * Set up a new spinner object where every Score Mode is
     * available to choose.
     */
    private void setUpScoreModeSpinner(){
        this.availableScoreModes = new ArrayList<>(Arrays.asList(
                "LOW", "FOUR", "FIVE", "SIX", "SEVEN",
                "EIGHT", "NINE", "TEN", "ELEVEN", "TWELVE")
        );
        setNewSpinnerAdapter();
    }


    /**
     * Sets a new adapter for the Score Mode spinner.
     */
    private void setNewSpinnerAdapter(){
        ArrayAdapter<String> spinnerAdapter = new ScoreModeArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                this.availableScoreModes
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.scoreModeSpinner.setAdapter(spinnerAdapter);
    }


    /**
     * Initialize a new game.
     */
    private void initNewGame(){
        this.gameLogic = new GameLogic();
        this.dice = new Dice();
    }


    /**
     * Toggle locked/unlocked state for dice buttons
     * @param view = instance of the button pressed
     */
    private void toggleDiceState(View view) {
        int dieIndex = diceButtons.indexOf(view);
        int dieValue = dice.getDieValue(dieIndex);

        dice.toggleDieLockOnIndex(dieIndex);
        setBackgroundForButton(view, dieIndex, dieValue);
    }


    /**
     * Get resource ID for locked state die backgrounds.
     * @param dieValue = the value of the die.
     * @return = the background resource ID.
     */
    private int getLockedDieBackgroundRes(int dieValue){
        switch (dieValue){
            case 1: return R.mipmap.grey1;
            case 2: return R.mipmap.grey2;
            case 3: return R.mipmap.grey3;
            case 4: return R.mipmap.grey4;
            case 6: return R.mipmap.grey6;
            default: return R.mipmap.grey5;
        }
    }


    /**
     * Get resource ID for unlocked state die backgrounds.
     * @param dieValue = the value of the die.
     * @return = the background resource ID.
     */
    private int getUnLockedDieBackgroundRes(int dieValue){
        switch (dieValue){
            case 1: return R.mipmap.white1;
            case 2: return R.mipmap.white2;
            case 3: return R.mipmap.white3;
            case 4: return R.mipmap.white4;
            case 6: return R.mipmap.white6;
            default: return R.mipmap.white5;
        }
    }


    /**
     * Rolls the dice, increments current throw,
     * updates die button background and disables
     * the roll button if all throws are used.
     */
    private void makeNewDiceRoll() {
        ArrayList<Integer> diceValues = dice.roll();
        gameLogic.incrementThrow();
        this.hasRolledDice = true;
        this.endTurnBtn.setEnabled(true);

        for (ImageButton btn : diceButtons) {
            int dieIndex = diceButtons.indexOf(btn);
            int dieValue = dice.getDieValue(dieIndex);
            setBackgroundForButton(btn, dieIndex, dieValue);
        }

        if (!gameLogic.canThrowAgain()){
            rollDiceBtn.setEnabled(false);
        }
    }


    /**
     * Set background (dice image) for each unlocked die.
     * @param btn = the button to change background on
     * @param index = button index in array
     * @param value = the value of the button.
     */
    private void setBackgroundForButton(View btn, int index, int value){
        if (dice.dieIsLocked(index)){
            btn.setBackgroundResource(this.getLockedDieBackgroundRes(value));
        } else btn.setBackgroundResource(this.getUnLockedDieBackgroundRes(value));
    }


    /**
     * ...
     */
    private void endOfGameTurn() {
        if (scoreModeSpinner.getSelectedItem() != null) {
            String scoreModeStr = scoreModeSpinner.getSelectedItem().toString();
            int scoreMode = scoreModeStringToInt(scoreModeStr);
            int turnScore= getTurnScore(scoreMode);

            Toast.makeText(this, "Score: " + turnScore , Toast.LENGTH_SHORT).show();

            gameTurns.add(new GameTurn(scoreModeStr, turnScore, gameLogic.getCurrentTurn()));
            removeChosenScoreMode();
            resetDiceLockedState();

            if (gameLogic.gameIsEnd()) {
                // game is over
                // Todo: calc total score, open results activity


                ArrayList<Integer> scores = new ArrayList<>();
                for (GameTurn turn : gameTurns){
                    scores.add(turn.getTurnScore());
                }
                int totalScore = gameLogic.calcSum(scores);


                Toast.makeText(this, "Game Over, Total Score: " + totalScore, Toast.LENGTH_SHORT).show();


                endTurnBtn.setEnabled(false);
                rollDiceBtn.setEnabled(false);
            } else {
                endTurnBtn.setEnabled(false);
                rollDiceBtn.setEnabled(true);
                gameLogic.beginNewTurn();
                hasRolledDice = false;
            }
        } else throw new IllegalStateException("Score mode spinner has no selected value");
    }


    /**
     * Remove chosen Score Mode from the available options and
     * set new ArrayAdapter for spinner to take it into effect.
     */
    private void removeChosenScoreMode(){
        availableScoreModes.remove(scoreModeSpinner.getSelectedItemPosition());
        setNewSpinnerAdapter();
    }


    /**
     * Reset dice button locked state after a turn.
     */
    private void resetDiceLockedState(){
        for (int i = 0; i < diceButtons.size(); i++) {
            if (dice.dieIsLocked(i)) {
                dice.toggleDieLockOnIndex(i);
                setBackgroundForButton(diceButtons.get(i), i, dice.getDieValue(i));
            }
        }
    }


    /**
     * Get the score for the current turn of the game.
     * @return = the score.
     * @throws IllegalStateException = if a die value is zero.
     */
    private int getTurnScore(int scoreMode) throws IllegalStateException {
        ArrayList<Integer> finalDiceValues = new ArrayList<>();

        for (int i = 0; i < diceButtons.size(); i++){
            if (dice.getDieValue(i) == 0) throw new IllegalStateException("Die value is zero");
            else finalDiceValues.add(dice.getDieValue(i));
        }

        return gameLogic.calcTurnScore(finalDiceValues, scoreMode);
    }


    /**
     * Convert spinner list item name to its corresponding
     * integer value.
     * @param scoreMode = score mode as string
     * @return = the corresponding numerical value.
     */
    private int scoreModeStringToInt(String scoreMode){
        switch (scoreMode) {
            case "FOUR": return ScoreMode.FOUR;
            case "FIVE": return ScoreMode.FIVE;
            case "SIX": return ScoreMode.SIX;
            case "SEVEN": return ScoreMode.SEVEN;
            case "EIGHT": return ScoreMode.EIGHT;
            case "NINE": return ScoreMode.NINE;
            case "TEN": return ScoreMode.TEN;
            case "ELEVEN": return ScoreMode.ELEVEN;
            case "TWELVE": return ScoreMode.TWELVE;
            default: return ScoreMode.LOW;
        }
    }
}