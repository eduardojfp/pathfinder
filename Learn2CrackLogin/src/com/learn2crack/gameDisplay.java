package com.learn2crack;

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

public class gameDisplay extends Activity  {
	TextView GameName;
	TextView Zipcode;
	TextView StartTime;
	TextView EndTime;
	Button ViewTasks;
	Button JoinGame;
	
	private static String KEY_SUCCESS = "success";
	private static String KEY_GAMENAME = "gname";
	private static String KEY_ZIPCODE = "location";
	private static String KEY_START_TIME = "starttime";
	private static String KEY_END_TIME = "endtime";
	
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
	        case R.id.action_logout:
	        	openLogout();
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
    	Intent intent = new Intent(this, Search_Games.class);
    	startActivity(intent);
    }
	public void openLogout() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_display);
		GameName = (TextView) findViewById(R.id.gameName);
		Zipcode = (TextView) findViewById(R.id.localGames);
		StartTime = (TextView) findViewById(R.id.startTime);
		EndTime = (TextView) findViewById(R.id.endTime);
		ViewTasks = (Button) findViewById(R.id.viewTasksButton);
		JoinGame = (Button) findViewById(R.id.join_game);
		
		//buttons
		ViewTasks.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					new GameTasks();
                }	
		});
		JoinGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					//TODO: add player to game	
                }	
		});

		new GetGameData().execute();		
	}
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		
		SharedPreferences prefs = gameDisplay.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String savedgid = "com.example.app.gid";
  	    String gid = prefs.getString(savedgid, "none");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGameDataGid(gid);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
               if (json.getString(KEY_SUCCESS) != null) {


                    JSONObject json_user = json.getJSONObject("Game");
        			GameName.setText(json_user.getString(KEY_GAMENAME));
        			//Zipcode.setText(json_user.getString(KEY_ZIPCODE));
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
}