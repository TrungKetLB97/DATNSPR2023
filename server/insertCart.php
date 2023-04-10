<?php

include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$id_user = mysqli_real_escape_string($conn, $_POST['id_user']);
	$code_product = mysqli_real_escape_string($conn, $_POST['code_product']);
	$quantity = mysqli_real_escape_string($conn, $_POST['code_product']);

	$check_query = "SELECT * FROM tblcart WHERE code_product ='$code_product'";
	$check_result = mysqli_fetch_array(mysqli_query($conn, $check_query));

	if (!isset($check_result))
	{
		$sql = mysqli_query($conn, 'INSERT INTO tblcart (id_user, code_product, quantity)
        VALUES("'.$id_user.'","'.$code_product.'","'.$quantity.'")');

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
		// $sql = mysqli_query($conn, 'UPDATE tblcart SET quantity = '$quantity' WHERE code_product = '$code_product' AND id_user = '$id_user'');
		// if (!$sql) {
		// 	die (mysqli_error($conn));
		// } else {

		//     $result["success"] = 1;
		// 	$result["message"] = "Đã thêm sản phẩm!";

		// 	echo json_encode($result);
		// 	print_r ($result);
		// }
	}
	
} else {

    $result["success"] = 0;
	$result["message"] = "Thêm thất bại, xin vui lòng thử lại!";

	echo json_encode($result);
	print_r ($result);
}


?>
