CREATE DATABASE  IF NOT EXISTS `qlsv` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `qlsv`;
-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: qlsv
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tbl_diemso`
--

DROP TABLE IF EXISTS `tbl_diemso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_diemso` (
  `maDiem` int NOT NULL AUTO_INCREMENT,
  `maSV` varchar(20) NOT NULL,
  `maMH` varchar(20) NOT NULL,
  `diemSo` double NOT NULL,
  PRIMARY KEY (`maDiem`),
  UNIQUE KEY `uq_sv_mh` (`maSV`,`maMH`),
  KEY `maMH` (`maMH`),
  CONSTRAINT `tbl_diemso_ibfk_1` FOREIGN KEY (`maSV`) REFERENCES `tbl_sinhvien` (`maSV`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tbl_diemso_ibfk_2` FOREIGN KEY (`maMH`) REFERENCES `tbl_monhoc` (`maMH`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_diemso`
--

LOCK TABLES `tbl_diemso` WRITE;
/*!40000 ALTER TABLE `tbl_diemso` DISABLE KEYS */;
INSERT INTO `tbl_diemso` VALUES (2,'SV001','CSDL',7),(3,'SV001','TOAN',9),(4,'SV002','LTJAVA',6.5),(5,'SV002','CSDL',8),(6,'SV003','LTJAVA',4.5),(7,'SV003','TOAN',5.5),(8,'SV004','CSDL',9.5),(9,'SV004','MANG',8),(10,'SV005','LTJAVA',7.5),(11,'SV005','CTDLGT',6),(12,'SV003','MANG',9),(13,'SV001','MANG',9),(19,'SV001','LTJAVA',9),(20,'SV006','ITWS',6.66),(21,'SV007','CSDL',9),(22,'SV007','MANG',0);
/*!40000 ALTER TABLE `tbl_diemso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_giangvien`
--

DROP TABLE IF EXISTS `tbl_giangvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_giangvien` (
  `maGV` varchar(20) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `ngaySinh` date DEFAULT NULL,
  `gioiTinh` varchar(10) DEFAULT NULL,
  `khoaBoMon` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sdt` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`maGV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_giangvien`
--

LOCK TABLES `tbl_giangvien` WRITE;
/*!40000 ALTER TABLE `tbl_giangvien` DISABLE KEYS */;
INSERT INTO `tbl_giangvien` VALUES ('GV001','Nguyễn Văn An','1980-05-15','Nam','Công nghệ thông tin','nguyenvanan@uni.edu.vn','0901234567'),('GV002','Trần Thị Bình','1985-08-20','Nữ','Kỹ thuật phần mềm','tranthihinh@uni.edu.vn','0912345678'),('GV003','Lê Văn Cường','1978-03-10','Nam','Hệ thống thông tin','levancuong@uni.edu.vn','0923456789'),('GV004','Nguyễn Tiến A','1989-12-10','Nam','Công nghệ thông tin','nguyenvana@vnuf.edu.vn','0901234566');
/*!40000 ALTER TABLE `tbl_giangvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_khoa`
--

DROP TABLE IF EXISTS `tbl_khoa`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_khoa` (
  `maKhoa` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tenKhoa` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `moTa` text COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`maKhoa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_khoa`
--

LOCK TABLES `tbl_khoa` WRITE;
/*!40000 ALTER TABLE `tbl_khoa` DISABLE KEYS */;
INSERT INTO `tbl_khoa` VALUES ('CNTT','Công nghệ Thông tin','Khoa đào tạo kỹ sư phần mềm, mạng máy tính'),('DTVT','Điện tử Viễn thông','Khoa đào tạo kỹ sư điện tử, viễn thông'),('KTKT','Kỹ thuật Kinh tế','Khoa đào tạo kỹ sư kinh tế, quản lý dự án'),('KTPM','Kỹ thuật Phần mềm','Khoa đào tạo kỹ sư phần mềm'),('NGON','Ngôn ngữ Anh','Khoa đào tạo cử nhân ngôn ngữ Anh'),('QTKD','Quản trị Kinh doanh','Khoa đào tạo cử nhân quản trị kinh doanh');
/*!40000 ALTER TABLE `tbl_khoa` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_lophoc`
--

DROP TABLE IF EXISTS `tbl_lophoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_lophoc` (
  `maLop` varchar(20) NOT NULL,
  `tenLop` varchar(100) NOT NULL,
  `khoaHoc` varchar(50) NOT NULL,
  `siSo` int DEFAULT '0',
  PRIMARY KEY (`maLop`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_lophoc`
--

LOCK TABLES `tbl_lophoc` WRITE;
/*!40000 ALTER TABLE `tbl_lophoc` DISABLE KEYS */;
INSERT INTO `tbl_lophoc` VALUES ('CNTT01','Công nghệ thông tin K01','2022-2026',40),('CNTT02','Công nghệ thông tin K02','2022-2026',38),('CNTTK68','Công nghệ thông tin K68','2023-2027',50),('HTTT- K68A','Hệ thống thông tin','2023-2027',50),('KTPM01','Kỹ thuật phần mềm K01','2023-2027',35);
/*!40000 ALTER TABLE `tbl_lophoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_monhoc`
--

DROP TABLE IF EXISTS `tbl_monhoc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_monhoc` (
  `maMH` varchar(20) NOT NULL,
  `tenMH` varchar(100) NOT NULL,
  `soTinChi` int DEFAULT '3',
  PRIMARY KEY (`maMH`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_monhoc`
--

LOCK TABLES `tbl_monhoc` WRITE;
/*!40000 ALTER TABLE `tbl_monhoc` DISABLE KEYS */;
INSERT INTO `tbl_monhoc` VALUES ('CSDL','Cơ sở dữ liệu',3),('CTDLGT','Cấu trúc dữ liệu',3),('ITWS','Windows Server',3),('LTJAVA','Lập trình Java',4),('MANG','Mạng máy tính',3),('TOAN','Toán rời rạc',2);
/*!40000 ALTER TABLE `tbl_monhoc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_sinhvien`
--

DROP TABLE IF EXISTS `tbl_sinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_sinhvien` (
  `maSV` varchar(20) NOT NULL,
  `hoTen` varchar(100) NOT NULL,
  `ngaySinh` date DEFAULT NULL,
  `gioiTinh` varchar(10) DEFAULT NULL,
  `maLop` varchar(20) DEFAULT NULL,
  `maKhoa` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `sdt` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`maSV`),
  KEY `maLop` (`maLop`),
  CONSTRAINT `tbl_sinhvien_ibfk_1` FOREIGN KEY (`maLop`) REFERENCES `tbl_lophoc` (`maLop`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_sinhvien`
--

LOCK TABLES `tbl_sinhvien` WRITE;
/*!40000 ALTER TABLE `tbl_sinhvien` DISABLE KEYS */;
INSERT INTO `tbl_sinhvien` VALUES ('SV001','Nguyễn Minh Anh','2004-01-15','Nam','CNTT01','CNTT','minhanh@vnuf.edu.vn','0931234567'),('SV002','Trần Thị B','2004-03-22','Nữ','CNTT01','CNTT','thib@vnuf.edu.vn','0942345678'),('SV003','Lê Văn Chiến','2004-07-08','Nam','CNTT02','CNTT','vanchien@vnuf.edu.vn','0953456789'),('SV004','Phạm Thị Dung','2003-11-30','Nữ','KTPM01','KTPM','thidung@vnuf.edu.vn','0964567890'),('SV005','Hoàng Văn Anh','2004-05-12','Nam','CNTT01','CNTT','vananh@vnuf.edu.vn','0975678901'),('SV006','Nguyễn Văn A','2005-12-31','Nam','KTPM01','KTPM','nva@vnuf.edu.vn','0812321232'),('SV007','Vũ Trung C','2005-12-10','Nam','KTPM01','KTPM','vutrungc@vnuf.edu.vn','0912345124');
/*!40000 ALTER TABLE `tbl_sinhvien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_taikhoan`
--

DROP TABLE IF EXISTS `tbl_taikhoan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_taikhoan` (
  `tenDN` varchar(50) NOT NULL,
  `matKhau` varchar(64) NOT NULL,
  `vaiTro` varchar(20) NOT NULL,
  `maSV` varchar(20) DEFAULT NULL,
  `maGV` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`tenDN`),
  KEY `maSV` (`maSV`),
  KEY `maGV` (`maGV`),
  CONSTRAINT `tbl_taikhoan_ibfk_1` FOREIGN KEY (`maSV`) REFERENCES `tbl_sinhvien` (`maSV`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `tbl_taikhoan_ibfk_2` FOREIGN KEY (`maGV`) REFERENCES `tbl_giangvien` (`maGV`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_taikhoan`
--

LOCK TABLES `tbl_taikhoan` WRITE;
/*!40000 ALTER TABLE `tbl_taikhoan` DISABLE KEYS */;
INSERT INTO `tbl_taikhoan` VALUES ('admin','4acb4bc224acbbe3c2bfdcaa39a4324e','admin',NULL,NULL),('gv001','a809f1afcfd2fafefb60e96db7721ff6','giangvien',NULL,'GV001'),('gv002','3916d3e5a0a497420f8da80ef98c3aa9','giangvien',NULL,'GV002'),('sv001','343b7868ffdd9160caaa5cbda4c6303f','sinhvien','SV001',NULL),('sv002','d4eddc76b4e3a24472e0052b54bf189d','sinhvien','SV002',NULL),('sv003','343b7868ffdd9160caaa5cbda4c6303f','sinhvien','SV003',NULL);
/*!40000 ALTER TABLE `tbl_taikhoan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_diemtb_sinhvien`
--

DROP TABLE IF EXISTS `v_diemtb_sinhvien`;
/*!50001 DROP VIEW IF EXISTS `v_diemtb_sinhvien`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_diemtb_sinhvien` AS SELECT 
 1 AS `maSV`,
 1 AS `hoTen`,
 1 AS `tenLop`,
 1 AS `diemTB`,
 1 AS `hocLuc`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_thongke_hocluc`
--

DROP TABLE IF EXISTS `v_thongke_hocluc`;
/*!50001 DROP VIEW IF EXISTS `v_thongke_hocluc`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_thongke_hocluc` AS SELECT 
 1 AS `maSV`,
 1 AS `diemTB`,
 1 AS `hocLuc`*/;
SET character_set_client = @saved_cs_client;

--
-- Final view structure for view `v_diemtb_sinhvien`
--

/*!50001 DROP VIEW IF EXISTS `v_diemtb_sinhvien`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_diemtb_sinhvien` AS select `sv`.`maSV` AS `maSV`,`sv`.`hoTen` AS `hoTen`,`lop`.`tenLop` AS `tenLop`,round(avg(`d`.`diemSo`),2) AS `diemTB`,(case when (avg(`d`.`diemSo`) >= 9.5) then 'A+' when (avg(`d`.`diemSo`) >= 9.0) then 'A' when (avg(`d`.`diemSo`) >= 8.5) then 'A-' when (avg(`d`.`diemSo`) >= 8.0) then 'B+' when (avg(`d`.`diemSo`) >= 7.5) then 'B' when (avg(`d`.`diemSo`) >= 7.0) then 'B-' when (avg(`d`.`diemSo`) >= 6.5) then 'C+' when (avg(`d`.`diemSo`) >= 6.0) then 'C' when (avg(`d`.`diemSo`) >= 5.5) then 'D+' when (avg(`d`.`diemSo`) >= 5.0) then 'D' else 'F' end) AS `hocLuc` from ((`tbl_sinhvien` `sv` left join `tbl_lophoc` `lop` on((`sv`.`maLop` = `lop`.`maLop`))) left join `tbl_diemso` `d` on((`sv`.`maSV` = `d`.`maSV`))) group by `sv`.`maSV`,`sv`.`hoTen`,`lop`.`tenLop` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_thongke_hocluc`
--

/*!50001 DROP VIEW IF EXISTS `v_thongke_hocluc`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_thongke_hocluc` AS select `tbl_diemso`.`maSV` AS `maSV`,avg(`tbl_diemso`.`diemSo`) AS `diemTB`,(case when (avg(`tbl_diemso`.`diemSo`) >= 9.5) then 'A+' when (avg(`tbl_diemso`.`diemSo`) >= 9.0) then 'A' when (avg(`tbl_diemso`.`diemSo`) >= 8.5) then 'A-' when (avg(`tbl_diemso`.`diemSo`) >= 8.0) then 'B+' when (avg(`tbl_diemso`.`diemSo`) >= 7.5) then 'B' when (avg(`tbl_diemso`.`diemSo`) >= 7.0) then 'B-' when (avg(`tbl_diemso`.`diemSo`) >= 6.5) then 'C+' when (avg(`tbl_diemso`.`diemSo`) >= 6.0) then 'C' when (avg(`tbl_diemso`.`diemSo`) >= 5.5) then 'D+' when (avg(`tbl_diemso`.`diemSo`) >= 5.0) then 'D' else 'F' end) AS `hocLuc` from `tbl_diemso` group by `tbl_diemso`.`maSV` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-25  1:34:06
