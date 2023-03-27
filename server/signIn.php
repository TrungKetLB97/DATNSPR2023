<?php

	include('connect.php');

	if($_SERVER['REQUEST_METHOD'] == 'POST') {
		$email = mysqli_real_escape_string($conn, $_POST['email']);
		$password = mysqli_real_escape_string($conn, $_POST['password']);

		$result = [];

		$que = mysqli_query($conn, "select * from tbluser where email = '$email'");
		$row = mysqli_num_rows($que);

		if($row == 1) {

			$data = $que -> fetch_array();

			if (password_verify($password, $data['password'])) {
				$stmt = $conn -> prepare("SELECT id_user, email, password, name_user, phone_number, avatar, birth, sex FROM tbluser where email = '$email'");

				$stmt -> execute();

				$stmt -> bind_result($id_user, $email, $password, $name_user, $phone_number, $avatar, $birth, $sex);

				$data_user = array();

				while($stmt -> fetch()){

				    $temp = array();
					
					$temp['id_user'] = $id_user;
					$temp['email'] = $email;
					$temp['password'] = $password;
					$temp['name_user'] = $name_user;
					$temp['phone_number'] = $phone_number;
					$temp['avatar'] = $avatar;
					$temp['birth'] = $birth;
					$temp['sex'] = $sex;

					array_push($data_user, $temp);
				}

				$result['success'] = 1;
		    	$result['message'] = "Đăng nhập thành công";
		    	$result['data'] = $data_user;

		    	echo json_encode($result);
			} else {
				$result["success"] = 0;
				$result["message"] = "Mật khẩu không chính xác!z";
				echo json_encode($result);
			}
    	
	    } else {

			$result["success"] = 0;
			$result["message"] = "Tài khoản không tồn tại";
			echo json_encode($result);
		}

	}

?>
