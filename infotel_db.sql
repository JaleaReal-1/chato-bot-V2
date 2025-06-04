-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 04, 2025 at 02:35 PM
-- Server version: 8.0.30
-- PHP Version: 8.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `infotel_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `compra`
--

CREATE TABLE `compra` (
  `id` bigint NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre_cliente` varchar(255) DEFAULT NULL,
  `telefono` varchar(255) DEFAULT NULL,
  `tipo_envio` varchar(255) DEFAULT NULL,
  `conjunto_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `conjunto`
--

CREATE TABLE `conjunto` (
  `id` bigint NOT NULL,
  `cliente_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `conjunto`
--

INSERT INTO `conjunto` (`id`, `cliente_id`) VALUES
(1, NULL),
(2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `conjunto_prendas`
--

CREATE TABLE `conjunto_prendas` (
  `conjunto_id` bigint NOT NULL,
  `prendas_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `conjunto_prendas`
--

INSERT INTO `conjunto_prendas` (`conjunto_id`, `prendas_id`) VALUES
(1, 1),
(1, 3),
(1, 4),
(2, 5);

-- --------------------------------------------------------

--
-- Table structure for table `prenda`
--

CREATE TABLE `prenda` (
  `id` bigint NOT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `color` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `material` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `prenda`
--

INSERT INTO `prenda` (`id`, `tipo`, `color`, `descripcion`, `imagen_url`, `nombre`, `precio`, `material`, `imagen`) VALUES
(1, 'chaqueta', 'cafe', 'una chaqueta de color cafe especialmente para el frio de los andes peruanos', 'chaqueta.glb', 'Chaqueta Andina\n', 89.9, 'lana de alpaca', NULL),
(2, 'chaleco', 'gris , oscuro', 'Chaleco tejido de corte recto, ideal para climas frescos, con diseño asimétrico y bolsillos frontales amplios.', 'CARDIGAN_CHINO.glb', 'Cardigan Chino', 79.9, 'lana de alpaca', NULL),
(3, 'vestido', 'llamita, cafe', 'Vestido corto que usualmente se usa para eventos formales, trabajo y actividades al aire libre.', 'polivestido.glb', 'Poli Vestido Tortuga', 129.9, 'tela fina', NULL),
(4, 'vestido_capela', 'gris , con bolsillos decorados blanco, marron, negro, en forma circular', 'Ideal para temporada de frio tejido de alpaca y capucha para el frio y para estar vestido para dias casuales.\ncon capucha y bolsillos', 'polivestido_capela.glb', 'Poli Vestido Capela', 149.9, 'lana de alpaca', NULL),
(5, 'Poncho', 'Azul marino con franjas beige y marrón', 'Poncho artesanal de corte amplio, abotonado en el cuello, ideal para clima frío con estilo tradicional andino.', 'Poncho_tortuga.glb', 'Poncho Tortuga', 99.9, 'lana de aplaca', NULL),
(6, 'Chompa', 'Azul con patrones en relieve', 'Chompa de punto azul con diseño de texturas variadas, ideal para un look casual y abrigado en tiempos de frio. ', 'SWEATER_PUNTO_VARON.glb', 'Chompa Andina', 89.9, 'lana de alpaca', NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `compra`
--
ALTER TABLE `compra`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKp4yikuckpoeb5rpuv2yeew6b0` (`conjunto_id`);

--
-- Indexes for table `conjunto`
--
ALTER TABLE `conjunto`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `conjunto_prendas`
--
ALTER TABLE `conjunto_prendas`
  ADD KEY `FKa3n7lx1eops57yb60uihmn90s` (`prendas_id`),
  ADD KEY `FKq6t29of5iqhjank04d3g8gmdq` (`conjunto_id`);

--
-- Indexes for table `prenda`
--
ALTER TABLE `prenda`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `compra`
--
ALTER TABLE `compra`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `conjunto`
--
ALTER TABLE `conjunto`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `prenda`
--
ALTER TABLE `prenda`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `compra`
--
ALTER TABLE `compra`
  ADD CONSTRAINT `FKkkx39ux4xjstyksscr99mqkac` FOREIGN KEY (`conjunto_id`) REFERENCES `conjunto` (`id`);

--
-- Constraints for table `conjunto_prendas`
--
ALTER TABLE `conjunto_prendas`
  ADD CONSTRAINT `FKa3n7lx1eops57yb60uihmn90s` FOREIGN KEY (`prendas_id`) REFERENCES `prenda` (`id`),
  ADD CONSTRAINT `FKq6t29of5iqhjank04d3g8gmdq` FOREIGN KEY (`conjunto_id`) REFERENCES `conjunto` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
