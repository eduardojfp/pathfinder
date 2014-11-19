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

public class Profile extends Activity  {
	Button saveProfile;
	EditText inputUsername;
	EditText inputAbout;
	EditText inputContact;
	TextView TimeJoined;
	TextView Games;
	TextView Teams;
	
    private static String KEY_SUCCESS = "success";
    private static String KEY_USERNAME = "uname";
    private static String KEY_CREATED_AT = "created_at";
    private static String KEY_ABOUT = "about";
    private static String KEY_CONTACT = "contact";
	
	//some placeholder values
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
		
       // json.getString(KEY_FIRSTNAME);
        //json.getString(KEY_LASTNAME);
        //json.getString(KEY_EMAIL);
        //json.getString(KEY_USERNAME);
        //json.getString(KEY_UID);
      //  json.getString(KEY_CREATED_AT);
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
		new GetUserData().execute();
		
		/*String uid = "13";
		UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.getUserData(uid);
        
        try {
			JSONObject json_user = json.getJSONObject("user");
			inputUsername.setText(json_user.getString(KEY_USERNAME));
			TimeJoined.setText(json_user.getString(KEY_CREATED_AT));
			inputAbout.setText(json_user.getString(KEY_ABOUT));
			inputContact.setText(json_user.getString(KEY_CONTACT));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		saveProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if  ( !inputUsername.getText().toString().equals(""))
                {
					//save some stuff to database

					new SetUserData().execute();
					
                } else if(inputUsername.getText().toString().equals("")) {
                	Toast.makeText(getApplicationContext(),
                            "Username is empty", Toast.LENGTH_SHORT).show();
                	
                }
				
			}});
		
		
		
	}
	private class GetUserData extends AsyncTask<String, String, JSONObject> {
		
		
		SharedPreferences prefs = Profile.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String saveduid = "com.example.app.uid";
  	    String uid = prefs.getString(saveduid, "none");
  	    //String uid = "13";

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


                    JSONObject json_user = json.getJSONObject("user");
        			inputUsername.setText(json_user.getString(KEY_USERNAME));
        			TimeJoined.setText(json_user.getString(KEY_CREATED_AT));
        			if(json_user.getString(KEY_ABOUT) != "null"){
        				inputAbout.setText(json_user.getString(KEY_ABOUT));
        			}
        			if(json_user.getString(KEY_CONTACT) != "null"){
        				inputContact.setText(json_user.getString(KEY_CONTACT));
        			}
        			if(uid == "none"){
        				inputUsername.setText("Saved Did not work");
        			}
        			
                    }
               else{

                    }
                
            } catch (JSONException e) {
                e.printStackTrace();
            }
       }
		
		
	}
	private class SetUserData extends AsyncTask<String, String, JSONObject> {
		String username = inputUsername.getText().toString();
		String about = inputAbout.getText().toString();
		String contact = inputContact.getText().toString();
		
		SharedPreferences prefs = Profile.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String saveduid = "com.example.app.uid";
  	    String uid = prefs.getString(saveduid, "none");

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.setUserData(uid, username, about, contact);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	try {
                if (json.getString(KEY_SUCCESS) != null) {
                	Toast.makeText(getApplicationContext(),"Saved", Toast.LENGTH_SHORT).show();
                }else{
                	Toast.makeText(getApplicationContext(),"Save Unsucessful", Toast.LENGTH_SHORT).show();
                }
        	}catch (JSONException e) {
                e.printStackTrace();
            }
        }
		
	}
		
}
