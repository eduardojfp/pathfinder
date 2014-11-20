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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class Profile extends Activity  {
	LinearLayout lm; //master layout
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
	            //openSettings(); //no settings
	            return true;
	        case R.id.action_profile:
	            //openProfile(); //none for profile because already in it
	            return true;
	        case R.id.action_logout:
	        	openLogout();
	        	return true;
	        default:
	        	return super.onOptionsItemSelected(item);
        }
    }
	
	public void createGame() {
    	Intent intent = new Intent(this, CreateGameActivity.class); //go to create game
    	startActivity(intent);
    }
	public void openSearch() {
    	Intent intent = new Intent(this, Search_Games.class); //go to search
    	startActivity(intent);
    }

	public void openLogout() {
		Intent intent = new Intent(this, Login.class); //logout
		startActivity(intent);
	}

	public void onCreate(Bundle savedInstanceState) {
		
       
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);

		lm = (LinearLayout) findViewById(R.id.linear); //setlayout to be the same as current
		//create fields from xml
		inputUsername = (EditText) findViewById(R.id.userName);
		inputAbout = (EditText) findViewById(R.id.aboutText);
		inputContact = (EditText) findViewById(R.id.contactText);
		saveProfile = (Button) findViewById(R.id.Save);
		TimeJoined = (TextView) findViewById(R.id.time);
		
		//run fuctions to get data
		new GetUserData().execute();
		new GetGamesByUid().execute();
		
		
		saveProfile.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if  ( !inputUsername.getText().toString().equals(""))
                {
					//save some stuff to database

					new SetUserData().execute();
					
                } else if(inputUsername.getText().toString().equals("")) { //don't save
                	Toast.makeText(getApplicationContext(),
                            "Username is empty", Toast.LENGTH_SHORT).show();
                	
                }
				
			}});
		
		
		
	}
	OnClickListener btnClickListener = new OnClickListener() {
		@Override
	    public void onClick(View v) { //click on game will take you to the game
			Object test = v.getTag(); //get the id
			test.toString();
			//shared preference creates a variable that all fuctions can access
			SharedPreferences prefs = Profile.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
			String savegid = "com.example.app.gid";
			SharedPreferences.Editor editor = prefs.edit();
			String gid = test.toString(); //save the id to shared preference
			editor.putString(savegid, gid); 
			editor.commit(); 
			Intent intent = new Intent(Profile.this, GameTasks.class); //go to the game
	    	startActivity(intent);
			
		}
	};
	/////Gets Games by ID
	private class GetGamesByUid extends AsyncTask<String, String, JSONObject> { //goes into database and gets a list of games user is playing
		//get uid that was saved in login
		SharedPreferences prefs = Profile.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
  	    String saveduid = "com.example.app.uid";
  	    String uid = prefs.getString(saveduid, "none");

  	    LinearLayout a = new LinearLayout(Profile.this);

		@Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
        	
        	UserFunctions userFunction = new UserFunctions();
            JSONObject json = userFunction.getGamesByUid(uid); //to user fuctions
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	//creates a list of games user is playing
        	try {
        		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( //set params
    	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        	JSONObject json_user = json.getJSONObject("user"); 
	           a.setOrientation(LinearLayout.HORIZONTAL);
	           
	            TextView gamename = new TextView(Profile.this); //dynamic xml
	            gamename.setLayoutParams(params); 
	            gamename.setTag(json_user.getInt("gid")); //set id same as gid
	            gamename.setText(json_user.getString("gname")); //set the name
				gamename.setOnClickListener(btnClickListener); //add a listener
	            a.addView(gamename);
	            
            } catch (JSONException e) {
                e.printStackTrace();
            }

            lm.addView(a); 
        }
		
	}
	////Get user Data
	private class GetUserData extends AsyncTask<String, String, JSONObject> { //goes into database and gets user data
		
		//shared preference from profile
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
            JSONObject json = userFunction.getUserData(uid);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
        	//change the text fields based on what has been found in the database
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
        			if(uid == "none"){//testing sharedpref working
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
	private class SetUserData extends AsyncTask<String, String, JSONObject> { //goes into the database and changes user data based on changes made
		String username = inputUsername.getText().toString();
		String about = inputAbout.getText().toString();
		String contact = inputContact.getText().toString();
		//from login
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
        protected void onPostExecute(JSONObject json) { //message of success or failure
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
