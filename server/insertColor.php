<?php

include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$name = mysqli_real_escape_string($conn, $_POST['name']);

	$check_query = "SELECT * FROM tblcolor WHERE name ='$name'";
	$check_result = mysqli_fetch_array(mysqli_query($conn, $check_query));

	if (!isset($check_result))
	{
		$sql = mysqli_query($conn, 'INSERT INTO tblcolor (name)
			VALUES("'.$name.'")');


			if (!$sql) {
				die (mysqli_error($conn));
			} else {

				$result["success"] = 1;
				$result["message"] = "Thêm thành công!";

				echo json_encode($result);
				print_r ($result);
			}
		}
	} 
	else 
	{
		$result["success"] = 0;
		$result["message"] = "Thêm thất bại, xin vui lòng thử lại!";

		echo json_encode($result);
		print_r ($result);
	}

?>
