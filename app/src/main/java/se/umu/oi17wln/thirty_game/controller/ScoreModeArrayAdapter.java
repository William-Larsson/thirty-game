package se.umu.oi17wln.thirty_game.controller;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;

import java.util.ArrayList;

/**
 * Controller class for the main game screen and its
 * underlying logic.
 *
 * Author: William Larsson
 * CS-ID: oi17wln
 * Course: Development of mobile applications, 5DV209
 */
public class ScoreModeArrayAdapter<T> extends ArrayAdapter<T> {
    private ArrayList<Integer> disabledIndices;

    /**
     * Constructor, calls super and builds ArrayList.
     * @param context = where the adapter is created.
     * @param textViewResourceId = id of the layout resource to inflate
     * @param objects = the list of data objects to display.
     */
    public ScoreModeArrayAdapter(@NonNull Context context, int textViewResourceId, ArrayList<String> objects) {
        super(context, textViewResourceId, (T[]) objects.toArray());
        this.disabledIndices = new ArrayList<>();
    }


    /**
     * Returns true if item is not disabled.
     * @param index = index of the item
     * @return = true is enabled, false if disabled.
     */
    @Override
    public boolean isEnabled(int index){
        if(disabledIndices.contains(index)) {
            return false;
        }
        else {
            return true;
        }
    }


    /**
     * Custom drop down view which should set a faded
     * text color to disabled items.
     * @param index = position in the list
     * @param convertView =  idk really
     * @param parent = the view parent
     * @return = the view
     */
    @Override
    public View getDropDownView(int index, View convertView, ViewGroup parent) {
        View spinnerView = super.getDropDownView(index, convertView, parent);
        TextView spinnerTextView = (TextView) spinnerView;

        if(disabledIndices.contains(index)) {
            // Faded color for disabled item.
            spinnerTextView.setTextColor(Color.parseColor("#bcbcbb"));
        }
        else {
            spinnerTextView.setTextColor(Color.BLACK);
        }
        return spinnerView;
    }


    /**
     * Disables onClick for an index in the list
     * @param index = the index in the list to disable
     */
    public void disableItem(int index){
        disabledIndices.add(index);
    }
}
