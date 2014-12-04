package com.learn2crack;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.Button;
import android.view.View;
import android.app.DialogFragment;
import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import com.learn2crack.library.UserFunctions;

import android.app.Dialog;
import android.content.Intent;
import android.widget.TimePicker;
import android.text.format.DateFormat;

public class CreateGameActivity extends Activity {
	
	private static String KEY_SUCCESS = "success";
	
	// User Input Fields
	EditText gameNameEditField;
	EditText taskNumField;
	Button next;
	TextView ErrorMsg;
	
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
		ErrorMsg = (TextView) findViewById(R.id.register_error);
		
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
		
		new ProcessGame();
	}
	
	 private class ProcessGame extends AsyncTask<String, String, JSONObject> {
		         @Override
		         protected void onPreExecute() {
		             super.onPreExecute();
		             /*
		             inputUsername = (EditText) findViewById(R.id.uname);
		             inputPassword = (EditText) findViewById(R.id.pword);
		                fname = inputFirstName.getText().toString();
		                lname = inputLastName.getText().toString();
		                 email = inputEmail.getText().toString();
		                 uname= inputUsername.getText().toString();
		                 uzip = inputZipcode.getText().toString();
		                 password = inputPassword.getText().toString();
		                 */
		         }

		         @Override
		         protected JSONObject doInBackground(String... args) {


		         UserFunctions userFunction = new UserFunctions();
		         JSONObject json = userFunction.processGame(gameName, numTask,
		        		 time_limit, hourStart, minStart, hourEnd, minEnd);

		             return json;
		         }
		        @Override
		         protected void onPostExecute(JSONObject json) {
		                 try {
		                     if (json.getString(KEY_SUCCESS) != null) {
		                         ErrorMsg.setText("");
		                         String res = json.getString(KEY_SUCCESS);

		                         if(Integer.parseInt(res) == 1){
		                             ErrorMsg.setText("Successfully Created Game");
////////////////////
		                             //GO TO SUCCESS PAGE
		                             //Intent registered = new Intent(getApplicationContext(), Registered.class);
////////////////////////
		                               finish();
		                         }
		                     }
		                         else{
		                             ErrorMsg.setText("There was a problem creating the game!");
		                         }

		                 } catch (JSONException e) {
		                     e.printStackTrace();
		                 }
		             }}
}
