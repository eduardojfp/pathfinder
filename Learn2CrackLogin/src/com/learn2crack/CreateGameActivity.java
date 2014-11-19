package com.learn2crack;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.app.DialogFragment;
import java.util.Calendar;
import android.app.Dialog;
import android.content.Intent;
import android.widget.TimePicker;
import android.text.format.DateFormat;

public class CreateGameActivity extends Activity {
	
	// User Input Fields
	EditText gameNameEditField;
	EditText taskNumField;
	Button next;
	
	// Stored Game Parameters
	String gameName;
	int numTask;
	boolean time_limit;
	static int hourStart;
	static int minStart;
	static int hourEnd;
	static int minEnd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game);
		
		// Create the text view
		//TextView textView = new TextView(this);
	    //textView.setTextSize(40);
	    
	    gameNameEditField = (EditText)findViewById(R.id.edit_message);
		taskNumField = (EditText)findViewById(R.id.task_num);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        /* Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        */
    	// Inflate the menu items for use in the action bar
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
        
    }
	
	
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()) {
	        case R.id.action_search:
	            openSearch();
	            return true;
	        case R.id.action_create:
	        	//createGame();
	        	return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        case R.id.action_profile:
	            openProfile();
	            return true;
	        case R.id.action_logout:
	        	openLogout();
	        	return true;
	        default:
	        	return super.onOptionsItemSelected(item);
        }
    }
	
	public void openSearch() {
    	Intent intent = new Intent(this, Search_Games.class);
    	startActivity(intent);
    }
	public void openProfile() {
    	Intent intent = new Intent(this, Profile.class);
    	startActivity(intent);
    }
	public void openLogout() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	
	public void onCheckBoxClicked(View view) {
		
		// Check to see if time limit box is checked
		// Assigns boolean value to time_limit
		boolean checked = ((CheckBox) view).isChecked();
		switch(view.getId()) {
		case R.id.time_limit_check:
			if(checked) {
				time_limit = true;
			}
			else time_limit = false;
		}
	}
	
	public static class StartTimePickerFragment extends DialogFragment
		implements TimePickerDialog.OnTimeSetListener {
		@Override
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	        // Use the current time as the default values for the picker
	        final Calendar c = Calendar.getInstance();
	        int hour = c.get(Calendar.HOUR_OF_DAY);
	        int minute = c.get(Calendar.MINUTE);

	        // Create a new instance of TimePickerDialog and return it
	        return new TimePickerDialog(getActivity(), this, hour, minute,
	                DateFormat.is24HourFormat(getActivity()));
	    }

	    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
	        // Do something with the time chosen by the user
	    	hourStart = hourOfDay;
	    	minStart = minute;
	    }
	}
	
	public void showStartTimePickerDialog(View v) {
		DialogFragment newFragment = new StartTimePickerFragment();
	    newFragment.show(getFragmentManager(), "timePicker");	
	}
	
	public static class EndTimePickerFragment extends DialogFragment
	implements TimePickerDialog.OnTimeSetListener {
	@Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
    	hourEnd = hourOfDay;
    	minEnd = minute;
    }
}

	public void showEndTimePickerDialog(View v) {
		// Check to see if time limit box is checked
		// Assigns boolean value to time_limit
		boolean checked = ((CheckBox) v).isChecked();
		switch(v.getId()) {
		case R.id.time_limit_check:
			if(checked) {
				time_limit = true;
				DialogFragment newFragment = new EndTimePickerFragment();
			    newFragment.show(getFragmentManager(), "timePicker");	
			}
			else time_limit = false;
		}
	}
}
