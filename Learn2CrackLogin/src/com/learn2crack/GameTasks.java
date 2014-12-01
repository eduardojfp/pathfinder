package com.learn2crack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.learn2crack.library.UserFunctions;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameTasks extends Activity  { 
	private static final int PICK_IMAGE = 0;
	LinearLayout lm;
	TextView GameName;
	TextView Zipcode;
	TextView StartTime;
	TextView EndTime;
	
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
			
	
	private static String KEY_SUCCESS = "success";

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.game_tasks);
	        
	        //set layout/text to xml
	        GameName = (TextView) findViewById(R.id.gameName);
			StartTime = (TextView) findViewById(R.id.startTime);
			EndTime = (TextView) findViewById(R.id.endTime);
			//get the game data
			new getGameDataGid().execute();
			
	   	    lm = (LinearLayout) findViewById(R.id.linear);

	   	    
	         
	        // create the layout params that will be used to define how your
	        // button will be displayed
	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	         
	        
	        //get the teamdata
            new findTeamData().execute();
	        
	        
	 }
	 
	 OnClickListener btnClickListener = new OnClickListener() {
			@Override
		    public void onClick(View v) { //click on game will take you to the game
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
				
			    
				
			}
		};
		@Override
		protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		    if(requestCode == PICK_IMAGE && data != null && data.getData() != null) {
		        Uri _uri = data.getData();

		        //User had pick an image.
		        Cursor cursor = getContentResolver().query(_uri, new String[] { android.provider.MediaStore.Images.ImageColumns.DATA }, null, null, null);
		        cursor.moveToFirst();

		        //Link to the image
		        final String imageFilePath = cursor.getString(0);
		        cursor.close();
		    }
		    super.onActivityResult(requestCode, resultCode, data);
		}
	 ///get Game data
	 private class getGameDataGid extends AsyncTask<String, String, JSONObject> {//go into database and get game data base gid
			
	        @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }

	        @Override
	        protected JSONObject doInBackground(String... args) {
	        	//saved gid from profile
	        	SharedPreferences prefs2 = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
		  	    String savegid = "com.example.app.gid";
		  	    String gid = prefs2.getString(savegid, "none");
	        	UserFunctions userFunction = new UserFunctions();
	            JSONObject json = userFunction.getGameDataGid(gid);
	            return json;
	        }

	        @Override
	        protected void onPostExecute(JSONObject json) {
	            try {
	               if (json.getString(KEY_SUCCESS) != null) {

	            	   String res = json.getString(KEY_SUCCESS);
	            	   //set the data of xml with data from database
	                    JSONObject json_user = json.getJSONObject("Game");
	                    GameName.setText(json_user.getString("gname"));
	                    StartTime.setText("Start Time: " + json_user.getString("starttime"));
	        			EndTime.setText("End Time: " +json_user.getString("endtime"));
	                
	            }
	               else
	               {
	               }
	               } catch (JSONException e) {
	                e.printStackTrace();
	            }
	       }
			
			
		}
	 
	 
	 
	 
	 
	 private class findTeamData extends AsyncTask<String, String, JSONObject> {//find team info using gameid and userid
			//uid from login
			SharedPreferences prefs = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
	  	    String saveduid = "com.example.app.uid";
	  	    String uid = prefs.getString(saveduid, "none");
	  	    //gid from profile
	  	    SharedPreferences prefs2 = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
	  	    String savegid = "com.example.app.gid";
	  	    String gid = prefs2.getString(savegid, "none");
	  	    
	  	    //two layouts to add
	  	    LinearLayout a = new LinearLayout(GameTasks.this);
	  	    LinearLayout b = new LinearLayout(GameTasks.this);

			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }

	        @Override
	        protected JSONObject doInBackground(String... args) {

	        	UserFunctions userFunction = new UserFunctions();
	            JSONObject json = userFunction.findTeamData(uid, gid);
	            return json;
	        }

	        @Override
	        protected void onPostExecute(JSONObject json) {
	        	try {
	        		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( //set params
	    	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		        	JSONObject json_user = json.getJSONObject("teamuser"); 
		           a.setOrientation(LinearLayout.HORIZONTAL);
	        		///save teamid
	        	   SharedPreferences prefs = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
               	   String savedteam = "com.example.app.teamid";
               	   SharedPreferences.Editor editor = prefs.edit();
               	   String teamid = json_user.getString("teamid");
               	   editor.putString(savedteam, teamid);
               	   editor.commit();
	        		///
	        		
		            //set text based on data found
		            TextView teamname = new TextView(GameTasks.this); //display teamname
		            teamname.setLayoutParams(params);
		            teamname.setText(json_user.getString("teamname"));
		            a.addView(teamname);
		            
		            TextView score = new TextView(GameTasks.this);
		            score.setLayoutParams(params);
		            score.setText("Score: "+json_user.getString("score"));
		            b.addView(score);
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }

                lm.addView(a); 
                lm.addView(b); 
                new getTaskData().execute(); //execute get task after
	        }
			
		}
	//////////////////get task info from database
	 private class getTaskData extends AsyncTask<String, String, JSONObject> {//goes into tasks and gets taskinfo based on teamid and gameid
			//from previous fuction
		 	SharedPreferences prefs = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
		 	String savedteam = "com.example.app.teamid";
	  	    String teamid =  prefs.getString(savedteam, "1");
	  	    
	  	    //from profile
	  	    SharedPreferences prefs2 = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
	  	    String savegid = "com.example.app.gid";
	  	    String gid = prefs2.getString(savegid, "none");

	  	    

			@Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	        }

	        @Override
	        protected JSONObject doInBackground(String... args) {

	        	UserFunctions userFunction = new UserFunctions();
	            JSONObject json = userFunction.getTaskData(teamid, gid);
	            return json;
	        }

	        @Override
	        protected void onPostExecute(JSONObject json) {
	        	
	        	try {
	        		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( //set params
	    	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	        		//in multiple objects
	        		int i = 0;
	        		
		        	JSONObject json_user = json.getJSONObject("task"); 
		        	

	        		while(json_user.has("array" + Integer.toString(i))){
	        			//dynaimic layout to add
	        	  	    LinearLayout name = new LinearLayout(GameTasks.this);

	        	  	    LinearLayout description = new LinearLayout(GameTasks.this);

	        	  	    LinearLayout score = new LinearLayout(GameTasks.this);
	        	  	    LinearLayout imagesend = new LinearLayout(GameTasks.this);
	        			
			        	JSONObject obj	= json_user.getJSONObject("array" + Integer.toString(i));
			        	
			        	JSONObject obj2 = (obj.getJSONObject("data"));
			        	//set the text based off of data found
			        	TextView taskName = new TextView(GameTasks.this);
			        	taskName.setText(obj2.getString("tname"));
			        	name.addView(taskName);
			        	
			        	TextView taskDescription = new TextView(GameTasks.this);
			        	taskDescription.setText("Task Description: " + obj2.getString("tdescription"));
			        	description.addView(taskDescription );
			        	
			        	TextView taskScore = new TextView(GameTasks.this);
			        	taskScore.setText(obj2.getString("score"));
			        	score.addView(taskScore);
			        	if(obj2.getString("complete").equals("0")){ //if task not complete add a send and image button
			        		ImageButton image = new ImageButton(GameTasks.this);
			        		image.setBackgroundResource(R.drawable.picture_icon);
	
							image.setOnClickListener(btnClickListener); //add a listener
			        		image.setTag(obj2.getInt("tid")); //set id to be value of taskid
			        		imagesend.addView(image);
			        		
			        		Button send = new Button(GameTasks.this);
			        		send.setText("Send");
			        		send.setId(obj2.getInt("tid")); //set id to be value of taskid
			        		imagesend.addView(send);
			        		
			        	}
		        		i++;

			        	//add layout
			        	lm.addView(name); 
			        	lm.addView(description); 
			        	lm.addView(score); 
			        	lm.addView(imagesend); 
	        		}
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	        }
			
		}
	
}