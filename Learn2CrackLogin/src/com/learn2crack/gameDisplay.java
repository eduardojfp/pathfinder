package com.learn2crack;
/*
 * Author: Megan Matiz
 */

//Import this stuff
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.learn2crack.library.DatabaseHandler;
import com.learn2crack.library.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
	
	//keys for accessing information from the database
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
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
		
		//Buttons!
		//Button that takes you to the tasks for the game
		ViewTasks.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
					new GameTasks();
                }	
		});
		//Button that lets you join the game
		JoinGame.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				
				//Get the gid that was saved when you clicked on the game -> gonna use this to find the info for the right game
				SharedPreferences prefs = gameDisplay.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
		  	    String savedgid = "com.example.app.gid";
		  	    String gid = prefs.getString(savedgid, "none");
		  	    
		  	    //do the stuff to join the user to the game
		  	    NetAsync(v);
		  	    
		  	    //save the gid
		  	    String savegid = "com.example.app.gid";
				SharedPreferences.Editor editor = prefs.edit();
				editor.putString(savegid, gid);
				editor.commit();
				//go to the success page
				Intent intent = new Intent(gameDisplay.this, JoinGame.class);
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

	private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(gameDisplay.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
                ErrorMsg.setText("Error in Network Connection");
            }
        }
    }

    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

/**
 * Defining Process dialog
 **/
        private ProgressDialog pDialog;

        String email,password,fname,lname,uname,uzip;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /*
            inputUsername = (EditText) findViewById(R.id.uname);
            inputPassword = (EditText) findViewById(R.id.pword);
               fname = inputFirstName.getText().toString();
               lname = inputLastName.getText().toString();
                email = inputEmail.getText().toString();
                uname= inputUsername.getText().toString();
                uzip = inputZipcode.getText().toString();
                password = inputPassword.getText().toString();
                */
            pDialog = new ProgressDialog(gameDisplay.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Registering ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {


        UserFunctions userFunction = new UserFunctions();
        JSONObject json = userFunction.registerUser(fname, lname, email, uname, uzip, password);

            return json;


        }
       @Override
        protected void onPostExecute(JSONObject json) {
       /**
        * Checks for success message.
        **/
                try {
                    if (json.getString(KEY_SUCCESS) != null) {
                        ErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);

                        String red = json.getString(KEY_ERROR);

                        if(Integer.parseInt(res) == 1){
                            pDialog.setTitle("Getting Data");
                            pDialog.setMessage("Loading Info");

                            ErrorMsg.setText("Successfully Registered");


                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");

                            /**
                             * Removes all the previous data in the SQlite database
                             **/
                            
                            /*
                             TODO: ADD userfunction for database stuff
                            UserFunctions logout = new UserFunctions();
                            logout.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_ZIPCODE),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                            
                            */
                              finish();
                        }

                        else if (Integer.parseInt(red) ==2){
                            pDialog.dismiss();
                            ErrorMsg.setText("User already exists");
                        }
                        else if (Integer.parseInt(red) ==3){
                            pDialog.dismiss();
                            ErrorMsg.setText("Invalid Email id");
                        }

                    }


                        else{
                        pDialog.dismiss();

                            ErrorMsg.setText("Error occured in registration");
                        }

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }}
        public void NetAsync(View view){
            new NetCheck().execute();
        }



}