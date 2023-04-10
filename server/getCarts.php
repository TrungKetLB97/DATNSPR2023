<?php

	include('connect.php');

	$id_user = mysqli_real_escape_string($conn, $_POST['id_user']);
	$id_product = mysqli_real_escape_string($conn, $_POST['id_product']);

	$stmt = $conn->prepare("select tblcart.id_user, tblcart.id_product, tblcart.quantity, tblproducts.id_category, tblproducts.name, tblproducts.image_thumb, tblproducts.image_large, tblproducts.cost_price, tblproducts.selling_price, tblproducts.color, tblproducts.size, tblproducts.quantity, tblproducts.rate, tblproducts.description, tblproducts.receiving_date, tblproducts.discount FROM tblcart, tblproducts WHERE tblcart.id_user = '$id_user' and tblcart.id_product = '$id_product' and tblproducts.id_product = '$id_product'");

	$stmt -> execute();

	$stmt -> bind_result($id_user, $id_product, $quantityC, $id_category, $name, $image_thumb, $image_large, $cost_price, $selling_price, $color, $size, $quantityP, $description, $rate, $receiving_date, $discount);

	$tblproducts = array();

	while ($stmt -> fetch()){

	    $temp = array();
	    $temp['id_user'] = $id_user;
		$temp['id_product'] = $id_product;
		$temp['quantityC'] = $quantityC;
		$temp['id_category'] = $id_category;
		$temp['name'] = $name;
		$temp['image_thumb'] = $image_thumb;
		$temp['image_large'] = json_decode($image_large);
		$temp['cost_price'] = number_format($cost_price);
		$temp['selling_price'] = number_format($selling_price);
		$temp['color'] = $color;
		$temp['size'] = $size;
		$temp['quantityP'] = $quantityP;
		$temp['rate'] = $rate;
		$temp['description'] = $description;
		$temp['receiving_date'] = $receiving_date;
		$temp['discount'] = $discount;

		array_push($tblproducts, $temp);
	}

	echo json_encode($tblproducts);

?>