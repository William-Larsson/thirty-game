package se.umu.oi17wln.thirty_game.controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
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
import se.umu.oi17wln.thirty_game.model.Die;
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
    // Global variables
    private static final String SAVE_DICE = "se.umu.oi17wln.save_dice";
    private static final String SAVE_SCORE_MODES = "se.umu.oi17wln.save_score_modes";
    private static final String SAVE_LOGIC = "se.umu.oi17wln.save_logic";
    private static final String SAVE_TURNS = "se.umu.oi17wln.save_turns";
    private static final String SAVE_HAS_ROLLED = "se.umu.oi17wln.save_has_rolled";
    private static final int NUMBER_OF_DICE = 6;
    // Views
    private Button endTurnBtn;
    private Button rollDiceBtn;
    private TextView diceHelpText;
    private TextView scoreHelpText;
    private Spinner scoreModeSpinner;
    // State
    private ArrayList<ImageButton> diceButtons;
    private ArrayList<Die> dice;
    private ArrayList<String> availableScoreModes;
    private ArrayList<GameTurn> gameTurns;
    private GameLogic gameLogic;
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
        setUpViewInstances();
        setUpViewListeners();

        if (savedInstanceState != null) {
            dice = savedInstanceState.getParcelableArrayList(SAVE_DICE);
            gameTurns = savedInstanceState.getParcelableArrayList(SAVE_TURNS);
            gameLogic = savedInstanceState.getParcelable(SAVE_LOGIC);
            availableScoreModes = savedInstanceState.getStringArrayList(SAVE_SCORE_MODES);
            hasRolledDice = savedInstanceState.getByte(SAVE_HAS_ROLLED) != 0;
            setNewSpinnerAdapter();

            for (int i = 0; i < NUMBER_OF_DICE;  i++){
                setBackgroundForButton(diceButtons.get(i), i, dice.get(i).getValue());
            }
        } else {
            gameTurns = new ArrayList<>();
            setUpScoreModeSpinner();
            initNewGame();
        }

        if (!hasRolledDice) {
            endTurnBtn.setEnabled(false);
        }
    }


    /**
     * Save the current state, for example in case of
     * screen rotation.
     * @param outState = the bundle to state save to.
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_DICE, dice);
        outState.putParcelableArrayList(SAVE_TURNS, gameTurns);
        outState.putStringArrayList(SAVE_SCORE_MODES, availableScoreModes);
        outState.putParcelable(SAVE_LOGIC, gameLogic);
        outState.putByte(SAVE_HAS_ROLLED, (byte) (hasRolledDice ? 1 : 0));
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
        this.dice = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DICE; i++) dice.add(new Die());
    }


    /**
     * Rolls the dice, increments current throw,
     * updates die button background and disables
     * the roll button if all throws are used.
     */
    private void makeNewDiceRoll() {
        ArrayList<Integer> diceValues = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_DICE; i++) {
            diceValues.add(dice.get(i).roll());
        }

        gameLogic.incrementThrow();
        this.hasRolledDice = true;
        this.endTurnBtn.setEnabled(true);

        for (ImageButton btn : diceButtons) {
            int dieIndex = diceButtons.indexOf(btn);
            int dieValue = dice.get(dieIndex).getValue();
            setBackgroundForButton(btn, dieIndex, dieValue);
        }

        if (!gameLogic.canThrowAgain()){
            rollDiceBtn.setEnabled(false);
        }
    }


    /**
     * Toggle locked/unlocked state for dice buttons
     * @param view = instance of the button pressed
     */
    private void toggleDiceState(View view) {
        int dieIndex = diceButtons.indexOf(view);
        int dieValue = dice.get(dieIndex).getValue();

        dice.get(dieIndex).toogleLockedState();
        setBackgroundForButton(view, dieIndex, dieValue);
    }


    /**
     * Set background (dice image) for each unlocked die.
     * @param btn = the button to change background on
     * @param index = button index in array
     * @param value = the value of the button.
     */
    private void setBackgroundForButton(View btn, int index, int value){
        if (dice.get(index).isLocked()){
            btn.setBackgroundResource(this.getLockedDieBackgroundRes(value));
        } else btn.setBackgroundResource(this.getUnLockedDieBackgroundRes(value));
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
     * If used pressed End Turn, save the values of the
     * turn to a GameTurn object.
     * If not end of game, reset turn state
     * else call ResultActivity screen with the gameTurns data.
     */
    private void endOfGameTurn() {
        if (scoreModeSpinner.getSelectedItem() != null) {
            String scoreModeStr = scoreModeSpinner.getSelectedItem().toString();
            int scoreMode = scoreModeStringToInt(scoreModeStr);
            int turnScore= getTurnScore(scoreMode);

            gameTurns.add(new GameTurn(scoreModeStr, turnScore, gameLogic.getCurrentTurn()));
            removeChosenScoreMode();
            resetDiceLockedState();

            if (gameLogic.gameIsEnd()) {
                // start ResultActivity with an intent
                startActivity(ResultActivity.createIntent(this, gameTurns));
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
            Die die = dice.get(i);
            if (die.isLocked()) {
                die.toogleLockedState();
                setBackgroundForButton(diceButtons.get(i), i, die.getValue());
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

        for (int i = 0; i < NUMBER_OF_DICE; i++){
            if (dice.get(i).getValue() == 0) throw new IllegalStateException("Die value is zero");
            else finalDiceValues.add(dice.get(i).getValue());
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