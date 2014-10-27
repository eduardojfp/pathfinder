package com.learn2crack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity  {
	Button saveProfile;
	EditText inputUsername;
	EditText inputAbout;
	EditText inputContact;
	TextView TimeJoined;
	TextView Games;
	TextView Teams;
	
	//some placeholder values
	String dateofJoin = "10/16/2014";
	String[] RecentGames = {"Game 1", "Game 2", "Game 3"}; 
	String[] RecentTeams = {"Team 1", "Team 2", "Team 3"}; 
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
	            //openProfile();
	            return true;
	        default:
	        	return super.onOptionsItemSelected(item);
        }
    }
	
	public void createGame() {
    	Intent intent = new Intent(this, CreateGameActivity.class);
    	startActivity(intent);
    }
	public void openSearch() {
    	Intent intent = new Intent(this, MainActivity.class);
    	startActivity(intent);
    }


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		inputUsername = (EditText) findViewById(R.id.userName);
		inputAbout = (EditText) findViewById(R.id.aboutText);
		inputContact = (EditText) findViewById(R.id.contactText);
		saveProfile = (Button) findViewById(R.id.Save);
		TimeJoined = (TextView) findViewById(R.id.time);
		Games = (TextView) findViewById(R.id.recentGamesDisplayed);
		Teams = (TextView) findViewById(R.id.teamsDisplay);
		
		//some database thing
		TimeJoined.setText(dateofJoin);
		Games.setText("");
		Teams.setText("");
		for(int i = 0; i < (Integer)RecentGames.length; i++){
			Games.append(RecentGames[i]);
			Games.append("\n");
		}
		for(int i = 0; i < (Integer)RecentTeams.length; i++){
			Teams.append(RecentTeams[i]);
			Teams.append("\n");
		}
		
		saveProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if  ( !inputUsername.getText().toString().equals(""))
                {
					//save some stuff to database
					
                } else if(inputUsername.getText().toString().equals("")) {
                	Toast.makeText(getApplicationContext(),
                            "Username is empty", Toast.LENGTH_SHORT).show();
                	
                }
				
			}});
		
		
		
	}
	
	
	
}
