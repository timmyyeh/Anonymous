// This file parse all account data from DB to JSON file
<?php
include 'DatabaseCongfig.php';

// connection
$conn = new mysqli($HostName, $HostUser, $HostPass, $DatabaseName);

if($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM account";

$result = $conn->query($sql);

if($result->num_rows > 0) {
    while($row[] = $result->fetch_assoc()) {
        $tem = $row;
        $json = json_encode($tem);
    }
}
else{
    echo "No Results Found.";
}
echo $json;
$conn->close();

?>