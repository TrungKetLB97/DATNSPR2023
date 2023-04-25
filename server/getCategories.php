<?php

	include('connect.php');

	$stmt = $conn->prepare("SELECT id, title, image FROM tblcategory");

	$stmt -> execute();

	$stmt -> bind_result($id, $title, $image);

	$tblcategory = array();

	while($stmt -> fetch()){

	    $temp = array();
		
		$temp['id'] = $id;
		$temp['title'] = $title;
		$temp['image'] = $image;

		array_push($tblcategory, $temp);
	}

	echo json_encode($tblcategory);

?>