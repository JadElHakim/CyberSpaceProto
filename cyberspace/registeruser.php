<?php
//include database info+connection
include("DataBaseConfig.php");
$username = $_POST['username'];
$email = $_POST['email'];
echo($_POST['password']);
$pass = trim($_POST['password']);
$pass =password_hash($pass, PASSWORD_DEFAULT);

$career = "CyberSecurity Analyst";
$description = "I’m a CyberSecurity Analyst At Xcompany! !10 years of experience in research!
Check my Source Codes at 
https://github.com/
Contact Me : XXX-XXX-XXXX";
$profile = 'profile for now';
$query= $mysqli->prepare("INSERT INTO usertable(username,email,passwordhash,profile,career,profile_description) VALUES (?,?,?,?,?,?)");
$query->bind_param("ssssss", $username, $email, $pass,$profile,$career,$description);
$query->execute();

?>