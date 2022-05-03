<?php
//include database info+connection
include("DataBaseConfig.php");
$username = $_POST['username'];
$pass =$_POST['password'];
$pass = trim($pass);
$link= mysqli_connect($db_host,$db_user,$db_pass,$db_name);
$query= "select * from usertable where username = '$username'";
$result = mysqli_query($link,$query);
$row = mysqli_fetch_assoc($result);
if (mysqli_num_rows($result) != 0) {
    $dbusername = $row['username'];
    $dbpassword = $row['passwordhash'];
    $userid = $row['id'];
    if ($dbusername == $username && password_verify($pass, $dbpassword)){
        $login = true;
    } else {
    $login = false;
    echo "Not Logged";
}
} else{
    $login = false;
    echo "Not Logged";
};
if($login){
    $arr[]= $row;
    $output =json_encode($arr);
    echo($output);
}
?>