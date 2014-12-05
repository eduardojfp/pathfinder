package com.learn2crack;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
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
import java.util.Date;
import android.app.Dialog;
import android.content.Intent;
import android.widget.TimePicker;
import android.text.Editable;
import android.text.format.DateFormat;

public class CreateGameActivity extends Activity {
	
	// User Input Fields
	EditText gameNameEditField;
	EditText zipCodeField;
	//EditText taskNumField;
	Spinner spinner;
	Button next;
	TextView ErrorMsg;
	
	// Stored Game Parameters (with default values)
	String gameName = "Your Game";
	int numTask = 5;
	int zipCode = 12180;
	boolean time_limit;
	static int hourStart = 0;
	static int minStart = 0;
	static int hourEnd = 22;
	static int minEnd = 58;
	static Calendar gameDate = Calendar.getInstance();
	static int gameYear = 1990;
	static int gameMonth = 11;
	static int gameDay = 25;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_game);
		
		// Create the text view
		//TextView textView = new TextView(this);
	    //textView.setTextSize(40);
	    
	    gameNameEditField = (EditText)findViewById(R.id.edit_message);
	    zipCodeField = (EditText)findViewById(R.id.zip_code);
		//taskNumField = (EditText)findViewById(R.id.task_num);
		spinner = (Spinner)findViewById(R.id.task_num);
		ErrorMsg = (TextView) findViewById(R.id.register_error);
		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.task_num_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(adapter);
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
	public void createTasks(View view) {
		if( ( !zipCodeField.getText().toString().equals("") )) {
			System.out.println("Inside Create Task Starter");
			gameName = gameNameEditField.getText().toString();
			zipCode = Integer.parseInt(zipCodeField.getText().toString());
			Intent intent = new Intent(this, CreateTasks.class);
			Bundle extras = new Bundle();
			extras.putString("game_name", gameName);
			extras.putInt("zipCode", zipCode);
			extras.putInt("year", gameYear);
			extras.putInt("month", gameMonth);
			extras.putInt("day", gameDay);
			extras.putInt("hourStart", hourStart);
			extras.putInt("minStart", minStart);
			extras.putInt("hourEnd", hourEnd);
			extras.putInt("minEnd", minEnd);
			intent.putExtras(extras);
			startActivity(intent);
		}
		else{
			//ErrorMsg.setText("Fill in all fields!");
		}
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
	
	public static class DatePickerFragment extends DialogFragment
    implements DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);
			
			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}
		
		@SuppressWarnings("deprecation")
		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user
			int updatedMonth = month+1;
			gameYear = year;
			gameMonth = month;
			gameDay = day;
			/*gameDate = Calendar.getInstance();
			gameDate.set(Calendar.YEAR, year);
			gameDate.set(Calendar.MONTH, month);
			gameDate.set(Calendar.DAY_OF_MONTH, day);
			*/
			//System.out.println(gameDate.getTime());
			/*gameDate = new Date(year, updatedMonth, day);
			System.out.println(year + ":" + updatedMonth + ":" + day);
			System.out.println(gameDate.getYear() + ":" + gameDate.getMonth() + ":" + gameDate.getDay());
			*/
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
	
	public void showDatePickerDialog(View v) {
		// User selects a Date for their event
		DialogFragment newFragment = new DatePickerFragment();
	    newFragment.show(getFragmentManager(), "datePicker");	
	}
}

