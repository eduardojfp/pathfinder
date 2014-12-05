package com.learn2crack;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import com.learn2crack.library.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateTasks extends Activity {
	EditText first;
	EditText second;
	EditText third;
	EditText fourth;
	EditText fifth;
	
	// Containers for all information to be sent to database.
	String taskFirst;
	String taskSecond;
	String taskThird;
	String taskFourth;
	String taskFifth;
	Intent intent;
	String gameName;
	String zipCode;
	Calendar gameDate;
	
	String year;
	String month;
	String day;
	String hourStart;
	String minStart;
	String hourEnd;
	String minEnd;
	
	Button next;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_tasks);
		
		// Task containers
		first = (EditText)findViewById(R.id.first);
		second = (EditText)findViewById(R.id.second);
		third = (EditText)findViewById(R.id.third);
		fourth = (EditText)findViewById(R.id.fourth);
		fifth = (EditText)findViewById(R.id.fifth);
		
		// Finalize button
		next = (Button)findViewById(R.id.next_button);
		
		// Retrieve information to be sent to the database
		intent = getIntent();
		Bundle extras = intent.getExtras();
		gameName = extras.getString("game_name");
		zipCode = extras.getString("zipCode");
		year = Integer.toString(extras.getInt("year", 2014));
		month = Integer.toString(extras.getInt("month", 12));
		day = Integer.toString(extras.getInt("day", 15));
		hourStart = Integer.toString(extras.getInt("hourStart"));
		minStart = Integer.toString(extras.getInt("minStart"));
		hourEnd = Integer.toString(extras.getInt("hourEnd", 23));
		minEnd = Integer.toString(extras.getInt("minEnd", 59));
		
		
		
		
		//gameDate = Calendar.getInstance();
		//gameDate.set(Calendar.YEAR, year);
		//gameDate.set(Calendar.MONTH, month);
		//gameDate.set(Calendar.DAY_OF_MONTH, day);
		//Date date = gameDate.getTime();
		//System.out.println(gameDate.getTime());
		//System.out.println(hourStart + ":" + minStart);
		//System.out.println(hourEnd + ":" + minEnd);
		
		//System.out.println(year);
		//System.out.println(month);
		//System.out.println(day);
		
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
	        	createGame();
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
	public void createGame() {
    	Intent intent = new Intent(this, CreateGameActivity.class);
    	startActivity(intent);
    }
	public void finalizeGame(View view) {
		taskFirst = first.getText().toString();
		taskSecond = second.getText().toString();
		taskThird = third.getText().toString();
		taskFourth = fourth.getText().toString();
		taskFifth = fifth.getText().toString();	
		
		UserFunctions userFunction = new UserFunctions();
		String num_task ="5";
		userFunction.processGame(gameName, num_task, hourStart, minStart, hourEnd, minEnd, year, month, day);
	}
	
	

}
