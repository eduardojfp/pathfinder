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

public class JoinGame extends Activity {
	//names for stuff on the view -> used later
		TextView gameJoined;
		Button backButton;
		
		//keys for accessing information from the database
		private static String KEY_SUCCESS = "success";
		private static String KEY_GAMENAME = "gname";
		
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
			setContentView(R.layout.join_game);
			
			/*
			 * Here's that stuff I said that I would use later.. 
			 * attaching them to the ids in the XML file
			*/
			gameJoined = (TextView) findViewById(R.id.gameJoined);
			backButton = (Button) findViewById(R.id.backButton);
			
			//Back Button
			backButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
	
					Intent intent = new Intent(JoinGame.this, gameDisplay.class);
			    	startActivity(intent);
	                }	
			});
			
			//Get the game data
			new GetGameData().execute();
		}
		
		//Get the Game Data!
		private class GetGameData extends AsyncTask<String, String, JSONObject> {
			
			//Get the gid that was saved when you clicked on the game -> gonna use this to find the info for the right game
			SharedPreferences prefs2 = JoinGame.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
	  	    String savedgid = "com.example.app.gid";
	  	    String gid = prefs2.getString(savedgid, "none");

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
	        			gameJoined.setText(json_user.getString(KEY_GAMENAME));
	                    }
	               else{

	                    }
	                
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	       }
				
				
			}

}