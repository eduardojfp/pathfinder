<?php

/**
 PHP API for Login, Register, Changepassword, Resetpassword Requests and for Email Notifications.
 **/

if (isset($_POST['tag']) && $_POST['tag'] != '') {
    // Get tag
    $tag = $_POST['tag'];

    // Include Database handler
    require_once 'include/DB_Functions.php';
    $db = new DB_Functions();
    // response Array
    $response = array("tag" => $tag, "success" => 0, "error" => 0);

    // check for tag type
    if ($tag == 'login') {
        // Request type is check Login
        $email = $_POST['email'];
        $password = $_POST['password'];

        // check for user
        $user = $db->getUserByEmailAndPassword($email, $password);
        if ($user != false) {
            // user found
            // echo json with success = 1
            $response["success"] = 1;
            $response["user"]["fname"] = $user["firstname"];
            $response["user"]["lname"] = $user["lastname"];
            $response["user"]["email"] = $user["email"];
      $response["user"]["uname"] = $user["username"];
            $response["user"]["uzip"] = $user["zipcode"];
            $response["user"]["uid"] = $user["unique_id"];
            $response["user"]["created_at"] = $user["created_at"];
            
            echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "Incorrect email or password!";
            echo json_encode($response);
        }
    } 
      //get team data
      else if ($tag == 'findTeamData') {
        $uuid = $_POST['uuid'];
        $gid = $_POST['gid'];
        $user = $db->findTeamData($uuid, $gid);
        if ($user != false) {
        $response["success"] = 1;
        $response["teamuser"]["teamid"] = $user["teamid"];
        $response["teamuser"]["score"] = $user["score"];
        $response["teamuser"]["teamname"] = $user["teamname"];
        echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No Team data";
            echo json_encode($response);
        }
        
    }
    //get Games by uid
      else if ($tag == 'getGamesByUid') {
        $uuid = $_POST['uid'];
        $user = $db->getGamesByUid($uuid);
        if ($user != false) {
        $response["success"] = 1;
        $response["user"]["gid"] = $user["gid"];
        $response["user"]["gname"] = $user["gname"];
        echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No Games found";
            echo json_encode($response);
        }
        
    }
    //getTaskdata
    else if ($tag == 'getTaskData') {
        $teamid = $_POST['teamid'];
        $gid = $_POST['gid'];
        $user = $db->getTaskData($teamid, $gid);
        if ($user != false) {
        $response["success"] = 1;
        for($i = 0; $i < count($user); $i++){
             $array = "array" . $i;
             $response["task"][$array] = $user[$i];
        }
        echo json_encode($response);
        } else {
            // user not found
            // echo json with error = 1
            $response["error"] = 1;
            $response["error_msg"] = "No Team data";
            echo json_encode($response);
        }
        
    }
    //using gid get game data
    else if ($tag == 'getGameDataGid') {
        $gid = $_POST['gid'];
        $game = $db->getGameDataGid($gid);
        if ($game != false) {
        $response["success"] = 1;
        $response["Game"]["gid"] = $game["gid"];
        $response["Game"]["gname"] = $game["gname"];
        $response["Game"]["location"] = $game["location"];
        $response["Game"]["gameadmin"] = $game["gameadmin"];
        $response["Game"]["starttime"] = $game["starttime"];
        $response["Game"]["endtime"] = $game["endtime"];
        $response["Game"]["done"] = $game["done"];
        echo json_encode($response);
       } else {
       //no games found
       $response["error"] = 1;
       $response["error_msg"]="No Game Data";
       echo json_encode($response);
       }
        
    }
    //get game data
  else if ($tag == 'getGameData') {
       $uzip=$_POST['uzip'];
       $game = $db->getGameData($uzip);
       if ($game != false) {
       $response["success"] = 1;
       $response["Game"]["gid"] = $game["gid"];
       $response["Game"]["gname"] = $game["gname"];
       $response["Game"]["gameadmin"] = $game["gameadmin"];
       $response["Game"]["starttime"] = $game["starttime"];
       $response["Game"]["endtime"] = $game["endtime"];
       $response["Game"]["done"] = $game["done"];
       echo json_encode($response);
       } else {
       //no games found
       $response["error"] = 1;
       $response["error_msg"]="No Game Data";
       echo json_encode($response);
       }
    }

  else if ($tag == 'getUserData'){
     $uid = $_POST['uid'];
     $userdata = $db->getUserData($uid);
     if($userdata != false){
       $response["success"] = 1;
       $response["user"]["uname"] = $userdata["username"];
       $response["user"]["created_at"] = $userdata["created_at"];
       $response["user"]["about"] = $userdata["About"];
       $response["user"]["contact"] = $userdata["contact"]; 
       
       echo json_encode($response);     
    }
    else{
      $response["error"] = 1;
      $response["error_msg"] = "No uid found";
      echo json_encode($response);
    }

  }
  else if ($tag == 'joinGID'){
    $uid = $_POST['uid'];
    $gid = $_POST['gid'];

    $game = $db->joinGID($uid, $gid);

    if ($game != false) {
     $response["success"] = 1;
     echo json_encode($response);
     } else {
     $response["error"] = 1;
     $response["error_msg"]="Something terrible happened.";
     echo json_encode($response);
     }
  }
  else if ($tag == 'setUserData'){
    $uniqueid = $_POST['uid'];
    $username = $_POST['username'];
    $about = $_POST['about'];
    $contact = $_POST['contact'];
    $userdata = $db->setUserData($uniqueid, $username, $about, $contact);
    if($userdata != false){
      $response["success"] = 1;
      echo json_encode($response);   
    }else{
  
      $response["error"] = 1;
      echo json_encode($response);    
    }
  }
  else if ($tag == 'chgpass'){
  $email = $_POST['email'];

  $newpassword = $_POST['newpas'];
  

  $hash = $db->hashSSHA($newpassword);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"];
  $subject = "Change Password Notification";
         $message = "\n\nYour Password has been successfully changed.\n\nTeam PathFinder";
          $from = "support@team-pathfinder.tk";
          $headers = "From:" . $from;
  if ($db->isUserExisted($email)) {

 $user = $db->forgotPassword($email, $encrypted_password, $salt);
if ($user) {
         $response["success"] = 1;
          mail($email,$subject,$message,$headers);
         echo json_encode($response);
}
else {
$response["error"] = 1;
echo json_encode($response);
}


            // user is already exists - error response
           
           
        } 
           else {

            $response["error"] = 2;
            $response["error_msg"] = "User does not exist";
             echo json_encode($response);

}
}
else if ($tag == 'forpass'){
$forgotpassword = $_POST['forgotpassword'];

$randomcode = $db->random_string();
  

$hash = $db->hashSSHA($randomcode);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"];
  $subject = "Password Recovery";
         $message = "Your Password has been successfully changed. Your new Password is $randomcode. Be sure to login with your new Password and change it.\n\nTeam PathFinder.";
          $from = "support@team-pathfinder.tk";
          $headers = "From:" . $from;
  if ($db->isUserExisted($forgotpassword)) {

 $user = $db->forgotPassword($forgotpassword, $encrypted_password, $salt);
if ($user) {
         $response["success"] = 1;
          mail($forgotpassword,$subject,$message,$headers);
         echo json_encode($response);
}
else {
$response["error"] = 1;
echo json_encode($response);
}


            // user does not exist - error response
           
           
        } 
           else {

            $response["error"] = 2;
            $response["error_msg"] = "User does not exist";
             echo json_encode($response);

}

}
else if ($tag == 'register') {
        // Request type is Register new user
        $fname = $_POST['fname'];
        $lname = $_POST['lname'];
        $email = $_POST['email'];
  $uname = $_POST['uname'];
        $uzip = $_POST['uzip'];
        $password = $_POST['password'];
   
          $subject = "Registration";
         $message = "Hello $fname,\n\nYou have successfully registered to PathFinder.\n\nEnjoy!\nTeam PathFinder.";
          $from = "support@team-pathfinder.com";
          $headers = "From:" . $from;

        // check if user is already existed
        if ($db->isUserExisted($email)) {
            // user is already existed - error response
            $response["error"] = 2;
            $response["error_msg"] = "User already exists";
            echo json_encode($response);
            } 
           else if(!$db->validEmail($email)){
            $response["error"] = 3;
            $response["error_msg"] = "Invalid Email Id";
            echo json_encode($response);             
            }
           else {
            // store user
            $user = $db->storeUser($fname, $lname, $email, $uname, $uzip, $password);
            if ($user) {
                // user stored successfully
            $response["success"] = 1;
            $response["user"]["fname"] = $user["firstname"];
            $response["user"]["lname"] = $user["lastname"];
            $response["user"]["email"] = $user["email"];
      $response["user"]["uname"] = $user["username"];
            $response["user"]["uzip"] = $user["zipcode"];
            $response["user"]["uid"] = $user["unique_id"];
            $response["user"]["created_at"] = $user["created_at"];
               mail($email,$subject,$message,$headers);
            
                echo json_encode($response);
            } else {
                // user failed to store
                $response["error"] = 1;
                $response["error_msg"] = "JSON Error occured in Registartion";
                echo json_encode($response);
            }
        }
    } else {
         $response["error"] = 3;
         $response["error_msg"] = "JSON ERROR";
        echo json_encode($response);
    }
} else {
    echo "Login API";
}
?>
            