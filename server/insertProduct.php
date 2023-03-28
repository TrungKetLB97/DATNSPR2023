<?php

include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$id_category = mysqli_real_escape_string($conn, $_POST['id_category']);
	$id_color = mysqli_real_escape_string($conn, $_POST['id_color']);
	$id_size = mysqli_real_escape_string($conn, $_POST['id_size']);
	$code = mysqli_real_escape_string($conn, $_POST['code']);
	$name = mysqli_real_escape_string($conn,$_POST['name']);
	$image = mysqli_real_escape_string($conn,$_POST['image']);
	$cost_price = mysqli_real_escape_string($conn,$_POST['cost_price']);
	$selling_price = mysqli_real_escape_string($conn, $_POST['selling_price']);
	$quantity = mysqli_real_escape_string($conn,$_POST['quantity']);
	$description = mysqli_real_escape_string($conn,$_POST['description']);
	$receiving_date = date("Y-m-d H:i:s");

	$sql = mysqli_query($conn, 'INSERT INTO tblproducts (id_category, id_color, id_size, code, name, image, cost_price, selling_price, quantity, description, receiving_date)
        VALUES("'.$id_category.'","'.$id_color.'","'.$id_product_size.'","'.$code_product.'","'.$name.'","'.$image.'","'.$cost_price.'","'.$selling_price.'","'.$quantity.'","'.$description.'","'.$receiving_date.'")');

    echo ($sql);
		if (!$sql) {
			die (mysqli_error($conn));
		} else {

		    $result["success"] = 1;
			$result["message"] = "Thêm thành công!";

			echo json_encode($result);
			print_r ($result);
		}
		
	
} else {

    $result["success"] = 0;
	$result["message"] = "Thêm thất bại, xin vui lòng thử lại!";

	echo json_encode($result);
	print_r ($result);
}


?>
