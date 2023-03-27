<?php

	include('connect.php');

	$id_product = mysqli_real_escape_string($conn, $_POST['id_product']);

	$stmt = $conn->prepare("select id_product, id_category, name, image_thumb, image_large, cost_price, selling_price, color, size, quantity, rate, description, receiving_date, discount FROM tblproducts WHERE id_product = '$id_product'");

	$stmt -> execute();

	$stmt -> bind_result($id_product, $id_category, $name, $image_thumb, $image_large, $cost_price, $selling_price, $color, $size, $quantity, $description, $rate, $receiving_date, $discount);

	$tblproducts = array();

	while ($stmt -> fetch()){

	    $temp = array();
		$temp['id_product'] = $id_product;
		$temp['id_category'] = $id_category;
		$temp['name'] = $name;
		$temp['image_thumb'] = $image_thumb;
		$temp['image_large'] = json_decode($image_large);
		$temp['cost_price'] = number_format($cost_price);
		$temp['selling_price'] = number_format($selling_price);
		$temp['color'] = $color;
		$temp['size'] = $size;
		$temp['quantity'] = $quantity;
		$temp['rate'] = $rate;
		$temp['description'] = $description;
		$temp['receiving_date'] = $receiving_date;
		$temp['discount'] = $discount;

		array_push($tblproducts, $temp);
	}

	echo json_encode($tblproducts);

?>