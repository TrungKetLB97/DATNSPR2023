<?php

include('connect.php');

$name = mysqli_real_escape_string($conn, isset($_POST['name']) ? $_POST['name'] : '');
$id_category = mysqli_real_escape_string($conn, isset($_POST['id_category']) ? $_POST['id_category'] : '');
$stmt = null;
if (empty($name) && empty($id_category))
{
	$stmt = $conn->prepare("SELECT id_product, id_category, name, image_thumb, selling_price, color, size, discount FROM tblproducts ORDER BY receiving_date DESC LIMIT 20");
}
else if ($name && empty($id_category))
{
	$stmt = $conn->prepare("SELECT id_product, id_category, name, image_thumb, selling_price, color, size, discount FROM tblproducts where name = '$name'");
}
else if (empty($name) && $id_category)
{
	$stmt = $conn->prepare("SELECT id_product, id_category, name, image_thumb, selling_price, color, size, discount FROM tblproducts where id_category = '$id_category'");
}
else
{
	$stmt = $conn->prepare("SELECT id_product, id_category, name, image_thumb, selling_price, color, size, discount FROM tblproducts where name = '$name' and id_category = '$id_category'");
}

$stmt -> execute();

$stmt -> bind_result($id_product, $id_category, $name, $image_thumb, $selling_price, $color, $size, $discount);

$tblproducts = array();

while($stmt -> fetch()){

	$temp = array();
	$temp['id_product'] = $id_product;
	$temp['id_category'] = $id_category;
	$temp['name'] = $name;
	$temp['image_thumb'] = $image_thumb;
	$temp['selling_price'] = number_format($selling_price);
	$temp['color'] = $color;
	$temp['size'] = $size;
	$temp['discount'] = $discount;

	if ($tblproducts != null)
	{
		$checkDuplicate = false;
		for ($i = 0; $i < count($tblproducts); $i++) {
			
			if ($tblproducts[$i]['id_product'] == $temp['id_product'])
			{
				$checkDuplicate = true;
			}

		}
		if (!$checkDuplicate)
			array_push($tblproducts, $temp);

	}
	else
	{
		array_push($tblproducts, $temp);
	}
}

echo json_encode($tblproducts);

?>