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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Search_Games extends Activity  {
	TextView Games;
	Button createGame;
	
	private static String KEY_SUCCESS = "success";
	private static String KEY_ZIPCODE = "uzip";
	
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
	
	public void createGame() {
    	Intent intent = new Intent(this, CreateGameActivity.class);
    	startActivity(intent);
    }
	
	public void openLogout() {
		Intent intent = new Intent(this, Login.class);
		startActivity(intent);
	}
	
	public void openProfile() {
		Intent intent = new Intent (this, Login.class);
		startActivity(intent);
	}

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_games);
		Games = (TextView) findViewById(R.id.localGames);
		createGame = (Button) findViewById(R.id.create_game);
		
		new GetGameData().execute();
		
		
		createGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					createGame();	
                }
				
		});
		
	}
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		
		
		SharedPreferences prefs = Search_Games.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String saveduid = "com.example.app.uid";
  	    String uid = prefs.getString(saveduid, "none");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getUserData(uid);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
               if (json.getString(KEY_SUCCESS) != null) {


                    JSONObject json_user = json.getJSONObject("Games");
        			Games.setText(json_user.getString(KEY_ZIPCODE));
        			if(uid == "none"){
        				Games.setText("No Games Found");
        			}
        			
                    }
               else{

                    }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
		
		
	}
		
}
