package com.learn2crack;
/*
 * Author: Megan Matiz
 */

//Import this stuff
import org.json.JSONException;
import org.json.JSONObject;

import com.learn2crack.library.UserFunctions;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout;

/*
 * This class will be used to search for games by zip code
 * It will list the games, and the games will be clickable
 * Clicking on a game will take you to the game's display page
 * Or you can click on the button to go create a game
 * Enter Search Games from the menu
 */
public class Search_Games extends Activity  {
	//Stuff I need to define for later
	LinearLayout lm;
	EditText SearchInput;
	Button SearchButton;
	Button createGame;

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
	            //openSearch();
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
	public void openProfile() {
		Intent intent = new Intent (this, Profile.class);
		startActivity(intent);
	}
	public void openLogout() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	
	//Do this stuff
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_games);
		
		//A tricky outer layout so we can dynamically add stuff
	    lm = (LinearLayout) findViewById(R.id.linear);
  
	    //Here's that stuff I said that I would use later.. attaching them to the ids in the XML file
		SearchInput = (EditText) findViewById(R.id.searchLoc);
		SearchButton = (Button) findViewById(R.id.searchButton);
		createGame = (Button) findViewById(R.id.create_game);
		
		//Buttons!
		//Button that takes you to Create Game
		createGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					createGame();	
                }	
		});
		//Button to search for games located in the zip code entered by user
		SearchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!SearchInput.getText().toString().equals(""))
				{
					//Get the games in the zip code!
					new GetGameData().execute();
				}
					
				else if(!SearchInput.getText().toString().equals(""))
				{
					//No zip code to search!
					Toast.makeText(getApplicationContext(),
                            "Search Field is empty", Toast.LENGTH_SHORT).show();
				}
				
                }
				
		});	
	}
	
	/*This will make it so that you can click on the games 
	 * that get listed from the search and it will display the game
	 * information -> you have to save the gid so that the gameDisplay
	 * activity can use it
	 */
	OnClickListener btnClickListener = new OnClickListener() {
		@Override
	    public void onClick(View v) {
			Object test = v.getTag();
			test.toString();
			SharedPreferences prefs = Search_Games.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
			String savegid = "com.example.app.gid";
			SharedPreferences.Editor editor = prefs.edit();
			String gid = test.toString();
			editor.putString(savegid, gid);
			editor.commit();
			Intent intent = new Intent(Search_Games.this, gameDisplay.class);
	    	startActivity(intent);
			
		}
	};
	
	//Get the games in the zip code!
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		
		/*
		 * The zip code that the user typed in..
		 * Gonna use this to find the games by zip code
		 */
  	    String zip = SearchInput.getText().toString();
  	    
  	    /*
  	     * The second half of the trick so we can dynamically add stuff..
  	     * Basically going to add a view to this layout for each game
  	     */
  	    LinearLayout a = new LinearLayout(Search_Games.this);

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
        	
        	//Here's where I actually use the zip to get the game data
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGameData(zip);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	try {
        		//Setting the linear layout parameters
        		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        		
	        	JSONObject json_user = json.getJSONObject("Game"); 
	            a.setOrientation(LinearLayout.HORIZONTAL);
	            
	            //get ready to add game to view and make it clickable
	            TextView gameName = new TextView(Search_Games.this);
	            gameName.setLayoutParams(params);
	            gameName.setTag(json_user.getInt("gid"));
	            gameName.setText(json_user.getString("gname"));
	            gameName.setOnClickListener(btnClickListener);
	            a.addView(gameName);
	            
            } catch (JSONException e) {
                e.printStackTrace();
            }

        //add game to view
         lm.addView(a);
        }
		
	}

}