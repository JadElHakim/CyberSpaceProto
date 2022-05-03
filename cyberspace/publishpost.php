<?php
//include database info+connection
include("DataBaseConfig.php");
$json = file_get_contents('php://input');
$obj = json_decode($json);
$title = $obj->{'title'};
echo "this is title $title";
$vulnerability = $obj->{'vulnerability_type'};
$vulnerability_description = $obj->{'vulnerability_description'};
$mitigation_description = $obj->{'mitigation_description'};
$username = $obj->{'username'};
$query= $mysqli->prepare("INSERT INTO posts(post_title,vulnerability_type,vulnerability_description,mitigation_description,username) VALUES (?,?,?,?,?)");
$query->bind_param("sssss", $title, $vulnerability, $vulnerability_description,$mitigation_description,$username);
$query->execute();
?>