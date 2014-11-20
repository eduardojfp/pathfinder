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
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class GameTasks extends Activity  { 
	 LinearLayout lm;

	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.game_tasks);
	        
	   	    lm = (LinearLayout) findViewById(R.id.linear);

	   	    
	         
	        // create the layout params that will be used to define how your
	        // button will be displayed
	        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	         
	        

            new findTeamData().execute();
	        //Create four
	        
	 }
	 private class findTeamData extends AsyncTask<String, String, JSONObject> {
			
			SharedPreferences prefs = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
	  	    String saveduid = "com.example.app.uid";
	  	    String uid = prefs.getString(saveduid, "none");
	  	    String gid = "1";

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
                new getTaskData().execute();
	        }
			
		}
	//////////////////get task info from database
	 private class getTaskData extends AsyncTask<String, String, JSONObject> {
			
		 	SharedPreferences prefs = GameTasks.this.getSharedPreferences("com.learn2crack", Context.MODE_PRIVATE);
		 	String savedteam = "com.example.app.teamid";
	  	    String teamid =  "1"; //prefs.getString(savedteam, "1");
	  	    
	  	    String gid = "1";

	  	    LinearLayout name = new LinearLayout(GameTasks.this);

	  	    LinearLayout description = new LinearLayout(GameTasks.this);

	  	    LinearLayout score = new LinearLayout(GameTasks.this);
	  	    LinearLayout imagesend = new LinearLayout(GameTasks.this);

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
	        		
		        	JSONObject json_user = json.getJSONObject("task"); 
		        	
		        	
		        	JSONObject obj	= json_user.getJSONObject("array0");
		        	
		        	JSONObject obj2 = (obj.getJSONObject("data"));
		        	TextView taskName = new TextView(GameTasks.this);
		        	taskName.setText(obj2.getString("tname"));
		        	name.addView(taskName);
		        	
		        	TextView taskDescription = new TextView(GameTasks.this);
		        	taskDescription.setText("Task Description: " + obj2.getString("tdescription"));
		        	description.addView(taskDescription );
		        	
		        	TextView taskScore = new TextView(GameTasks.this);
		        	taskScore.setText(obj2.getString("score"));
		        	score.addView(taskScore);
		        	if(obj2.getString("complete").equals("0")){
		        		ImageButton image = new ImageButton(GameTasks.this);
		        		image.setBackgroundResource(R.drawable.picture_icon);
		        		image.setId(obj2.getInt("tid")); //set id to be value of taskid
		        		imagesend.addView(image);
		        		
		        		Button send = new Button(GameTasks.this);
		        		send.setText("Send");
		        		send.setId(obj2.getInt("tid"));
		        		imagesend.addView(send);
		        		
		        	}
		        	//taskScore.setText("Points: " + obj2.getString("score"));
		        	//score.addView(taskScore);
		        	
		        	
		            /*a.setOrientation(LinearLayout.HORIZONTAL);
		            
		            TextView teamname = new TextView(GameTasks.this); //display teamname
		            teamname.setLayoutParams(params);
		            teamname.setText(json_user.getString("teamname"));
		            a.addView(teamname);
		            
		            TextView score = new TextView(GameTasks.this);
		            score.setLayoutParams(params);
		            score.setText("Score: "+json_user.getString("score"));
		            b.addView(score);*/
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }

             lm.addView(name); 
             lm.addView(description); 
             lm.addView(score); 
             lm.addView(imagesend); 
	        }
			
		}
	
}