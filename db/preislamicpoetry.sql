-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Dec 10, 2023 at 07:07 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `preislamicpoetry`
--

-- --------------------------------------------------------

--
-- Table structure for table `book`
--

CREATE TABLE `book` (
  `book_id` int(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author_name` varchar(20) DEFAULT NULL,
  `author's_death_date` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `poem`
--

CREATE TABLE `poem` (
  `poem_id` int(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `book_id_fk` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pos_tags`
--

CREATE TABLE `pos_tags` (
  `pos_id` int(11) NOT NULL,
  `tags` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `pos_token`
--

CREATE TABLE `pos_token` (
  `pos_token_id` int(11) NOT NULL,
  `pos_fk` int(11) NOT NULL,
  `token_fk` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `root`
--

CREATE TABLE `root` (
  `root_id` int(50) NOT NULL,
  `root_word` varchar(20) NOT NULL,
  `is_verified` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `root_token`
--

CREATE TABLE `root_token` (
  `root_token_id` int(50) NOT NULL,
  `root_fk` int(50) NOT NULL,
  `token_fk` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `token`
--

CREATE TABLE `token` (
  `token_id` int(50) NOT NULL,
  `tokenized_words` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `user_id` int(100) UNSIGNED NOT NULL,
  `name` varchar(25) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(20) NOT NULL,
  `is_admin` tinyint(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `verse`
--

CREATE TABLE `verse` (
  `verse_id` int(50) NOT NULL,
  `poem_id_fk` int(50) NOT NULL,
  `misra_1` varchar(100) NOT NULL,
  `misra_2` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `verse_root`
--

CREATE TABLE `verse_root` (
  `verse_root_id` int(50) NOT NULL,
  `root_id_fk` int(50) NOT NULL,
  `verse_id_fk` int(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `verse_token`
--

CREATE TABLE `verse_token` (
  `verse_token_id` int(11) NOT NULL,
  `token_fk` int(11) DEFAULT NULL,
  `verse_fk` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `book`
--
ALTER TABLE `book`
  ADD PRIMARY KEY (`book_id`),
  ADD UNIQUE KEY `unique_book_title` (`title`);

--
-- Indexes for table `poem`
--
ALTER TABLE `poem`
  ADD PRIMARY KEY (`poem_id`),
  ADD UNIQUE KEY `unique_poem_title` (`title`),
  ADD KEY `book_id_FK` (`book_id_fk`);

--
-- Indexes for table `pos_tags`
--
ALTER TABLE `pos_tags`
  ADD PRIMARY KEY (`pos_id`),
  ADD UNIQUE KEY `unique_pos_tags` (`tags`);

--
-- Indexes for table `pos_token`
--
ALTER TABLE `pos_token`
  ADD PRIMARY KEY (`pos_token_id`),
  ADD KEY `pos_fk` (`pos_fk`),
  ADD KEY `token_id_fk` (`token_fk`);

--
-- Indexes for table `root`
--
ALTER TABLE `root`
  ADD PRIMARY KEY (`root_id`),
  ADD UNIQUE KEY `root_word` (`root_word`),
  ADD UNIQUE KEY `unique_root_word` (`root_word`);

--
-- Indexes for table `root_token`
--
ALTER TABLE `root_token`
  ADD PRIMARY KEY (`root_token_id`),
  ADD KEY `root_fk` (`root_fk`),
  ADD KEY `token_fk` (`token_fk`);

--
-- Indexes for table `token`
--
ALTER TABLE `token`
  ADD PRIMARY KEY (`token_id`),
  ADD UNIQUE KEY `tokenized_words` (`tokenized_words`),
  ADD UNIQUE KEY `unique_tokenized_word` (`tokenized_words`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `verse`
--
ALTER TABLE `verse`
  ADD PRIMARY KEY (`verse_id`),
  ADD UNIQUE KEY `unique_verse_in_poem` (`poem_id_fk`,`misra_1`,`misra_2`),
  ADD KEY `Verse_Poem_FK` (`poem_id_fk`);

--
-- Indexes for table `verse_root`
--
ALTER TABLE `verse_root`
  ADD PRIMARY KEY (`verse_root_id`),
  ADD KEY `VerseRoot_Verse_FK` (`verse_id_fk`),
  ADD KEY `VerseRoot_Root_FK` (`root_id_fk`);

--
-- Indexes for table `verse_token`
--
ALTER TABLE `verse_token`
  ADD PRIMARY KEY (`verse_token_id`),
  ADD KEY `fk_token` (`token_fk`),
  ADD KEY `fk_verse` (`verse_fk`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `book`
--
ALTER TABLE `book`
  MODIFY `book_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `poem`
--
ALTER TABLE `poem`
  MODIFY `poem_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `pos_tags`
--
ALTER TABLE `pos_tags`
  MODIFY `pos_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `pos_token`
--
ALTER TABLE `pos_token`
  MODIFY `pos_token_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `root`
--
ALTER TABLE `root`
  MODIFY `root_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- AUTO_INCREMENT for table `root_token`
--
ALTER TABLE `root_token`
  MODIFY `root_token_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=260;

--
-- AUTO_INCREMENT for table `token`
--
ALTER TABLE `token`
  MODIFY `token_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=109;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `user_id` int(100) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `verse`
--
ALTER TABLE `verse`
  MODIFY `verse_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=395;

--
-- AUTO_INCREMENT for table `verse_root`
--
ALTER TABLE `verse_root`
  MODIFY `verse_root_id` int(50) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT for table `verse_token`
--
ALTER TABLE `verse_token`
  MODIFY `verse_token_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=220;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `poem`
--
ALTER TABLE `poem`
  ADD CONSTRAINT `book_id_FK` FOREIGN KEY (`book_id_fk`) REFERENCES `book` (`book_id`);

--
-- Constraints for table `pos_token`
--
ALTER TABLE `pos_token`
  ADD CONSTRAINT `pos_fk` FOREIGN KEY (`pos_fk`) REFERENCES `pos_tags` (`pos_id`),
  ADD CONSTRAINT `token_id_fk` FOREIGN KEY (`token_fk`) REFERENCES `token` (`token_id`);

--
-- Constraints for table `root_token`
--
ALTER TABLE `root_token`
  ADD CONSTRAINT `root_fk` FOREIGN KEY (`root_fk`) REFERENCES `root` (`root_id`),
  ADD CONSTRAINT `token_fk` FOREIGN KEY (`token_fk`) REFERENCES `token` (`token_id`);

--
-- Constraints for table `verse`
--
ALTER TABLE `verse`
  ADD CONSTRAINT `Verse_Poem_FK` FOREIGN KEY (`poem_id_fk`) REFERENCES `poem` (`poem_id`);

--
-- Constraints for table `verse_root`
--
ALTER TABLE `verse_root`
  ADD CONSTRAINT `VerseRoot_Root_FK` FOREIGN KEY (`root_id_fk`) REFERENCES `root` (`root_id`),
  ADD CONSTRAINT `VerseRoot_Verse_FK` FOREIGN KEY (`verse_id_fk`) REFERENCES `verse` (`verse_id`);

--
-- Constraints for table `verse_token`
--
ALTER TABLE `verse_token`
  ADD CONSTRAINT `fk_token` FOREIGN KEY (`token_fk`) REFERENCES `token` (`token_id`),
  ADD CONSTRAINT `fk_verse` FOREIGN KEY (`verse_fk`) REFERENCES `verse` (`verse_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
