<?php
//include database info+connection
include("DataBaseConfig.php");
$link= mysqli_connect($db_host,$db_user,$db_pass,$db_name);
$query= "select * from posts";
$result = mysqli_query($link,$query);
if(@$result){
 while($row = mysqli_fetch_array($result)){
//while(mysqli_num_rows($result) != 0){
  $arr[]= $row;
    }
}
$output =json_encode($arr);
echo($output);
$mysqli -> close();
?>