// Register new user
 <?php
    if($_SERVER['REQUEST_METHOD']=='POST'){

        include 'DatabaseConfig.php';

        $con = mysqli_connect($HostName,$HostUser,$HostPass,$DatabaseName);

        $A_email = $_POST['Email'];
        $A_phone = $_POST['Phone'];
        $A_pass= $_POST['Password'];
        $A_country = $_POST['Country'];

        $Sql_Query = "INSERT INTO account (name, phone, password, country) values ('$A_email','$A_phone','$A_pass', 'A_country')";

        if(mysqli_query($con,$Sql_Query)){
            echo 'Account Registered Successfully';
        }
        else{
            echo 'Something went wrong';
        }
    }
    mysqli_close($con);
?>