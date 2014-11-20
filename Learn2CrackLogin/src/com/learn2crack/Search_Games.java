package com.learn2crack;

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

public class Search_Games extends Activity  {
	LinearLayout lm;
	EditText SearchInput;
	Button SearchButton;
	Button createGame;

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
		Intent intent = new Intent (this, Profile.class);
		startActivity(intent);
	}

	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_games);
		
	    lm = (LinearLayout) findViewById(R.id.linear);

   	    
        
        // create the layout params that will be used to define how your
        // button will be displayed
        //LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
           //     LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
         
		
		SearchInput = (EditText) findViewById(R.id.searchLoc);
		SearchButton = (Button) findViewById(R.id.searchButton);
		createGame = (Button) findViewById(R.id.create_game);
		
		//buttons
		createGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					createGame();	
                }	
		});
		
		SearchButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if(!SearchInput.getText().toString().equals(""))
				{
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
	
	private class GetGameData extends AsyncTask<String, String, JSONObject> {
		//some variables
  	    String gid = SearchInput.getText().toString();

  	    LinearLayout a = new LinearLayout(Search_Games.this);

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGameData(gid);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	try {
        		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
    	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        		
	        	JSONObject json_user = json.getJSONObject("Game"); 
	            a.setOrientation(LinearLayout.HORIZONTAL);
	            
	        	///save game id
	            /*
	        	   SharedPreferences prefs = Search_Games.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
            	   String savedGame = "com.example.app.gid";
            	   SharedPreferences.Editor editor = prefs.edit();
            	   String gid = json_user.getString("gid");
            	   editor.putString(savedGame, gid);
            	   editor.commit();
            	   */
	            
	            
	            //add game
	            TextView gameName = new TextView(Search_Games.this);
	            gameName.setLayoutParams(params);
	            gameName.setTag(json_user.getInt("gid"));
	            gameName.setText(json_user.getString("gname"));
	            gameName.setOnClickListener(btnClickListener);
	            a.addView(gameName);
	            
            } catch (JSONException e) {
                e.printStackTrace();
            }

         lm.addView(a);
        }
		
	}

}