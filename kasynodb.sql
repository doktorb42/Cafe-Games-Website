-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Maj 09, 2026 at 07:59 PM
-- Wersja serwera: 10.4.32-MariaDB
-- Wersja PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `kasynodb`
--

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `game_history`
--

CREATE TABLE `game_history` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `game_name` varchar(50) NOT NULL,
  `bet` int(11) NOT NULL,
  `result` varchar(50) NOT NULL,
  `balance_change` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `game_history`
--

INSERT INTO `game_history` (`id`, `user_id`, `game_name`, `bet`, `result`, `balance_change`, `created_at`) VALUES
(1, 1, 'SLOT', 100, 'WIN', 200, '2026-04-20 10:10:00'),
(2, 1, 'BLACKJACK', 50, 'LOSE', -50, '2026-04-20 10:15:00'),
(3, 2, 'SLOT', 200, 'LOSE', -200, '2026-04-20 11:00:00'),
(4, 2, 'BLACKJACK', 100, 'WIN', 200, '2026-04-20 11:10:00'),
(5, 3, 'SLOT', 50, 'WIN', 100, '2026-04-21 08:00:00'),
(6, 3, 'SLOT', 75, 'LOSE', -75, '2026-04-21 08:05:00'),
(7, 4, 'BLACKJACK', 150, 'WIN', 300, '2026-04-21 09:00:00'),
(8, 1, 'SLOT', 300, 'WIN', 600, '2026-04-22 07:00:00'),
(9, 2, 'BLACKJACK', 100, 'LOSE', -100, '2026-04-22 07:30:00'),
(10, 3, 'SLOT', 200, 'WIN', 400, '2026-04-22 08:00:00');

-- --------------------------------------------------------

--
-- Struktura tabeli dla tabeli `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `login` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(50) NOT NULL,
  `balance` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `login`, `password`, `username`, `balance`) VALUES
(1, 'adam', '$2a$10$abc123fakehash', 'Adi', 1200),
(2, 'kasia', '$2a$10$abc123fakehash', 'KasiaPro', 900),
(3, 'michal', '$2a$10$abc123fakehash', 'Miki', 1500),
(4, 'ola', '$2a$10$abc123fakehash', 'Olaaa', 700),
(5, 'Daw123', '$2a$10$BlL/8yxdvk8SFKtOagaZmupBYsHZHGI1W.a/TcuUgs47SOgOp.N6C', 'Dawid', 13348),
(6, 'Daw125', '$2a$10$wymhu0Uw6AZq1j8mHHXkGOhU3DbjajVW9KD2/C0pde8wt2cxWkoMq', 'Monika', 1000);

--
-- Indeksy dla zrzutów tabel
--

--
-- Indeksy dla tabeli `game_history`
--
ALTER TABLE `game_history`
  ADD PRIMARY KEY (`id`);

--
-- Indeksy dla tabeli `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`login`),
  ADD UNIQUE KEY `nickname` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `game_history`
--
ALTER TABLE `game_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
