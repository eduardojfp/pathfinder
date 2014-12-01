package com.learn2crack;
/*
 * Author: Megan Matiz
 */

//Import this stuff

import org.json.JSONException;
import org.json.JSONObject;
import com.learn2crack.library.UserFunctions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

/*
 * This class will display information for a single game
 * (game name, location, start and end times, etc)
 * Get to this activity by selecting a game
 */
public class gameDisplay extends Activity  {
	//names for stuff on the view -> used later
	TextView GameName;
	TextView Zipcode;
	TextView StartTime;
	TextView EndTime;
	TextView ErrorMsg;
	Button ViewTasks;
	Button JoinGame;
	Button buttonBack;
	
	//keys for accessing information from the database
	private static String KEY_SUCCESS = "success";
	private static String KEY_GAMENAME = "gname";
	private static String KEY_ZIPCODE = "location";
	private static String KEY_START_TIME = "starttime";
	private static String KEY_END_TIME = "endtime";
	
	//This is for the menu, press the menu key on your phone to see the menu
	public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);  
    }
	
	//These are the options for the menu
	public boolean onOptionsItemSelected(MenuItem item) {
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
	
	//The are the functions for the actions for the menu
	public void createGame() {
    	Intent intent = new Intent(this, CreateGameActivity.class);
    	startActivity(intent);
    }
	public void openSearch() {
    	Intent intent = new Intent(this, Search_Games.class);
    	startActivity(intent);
    }
	public void openProfile(){
		Intent intent = new Intent(this, Profile.class);
    	startActivity(intent);
	}
	public void openLogout() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	
	//Do this stuff
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//This says which XML file to use
		setContentView(R.layout.game_display);
		
		//Here's that stuff I said that I would use later.. attaching them to the ids in the XML file
		GameName = (TextView) findViewById(R.id.gameName);
		Zipcode = (TextView) findViewById(R.id.localGames);
		StartTime = (TextView) findViewById(R.id.startTime);
		EndTime = (TextView) findViewById(R.id.endTime);
		ViewTasks = (Button) findViewById(R.id.viewTasksButton);
		JoinGame = (Button) findViewById(R.id.join_game);
		buttonBack = (Button) findViewById(R.id.buttonBack);
		
		//Buttons!
		//Button that takes you to the tasks for the game
		ViewTasks.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(gameDisplay.this, GameTasks.class);
		    	startActivity(intent);
                }	
		});
		//Button that lets you join the game
		JoinGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
		  	    //do the stuff to join the user to the game
		  	    new ProcessJoin().execute();
		  	    
				//go to the success page
				Intent intent = new Intent(gameDisplay.this, JoinGame.class);
		    	startActivity(intent);
		    	
            }	
		});
		
		//Back to search games
		buttonBack.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				//go to the success page
				Intent intent = new Intent(gameDisplay.this, Search_Games.class);
		    	startActivity(intent);
		    	
            }	
		});
		
		//Now actually get the game data!
		new GetGameData().execute();
		
	}
	
	//Get the Game Data!
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		
		//Get the gid that was saved when you clicked on the game -> gonna use this to find the info for the right game
		SharedPreferences prefs = gameDisplay.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String savedgid = "com.example.app.gid";
  	    String gid = prefs.getString(savedgid, "none");
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
        	
        	//Here's where I use that gid to get the info for the right game.. uses a function in UserFunctions.java
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGameDataGid(gid);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
               if (json.getString(KEY_SUCCESS) != null) {

            	   //Now set those views declared earlier to display the information we got from the database
                    JSONObject json_user = json.getJSONObject("Game");
        			GameName.setText(json_user.getString(KEY_GAMENAME));
        			Zipcode.setText(json_user.getString(KEY_ZIPCODE));
        			StartTime.setText(json_user.getString(KEY_START_TIME));
        			EndTime.setText(json_user.getString(KEY_END_TIME));
                    }
               else{

                    }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
		
		
	}

	
    private class ProcessJoin extends AsyncTask<String, String, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
        	
    	//get uid
        	SharedPreferences prefs = gameDisplay.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
      	    String saveduid = "com.example.app.uid";
      	    String uid = prefs.getString(saveduid, "none");
        
        
        //get gid
        SharedPreferences prefs2 = gameDisplay.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String savedgid = "com.example.app.gid";
  	    String gid = prefs2.getString(savedgid, "none");

        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.joinGID(uid, gid);

        return json;
            


        }
       @Override
        protected void onPostExecute(JSONObject json) {

                }
            }


}