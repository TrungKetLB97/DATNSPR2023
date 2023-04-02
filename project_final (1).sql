-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th3 17, 2023 lúc 02:49 AM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `project_final`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblcart`
--

CREATE TABLE `tblcart` (
  `id_user` varchar(20) NOT NULL,
  `id_product` varchar(50) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tblcart`
--

INSERT INTO `tblcart` (`id_user`, `id_product`, `quantity`) VALUES
('1', 'YKLM', 1),
('1', 'LMYK', 2);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblcategory`
--

CREATE TABLE `tblcategory` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `image` varchar(200) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tblcategory`
--

INSERT INTO `tblcategory` (`id`, `title`, `image`) VALUES
(4, 'Quần Âu', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTgGqmojNkcaOquD6eJLR0H6UCj-PwatvDx0Q&usqp=CAU'),
(5, 'Quần Kaki', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTQDIp6VNhvwrFuJE00X9JyLNt-oxBHt45I0g&usqp=CAU');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblcolor`
--

CREATE TABLE `tblcolor` (
  `id` int(11) NOT NULL,
  `color` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tblcolor`
--

INSERT INTO `tblcolor` (`id`, `color`) VALUES
(1, 'Đen'),
(2, 'Xanh');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblfavorite`
--

CREATE TABLE `tblfavorite` (
  `id_user` varchar(20) NOT NULL,
  `code_product` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblitemizedreceipt`
--

CREATE TABLE `tblitemizedreceipt` (
  `id_receipt` varchar(20) NOT NULL,
  `code_product` varchar(50) NOT NULL,
  `amount` int(11) NOT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblnotification`
--

CREATE TABLE `tblnotification` (
  `id` int(11) NOT NULL,
  `title` varchar(200) NOT NULL,
  `content` varchar(1000) NOT NULL,
  `time` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblproducts`
--

CREATE TABLE `tblproducts` (
  `id_product` varchar(20) NOT NULL,
  `id_category` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image_thumb` varchar(500) NOT NULL,
  `image_large` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `cost_price` varchar(20) NOT NULL,
  `selling_price` varchar(20) NOT NULL,
  `color` varchar(100) NOT NULL,
  `size` varchar(5) NOT NULL,
  `quantity` int(11) NOT NULL,
  `rate` float NOT NULL,
  `description` varchar(1000) NOT NULL,
  `discount` tinyint(1) NOT NULL,
  `receiving_date` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tblproducts`
--

INSERT INTO `tblproducts` (`id_product`, `id_category`, `name`, `image_thumb`, `image_large`, `cost_price`, `selling_price`, `color`, `size`, `quantity`, `rate`, `description`, `discount`, `receiving_date`) VALUES
('LMYK', 4, 'Quần kaki nam', 'https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png', '[\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\"\r\n]', '10000', '30000', '2', '2', 10, 5, 'Quần kaki Việt Nam', 1, ''),
('YKLM', 4, 'Quần âu nam', 'https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png', '[\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\"\r\n]', '10000', '20000', '1', '1', 10, 5, 'Quần âu Việt Nam', 0, '');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblpromotion`
--

CREATE TABLE `tblpromotion` (
  `id_promotion` int(11) NOT NULL,
  `title` int(11) NOT NULL,
  `min_value` int(11) NOT NULL,
  `max_value` int(11) NOT NULL,
  `percent` int(11) NOT NULL,
  `amount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblreceipt`
--

CREATE TABLE `tblreceipt` (
  `id_receipt` varchar(20) NOT NULL,
  `id_user` varchar(20) NOT NULL,
  `id_staff` varchar(20) NOT NULL,
  `order_date` int(11) NOT NULL,
  `delivering_date` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblsize`
--

CREATE TABLE `tblsize` (
  `id` int(11) NOT NULL,
  `size` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tblsize`
--

INSERT INTO `tblsize` (`id`, `size`) VALUES
(1, 'X'),
(2, 'M');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tblstaff`
--

CREATE TABLE `tblstaff` (
  `id_staff` varchar(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `avatar` varchar(200) NOT NULL,
  `email` varchar(100) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `address` varchar(100) NOT NULL,
  `birth` varchar(20) NOT NULL,
  `working_day` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbluser`
--

CREATE TABLE `tbluser` (
  `id_user` varchar(20) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `name_user` varchar(100) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  `avatar` varchar(200) DEFAULT NULL,
  `birth` varchar(20) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `sales` varchar(20) DEFAULT NULL,
  `created_at` varchar(20) NOT NULL,
  `updated_at` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbluser`
--

INSERT INTO `tbluser` (`id_user`, `email`, `password`, `name_user`, `phone_number`, `avatar`, `birth`, `sex`, `sales`, `created_at`, `updated_at`) VALUES
('20230308210544htYVTz', 'can1h@gmail.com', '$2y$10$/rpa12oDmf/1zmi1u3HomuxvmTWNtnfy2O7pumsy4A0XrV8fA3BCy', 'YKLM', NULL, NULL, NULL, NULL, NULL, '2023-03-08 21:05:44', '2023-03-08 21:05:44'),
('20230315222329gegGDE', 'asakitora1999@gmail.com', '$2y$10$jWn5cV0tVma8Bn5ZAtQFTutYHfhB9iKZ0lVDw05WY3UJ8bVcwuHFi', NULL, NULL, NULL, NULL, NULL, NULL, '2023-03-15 22:23:29', '2023-03-15 22:23:29');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `test`
--

CREATE TABLE `test` (
  `id` int(11) NOT NULL,
  `json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL CHECK (json_valid(`json`))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `test`
--

INSERT INTO `test` (`id`, `json`) VALUES
(1, '[\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\"\r\n]'),
(2, '[\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\",\r\n	\"https://img.vn/uploads/thuvien/singa-png-20220719150401Tdj1WAJFQr.png\"\r\n]');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tblcategory`
--
ALTER TABLE `tblcategory`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tblcolor`
--
ALTER TABLE `tblcolor`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tblitemizedreceipt`
--
ALTER TABLE `tblitemizedreceipt`
  ADD UNIQUE KEY `fk_itemized_receipt` (`id_receipt`,`code_product`) USING BTREE;

--
-- Chỉ mục cho bảng `tblnotification`
--
ALTER TABLE `tblnotification`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tblproducts`
--
ALTER TABLE `tblproducts`
  ADD PRIMARY KEY (`id_product`);

--
-- Chỉ mục cho bảng `tblpromotion`
--
ALTER TABLE `tblpromotion`
  ADD PRIMARY KEY (`id_promotion`);

--
-- Chỉ mục cho bảng `tblreceipt`
--
ALTER TABLE `tblreceipt`
  ADD PRIMARY KEY (`id_receipt`);

--
-- Chỉ mục cho bảng `tblsize`
--
ALTER TABLE `tblsize`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tblstaff`
--
ALTER TABLE `tblstaff`
  ADD PRIMARY KEY (`id_staff`);

--
-- Chỉ mục cho bảng `tbluser`
--
ALTER TABLE `tbluser`
  ADD PRIMARY KEY (`id_user`);

--
-- Chỉ mục cho bảng `test`
--
ALTER TABLE `test`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tblcategory`
--
ALTER TABLE `tblcategory`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT cho bảng `tblcolor`
--
ALTER TABLE `tblcolor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `tblnotification`
--
ALTER TABLE `tblnotification`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tblpromotion`
--
ALTER TABLE `tblpromotion`
  MODIFY `id_promotion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tblsize`
--
ALTER TABLE `tblsize`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT cho bảng `test`
--
ALTER TABLE `test`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
