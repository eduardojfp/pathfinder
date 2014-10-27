package com.learn2crack;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
		TimeJoined.setText("dateofJoin");
		Games.setText("");
		Teams.setText("");
		for(int i = 0; i < RecentGames.length(); i++){
			
		}
		
		
		
	}
	
	
}
