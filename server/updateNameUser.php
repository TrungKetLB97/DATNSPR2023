<?php

include "connect.php";

// print_r($_POST); die();

if($_SERVER['REQUEST_METHOD'] == 'POST') {
	
	$id_user = $_POST['id_user'];
	
	$name_user = mysqli_real_escape_string($conn,$_POST['name_user']);

		
	if (isset($_POST['name_user'])&&!empty($_POST['name_user'])) {
        $name_user = $_POST['name_user'];
        update_name_user($conn,$id_user,$name_user);

        $result["success"] = "1";
		$result["message"] = "Cập nhật thành công!";

		echo json_encode($result);
		print_r ($result);
    } else {
    	$result["success"] = "0";
		$result["message"] = "Tên người dùng không thay đổi";

		echo json_encode($result);
		print_r ($result["message"]);
    }		    
		

} else {

    $result["success"] = "0";
	$result["message"] = "Cập nhật thất bại, xin vui lòng thử lại!";

	echo json_encode($result);
	print_r ($result);
}

function update_name_user($conn, $id_user,$name_user){
    mysqli_query($conn, "UPDATE tbluser SET name_user = '$name_user' WHERE id_user = '$id_user'");
}


?>
