package com.learn2crack;

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
	        for(int j=0;j<=4;j++)
	        {  

	            LinearLayout ll = new LinearLayout(this);
	            // Create LinearLayout
	            ll.setOrientation(LinearLayout.HORIZONTAL);
	             
	            // Create TextView
	            TextView product = new TextView(this);
	            product.setText(" Product"+j+"    ");
	            ll.addView(product);
	             
	            // Create TextView
	            TextView price = new TextView(this);
	            price.setText("  $"+j+"     ");
	            ll.addView(price);
	             
	            // Create Button
	            final Button btn = new Button(this);
	                // Give button an ID
	                btn.setId(j+1);
	                btn.setText("Add To Cart");
	                // set the layoutParams on the button
	                btn.setLayoutParams(params);
	                 
	                final int index = j;
	                // Set click listener for button
	                btn.setOnClickListener(new View.OnClickListener() {
	                    public void onClick(View v) {
	                         
	                       // Log.i("TAG", "index :" + index);
	                         
	                        Toast.makeText(getApplicationContext(),
	                                "Clicked Button Index :" + index,
	                                Toast.LENGTH_LONG).show();
	                         
	                    }
	                });
	                 
	               //Add button to LinearLayout
	                ll.addView(btn);
	               //Add button to LinearLayout defined in XML
	                lm.addView(ll); 
	                
	        }
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

	  	    LinearLayout a = new LinearLayout(GameTasks.this);

	  	    LinearLayout b = new LinearLayout(GameTasks.this);

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

             //lm.addView(a); 
             //lm.addView(b); 
	        }
			
		}
	
}