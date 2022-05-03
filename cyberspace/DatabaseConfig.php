<?php
//database host name
$db_host="localhost";
//database username
$db_user="root";
//database password
$db_pass= "";
//database name
$db_name="cyberspacedb";
//connect to databse
$mysqli = new mysqli($db_host,$db_user,$db_pass,$db_name);
if(mysqli_connect_errno()){
    die("Connection Failed" .$mysqli->connect_error);
}


?>