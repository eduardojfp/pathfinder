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
import android.widget.LinearLayout;

public class Search_Games extends Activity  {
	EditText SearchInput;
	Button SearchButton;
	Button createGame;
	
	Button Games = new Button(this);
	LinearLayout ll = (LinearLayout)findViewById(R.id.localGames);
	ll.addView(Games, lp);
	
	private static String KEY_SUCCESS = "success";
	private static String KEY_ZIPCODE = "zipcode";
	
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
		SearchButton = (Button) findViewById(R.id.searchButton);
		SearchInput = (EditText) findViewById(R.id.searchLoc);
		Games = (TextView) findViewById(R.id.localGames);
		createGame = (Button) findViewById(R.id.create_game);
		
		createGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					createGame();	
                }
				
		});
		
		SearchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!SearchInput.getText().toString().equals(""))
				{
					Games.setText("");
					new GetGameData().execute();
				}
				else if(!SearchInput.getText().toString().equals(""))
				{
					Toast.makeText(getApplicationContext(),
                            "Search Field is empty", Toast.LENGTH_SHORT).show();
				}
				
                }
				
		});
		
	}
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

        	String location = SearchInput.getText().toString();
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGameData(location);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
               if (json.getString(KEY_SUCCESS) != null) {

            	   String res = json.getString(KEY_SUCCESS);

                    JSONObject json_user = json.getJSONObject("Game");
                    Games.append(json_user.getString("gname"));
                
            }
               else
               {
            	   Games.setText("No Games Found");
               }
               } catch (JSONException e) {
                e.printStackTrace();
            }
       }
		
		
	}
		
}
