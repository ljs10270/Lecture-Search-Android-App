<?php
	header("Content-Type: text/html; charset=UTF-8");
	$con = mysqli_connect("localhost", "id", "FTP pw", "id");
		
	$courseMajor = $_GET["courseMajor"];

	$result = mysqli_query($con, "SELECT * FROM lecturelist WHERE major = '$courseMajor'");
	$response = array();

	while($row = mysqli_fetch_array($result)){
		array_push($response, array("lectureCode"=>$row[0], "major"=>$row[1], "lectureName"=>$row[2]
			, "term"=>$row[3], "grade"=>$row[4], "gradePoint"=>$row[5]));
	}

	echo json_encode(array("response"=>$response), JSON_UNESCAPED_UNICODE);
	mysqli_close($con);
?>