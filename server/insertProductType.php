<?php

include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$id_product_type = mysqli_real_escape_string($conn, $_POST['id_product_type']);
	$name = mysqli_real_escape_string($conn, $_POST['name']);
	$image = mysqli_real_escape_string($conn, $_POST['image']);

	$check_query_id = "SELECT * FROM tblproducttype WHERE id_product_type ='$id_product_type'";
	$check_result_id = mysqli_fetch_array(mysqli_query($conn, $check_query_id));

	$check_query_name = "SELECT * FROM tblproducttype WHERE name ='$name'";
	$check_result_name = mysqli_fetch_array(mysqli_query($conn, $check_query_name));

	if (!isset($check_result_id) && !isset($check_result_name))
	{
		$sql = mysqli_query($conn, 'INSERT INTO tblproducttype(id_product_type, name, image)
			VALUES("'.$id_product_type.'","'.$name.'","'.$image.'")');


			if (!$sql) {
				die (mysqli_error($conn));
			} else {

				$result["success"] = 1;
				$result["message"] = "Thêm thành công!";

				echo json_encode($result);
				print_r ($result);
			}
		}
		else
		{
			if (isset($check_result_id)) {
				$result["success"] = 0;
				$result["message"] = "Mã sản phẩm đã tồn tại!";
				echo json_encode($result);
				print_r ($result);
			} 
			if (isset($check_result_name))
			{
				$result["success"] = 0;
				$result["message"] = "Loại sản phẩm đã tồn tại!";
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
