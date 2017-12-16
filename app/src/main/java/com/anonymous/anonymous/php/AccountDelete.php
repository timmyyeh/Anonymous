<?php
if($_SERVER['REQUEST_METHOD']=='POST'){

    include 'DatabaseConfig.php';

     $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

     $A_email = $_POST['email'];

     $Sql_Query = "DELETE FROM account WHERE email = '$A_email'";

     if(mysqli_query($con,$Sql_Query)){
        echo 'Record Deleted Successfully';
     }
    else
    {
       echo 'Something went wrong';
    }
 }
 mysqli_close($con);
?>