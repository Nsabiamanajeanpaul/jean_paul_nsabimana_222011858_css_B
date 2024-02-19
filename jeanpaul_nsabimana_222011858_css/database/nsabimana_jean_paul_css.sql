-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 19, 2024 at 11:10 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `nsabimana_jean_paul_css`
--

-- --------------------------------------------------------

--
-- Table structure for table `carmanufacturers`
--

CREATE TABLE `carmanufacturers` (
  `manufacturer_id` int(11) NOT NULL,
  `manufacturer_name` varchar(255) NOT NULL,
  `country` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carmanufacturers`
--

INSERT INTO `carmanufacturers` (`manufacturer_id`, `manufacturer_name`, `country`) VALUES
(1, 'Toyota', 'Japan'),
(2, 'got', 'rwand'),
(3, 'Honda', 'Japan'),
(4, '', ''),
(5, 'ASA', 'ASDSA');

-- --------------------------------------------------------

--
-- Table structure for table `carmodels`
--

CREATE TABLE `carmodels` (
  `model_id` int(11) NOT NULL,
  `model_name` varchar(255) NOT NULL,
  `manufacturer_id` int(11) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `fuel_type` varchar(50) DEFAULT NULL,
  `engine_size` decimal(5,2) DEFAULT NULL,
  `transmission_type` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `carmodels`
--

INSERT INTO `carmodels` (`model_id`, `model_name`, `manufacturer_id`, `year`, `fuel_type`, `engine_size`, `transmission_type`) VALUES
(1, 'Camry', 1, 2022, 'Gasoline', 2.50, 'Automatic'),
(2, 'F-150', 2, 2021, 'Gasoline', 3.50, 'Automatic'),
(3, 'Civic', 3, 2022, 'Gasoline', 2.00, 'Automatic');

-- --------------------------------------------------------

--
-- Table structure for table `platformfeedback`
--

CREATE TABLE `platformfeedback` (
  `feedback_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `feedback_comments` text DEFAULT NULL,
  `feedback_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `platformfeedback`
--

INSERT INTO `platformfeedback` (`feedback_id`, `user_id`, `feedback_comments`, `feedback_date`) VALUES
(1, 2, 'The platform is user-friendly and intuitive.', '2022-12-15'),
(2, 3, 'More features would be appreciated.', '2022-12-20');

-- --------------------------------------------------------

--
-- Table structure for table `surveys`
--

CREATE TABLE `surveys` (
  `survey_id` int(11) NOT NULL,
  `user_id` int(11) DEFAULT NULL,
  `model_id` int(11) DEFAULT NULL,
  `rating` int(11) DEFAULT NULL,
  `comments` text DEFAULT NULL,
  `survey_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `surveys`
--

INSERT INTO `surveys` (`survey_id`, `user_id`, `model_id`, `rating`, `comments`, `survey_date`) VALUES
(1, 2, 1, 4, 'Great car, excellent fuel efficiency!', '2022-12-01'),
(2, 3, 2, 5, 'Powerful pickup, love it!', '2022-12-05'),
(3, 3, 3, 3, 'Decent car, but could be better.', '2022-12-10'),
(5, 3, 3, 7, 'bad', '2002-02-08');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`) VALUES
(1, 'admin', 'adminpassword', 'admin'),
(2, 'user1', 'user1password', 'user'),
(3, 'user2', 'user2password', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `carmanufacturers`
--
ALTER TABLE `carmanufacturers`
  ADD PRIMARY KEY (`manufacturer_id`);

--
-- Indexes for table `carmodels`
--
ALTER TABLE `carmodels`
  ADD PRIMARY KEY (`model_id`),
  ADD KEY `manufacturer_id` (`manufacturer_id`);

--
-- Indexes for table `platformfeedback`
--
ALTER TABLE `platformfeedback`
  ADD PRIMARY KEY (`feedback_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `surveys`
--
ALTER TABLE `surveys`
  ADD PRIMARY KEY (`survey_id`),
  ADD KEY `user_id` (`user_id`),
  ADD KEY `model_id` (`model_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `carmanufacturers`
--
ALTER TABLE `carmanufacturers`
  MODIFY `manufacturer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `carmodels`
--
ALTER TABLE `carmodels`
  MODIFY `model_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `platformfeedback`
--
ALTER TABLE `platformfeedback`
  MODIFY `feedback_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `surveys`
--
ALTER TABLE `surveys`
  MODIFY `survey_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `carmodels`
--
ALTER TABLE `carmodels`
  ADD CONSTRAINT `carmodels_ibfk_1` FOREIGN KEY (`manufacturer_id`) REFERENCES `carmanufacturers` (`manufacturer_id`);

--
-- Constraints for table `platformfeedback`
--
ALTER TABLE `platformfeedback`
  ADD CONSTRAINT `platformfeedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `surveys`
--
ALTER TABLE `surveys`
  ADD CONSTRAINT `surveys_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  ADD CONSTRAINT `surveys_ibfk_2` FOREIGN KEY (`model_id`) REFERENCES `carmodels` (`model_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
