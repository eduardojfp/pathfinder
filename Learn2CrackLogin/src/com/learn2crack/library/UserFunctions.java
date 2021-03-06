package com.learn2crack.library;

/**
 * Edited by Megan Matiz and Michael Shih
 * 
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import android.content.Context;


public class UserFunctions {

    private JSONParser jsonParser;

    //URL of the PHP API. all the same 
    private static String loginURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String registerURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String forpassURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String chgpassURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String getGameDataURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String joinGIDURL = "http://team-pathfinder.tk/a4794025_DB/";
    private static String processGameURL = "http://team-pathfinder.tk/a4794025_DB/";
    
    private static String getTaskData_tag = "getTaskData";
    private static String findTeamData_tag = "findTeamData";
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String forpass_tag = "forpass";
    private static String chgpass_tag = "chgpass";
    private static String getUserData_tag = "getUserData";
    private static String setUserData_tag = "setUserData";
    private static String getGameDataGid_tag = "getGameDataGid";
    private static String getGameData_tag = "getGameData";
    private static String getGamesByUid_tag = "getGamesByUid";
    private static String joinGID_tag = "joinGID";
    private static String createGame_tag = "createGame";


    // constructor7y
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    /**
     * Function to Login
     **/

    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }

    /**
     * Function to change password
     **/

    public JSONObject chgPass(String newpas, String email){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", chgpass_tag));

        params.add(new BasicNameValuePair("newpas", newpas));
        params.add(new BasicNameValuePair("email", email));
        JSONObject json = jsonParser.getJSONFromUrl(chgpassURL, params);
        return json;
    }

    /**
     * Function to reset the password
     **/

    /*
     * 
     * called from PasswordReset
     * creates a list of params for the JSONObject
     * returns the json object as a url, and list of params
     */
    public JSONObject forPass(String forgotpassword){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", forpass_tag));
        params.add(new BasicNameValuePair("forgotpassword", forgotpassword));
        JSONObject json = jsonParser.getJSONFromUrl(forpassURL, params);
        return json;
    }

     /**
      * Function to  Register
      **/
    public JSONObject registerUser(String fname, String lname, String email, String uname, String uzip, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("fname", fname));
        params.add(new BasicNameValuePair("lname", lname));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("uname", uname));
        params.add(new BasicNameValuePair("uzip", uzip));
        params.add(new BasicNameValuePair("password", password));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        return json;
    }
    /**
     * Function to Create a new game
     */
    
    public void processGame(String gameName, String numTask, 
    		String hourStart, String minStart, String hourEnd, String minEnd,
    		String year, String month, String day) {
        // Building Parameters
    	
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        
        params.add(new BasicNameValuePair("gameName", gameName));
        params.add(new BasicNameValuePair("numTask", numTask));
        params.add(new BasicNameValuePair("tag", createGame_tag));
        params.add(new BasicNameValuePair("hourStart", hourStart));
        params.add(new BasicNameValuePair("minStart", minStart));
        params.add(new BasicNameValuePair("hourEnd", hourEnd));
        params.add(new BasicNameValuePair("minEnd", minEnd));
        params.add(new BasicNameValuePair("year", year));
        params.add(new BasicNameValuePair("month", month));
        params.add(new BasicNameValuePair("day", day));
        
        JSONObject json = jsonParser.getJSONFromUrl(processGameURL,params);
        
    }
    /**
     * Function to get User Data
     **/
    public JSONObject getUserData(String uid){
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", getUserData_tag));
    	params.add(new BasicNameValuePair("uid", uid));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
    	return json;
    }
    
    /**
     * Function set user data
     **/
    public JSONObject setUserData(String uid, String username, String about, String contact){
    	
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
    	params.add(new BasicNameValuePair("tag", setUserData_tag));
    	params.add(new BasicNameValuePair("uid", uid));
    	params.add(new BasicNameValuePair("username", username));
    	params.add(new BasicNameValuePair("about", about));
    	params.add(new BasicNameValuePair("contact", contact));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
    	return json;
    }
    /**
     * Function to find Team data
     **/
	public JSONObject findTeamData(String uuid, String gid){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", findTeamData_tag));
		params.add(new BasicNameValuePair("uuid", uuid));
		params.add(new BasicNameValuePair("gid", gid));
	    JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
		return json;
	} 
	/**
     * Function to get task data
     **/
	public JSONObject getTaskData(String teamid, String gid){
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getTaskData_tag));
		params.add(new BasicNameValuePair("teamid", teamid));
		params.add(new BasicNameValuePair("gid", gid));
	    JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
		return json;
	}
	/**
     * Function to get game data by gid
     **/
	public JSONObject getGameDataGid(String gid){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getGameDataGid_tag));
		params.add(new BasicNameValuePair("gid", gid));
	    JSONObject json = jsonParser.getJSONFromUrl(getGameDataURL,params);
		return json;
	}
	/**
	 * Function to get game data by uid
	 */
	public JSONObject getGamesByUid(String uid){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getGamesByUid_tag));
		params.add(new BasicNameValuePair("uid", uid));
	    JSONObject json = jsonParser.getJSONFromUrl(getGameDataURL,params);
		return json;
		
	}
	  
	/*
     * Function to join a game
     */

    public JSONObject joinGID(String uid, String gid){
    	List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", joinGID_tag));
		params.add(new BasicNameValuePair("uid", uid));
		params.add(new BasicNameValuePair("gid", gid));
	    JSONObject json = jsonParser.getJSONFromUrl(joinGIDURL,params);
		return json;
    }
    
	/**
     * Function get game data by zipcode
     **/
	public JSONObject getGameData(String uzip){
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", getGameData_tag));
		params.add(new BasicNameValuePair("uzip", uzip));
	    JSONObject json = jsonParser.getJSONFromUrl(getGameDataURL,params);
		return json;
	}


    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }
    
}

