<?php

include "connect.php";

if($_SERVER['REQUEST_METHOD'] == 'POST') 
{
	$request = mysqli_real_escape_string($conn, $_POST['request']);
	$permitted_chars = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	
	$array = array(date("Ymd"), implode(explode(":", date("H:i:s"))), generate_string($permitted_chars, 6));
	$id_user = implode("", $array);
	
	$email = mysqli_real_escape_string($conn, $_POST['email']);

	$password1 = mysqli_real_escape_string($conn, $_POST['password1']);
	$password2 = mysqli_real_escape_string($conn, $_POST['password2']);
	$password = password_hash($password1, PASSWORD_DEFAULT);

	$created_at = date("Y-m-d H:i:s");
	$updated_at = date("Y-m-d H:i:s");

	$sql = 'INSERT INTO tbluser (id_user, email, password, created_at, updated_at)
	VALUES("'.$id_user.'","'.$email.'","'.$password.'","'.$created_at.'","'.$updated_at.'")';
		$check_query_email = "SELECT * FROM tbluser WHERE email ='$email'";
		$check_result = mysqli_fetch_array(mysqli_query($conn, $check_query_email));

		if (isset($check_result))
		{

			$result["success"] = "0";
			$result["message"] = "Tài khoản đã tồn tại!";

			echo json_encode($result);
			print_r ($result);
		} 
		else 
		{
			if (!is_email($email))
			{
				$result["success"] = "0";
				$result["message"] = "Email is incorrected!";

				echo json_encode($result);
				print_r ($result);
				return;
			} else {
				if ($password1 == null)
				{
					$result["success"] = "0";
					$result["message"] = "Nhập mật khẩu!";

					echo json_encode($result);
					print_r ($result);
					return;
				}
				else
				{
					if ($request == "0") {
						if (strlen($password1) < 8)
						{
							$result["success"] = "0";
							$result["message"] = "Sử dụng 8 ký tự trở lên cho mật khẩu của bạn!";

							echo json_encode($result);
							print_r ($result);
							return;
						}
						else
						{
							if ($password2 == null)
							{
								$result["success"] = "0";
								$result["message"] = "Xác nhận mật khẩu của bạn!";

								echo json_encode($result);
								print_r ($result);
								return;
							}
							else
							{
								if ($password1 != $password2)
								{
									$result["success"] = "0";
									$result["message"] = "Các mật khẩu đã nhập không hợp lệ. Hãy thử lại!";

									echo json_encode($result);
									print_r ($result);
									return;
								}
								else
								{
									if (!is_password($password1))
									{
										$result["success"] = "0";
										$result["message"] = "Vui lòng chọn mật khẩu mạnh hơn. Hãy thử kết hợp giữa chữ cái, số và ký hiệu!";

										echo json_encode($result);
										print_r ($result);
										return;
									}
								}
							}
						}
					} else {
						
					}
					
					$enforcement = mysqli_query($conn, $sql);

					if (!$enforcement) 
					{
						die (mysqli_error($conn));
					}

					else 
					{

						$result["success"] = "1";
						$result["message"] = "Đăng ký thành công!";

						echo json_encode($result);
						print_r ($result);
					}
				}
			}



			
		}
	}
	else 
	{

		$result["success"] = "0";
		$result["message"] = "Đăng ký thất bại, xin vui lòng thử lại!";

		echo json_encode($result);
		print_r ($result);
	}
	function generate_string($input, $strength = 16) {
		$input_length = strlen($input);
		$random_string = '';
		for($i = 0; $i < $strength; $i++) {
			$random_character = $input[mt_rand(0, $input_length - 1)];
			$random_string .= $random_character;
		}
		return $random_string;
	}

	function is_email($str) {
		return (!preg_match("/^([a-z0-9\+_\-]+)(\.[a-z0-9\+_\-]+)*@([a-z0-9\-]+\.)+[a-z]{2,6}$/ix", $str)) ? FALSE : TRUE;
	}

	function is_password($str) {
		return (!preg_match("/^([A-Z]){1}([\w_\.!@#$%^&*()]+){8,31}$/ix", $str)) ? FALSE : TRUE;
	}

	function is_phone_number($str) {
		return (!preg_match("/^(84|0[3|5|7|8|9])+([0-9]{9})\b/", $str)) ? FALSE : TRUE;
	}

	?>
