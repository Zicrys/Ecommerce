-- phpMyAdmin SQL Dump
-- version 4.7.7
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Авг 11 2020 г., 22:43
-- Версия сервера: 5.6.38
-- Версия PHP: 5.6.32

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `phonedb`
--

-- --------------------------------------------------------

--
-- Структура таблицы `basketitem`
--

CREATE TABLE `basketitem` (
  `GoodCount` int(11) NOT NULL,
  `IdBasket` bigint(20) DEFAULT NULL,
  `IdGood` bigint(20) DEFAULT NULL,
  `Count` bigint(20) DEFAULT NULL,
  `IdStatus` bigint(20) DEFAULT NULL,
  `Price` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `basketitem`
--

INSERT INTO `basketitem` (`GoodCount`, `IdBasket`, `IdGood`, `Count`, `IdStatus`, `Price`) VALUES
(1, 1, 1, 1, 1, 16000),
(2, 1, 3, 1, 1, 10000),
(3, 4, 1, 1, 1, 16000),
(4, 4, 2, 1, 1, 20000),
(5, 4, 1, 1, 1, 16000),
(6, 6, 1, 1, 1, 16000),
(7, 6, 2, 1, 1, 20000),
(8, 8, 1, 1, 1, 16000),
(9, 8, 2, 1, 1, 20000);

-- --------------------------------------------------------

--
-- Структура таблицы `basketitems_tmp`
--

CREATE TABLE `basketitems_tmp` (
  `GoodCount` int(11) NOT NULL,
  `IdBasket` int(11) NOT NULL,
  `IdGood` int(11) NOT NULL,
  `Count` int(11) NOT NULL,
  `IdStatus` int(11) NOT NULL,
  `Price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `baskets`
--

CREATE TABLE `baskets` (
  `IdOrder` bigint(11) NOT NULL DEFAULT '0',
  `IdUser` bigint(11) NOT NULL,
  `IdBasket` int(11) NOT NULL,
  `StartDate` varchar(80) NOT NULL,
  `FinishDate` varchar(80) NOT NULL,
  `IdStatus` bigint(80) NOT NULL,
  `sumcount` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `baskets`
--

INSERT INTO `baskets` (`IdOrder`, `IdUser`, `IdBasket`, `StartDate`, `FinishDate`, `IdStatus`, `sumcount`) VALUES
(1, 3, 1, '2019.12.24', 'null', 0, 26000),
(2, 4, 4, '2020.04.18', 'null', 0, 52000),
(3, 5, 6, '2020.04.18', 'null', 0, 36000),
(4, 6, 8, '2020.08.11', '2020.08.11', 1, 36000);

-- --------------------------------------------------------

--
-- Структура таблицы `basketstatus`
--

CREATE TABLE `basketstatus` (
  `IdStatus` int(11) NOT NULL,
  `StatusName` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `basketstatus`
--

INSERT INTO `basketstatus` (`IdStatus`, `StatusName`) VALUES
(0, 'Открыта'),
(1, 'Закрыта');

-- --------------------------------------------------------

--
-- Структура таблицы `good`
--

CREATE TABLE `good` (
  `IdGood` bigint(20) NOT NULL,
  `IdType` bigint(20) NOT NULL,
  `IdMark` bigint(20) NOT NULL,
  `IdModel` varchar(80) NOT NULL,
  `Price` bigint(20) NOT NULL,
  `Count` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `good`
--

INSERT INTO `good` (`IdGood`, `IdType`, `IdMark`, `IdModel`, `Price`, `Count`) VALUES
(1, 2, 4, '1', 16000, 589),
(2, 2, 5, '2', 20000, 792),
(3, 2, 6, '3', 10000, 246),
(4, 4, 1, '5', 69990, 49),
(5, 4, 2, '6', 118890, 18),
(6, 2, 2, '7', 128999, 75),
(7, 1, 3, '8', 690, 99),
(8, 2, 3, '9', 16000, 30),
(9, 2, 6, '4', 7000, 58),
(10, 1, 7, '10', 2900, 25),
(11, 1, 8, '11', 2400, 10),
(12, 2, 9, '12', 30000, 55),
(13, 2, 10, '13', 4990, 37),
(14, 4, 11, '14', 80000, 25),
(15, 2, 2, '15', 60000, 70);

-- --------------------------------------------------------

--
-- Структура таблицы `informuserbasket`
--

CREATE TABLE `informuserbasket` (
  `Cnt` int(11) NOT NULL,
  `IdUser` int(11) NOT NULL,
  `IdBasket` int(11) NOT NULL,
  `IdStatus` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `informuserbasket`
--

INSERT INTO `informuserbasket` (`Cnt`, `IdUser`, `IdBasket`, `IdStatus`) VALUES
(1, 3, 1, 1),
(2, 3, 2, 0),
(3, 2, 3, 0),
(4, 4, 4, 1),
(5, 4, 5, 0),
(6, 5, 6, 1),
(7, 5, 7, 0),
(8, 6, 8, 1),
(9, 6, 9, 0);

-- --------------------------------------------------------

--
-- Структура таблицы `itemstatus`
--

CREATE TABLE `itemstatus` (
  `IdStatus` int(11) NOT NULL,
  `StatusName` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `itemstatus`
--

INSERT INTO `itemstatus` (`IdStatus`, `StatusName`) VALUES
(0, 'В корзине'),
(1, 'Подтвержден'),
(3, 'Нет на складе');

-- --------------------------------------------------------

--
-- Структура таблицы `mark`
--

CREATE TABLE `mark` (
  `IdMark` bigint(20) NOT NULL,
  `Name` varchar(80) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='Marks of Goods';

--
-- Дамп данных таблицы `mark`
--

INSERT INTO `mark` (`IdMark`, `Name`) VALUES
(1, 'ACER'),
(2, 'Apple'),
(3, 'Fly'),
(4, 'Samsung'),
(5, 'Nokia'),
(6, 'Alcatel'),
(7, 'Panasonic'),
(8, 'Siemens'),
(9, 'LG'),
(10, 'Motorolla'),
(11, 'hp');

-- --------------------------------------------------------

--
-- Структура таблицы `model`
--

CREATE TABLE `model` (
  `IdModel` bigint(20) NOT NULL,
  `IdMark` bigint(20) NOT NULL,
  `Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `model`
--

INSERT INTO `model` (`IdModel`, `IdMark`, `Name`) VALUES
(1, 4, 'Galaxy'),
(2, 5, '3310'),
(3, 6, 'pop 4'),
(4, 6, 'one touch'),
(5, 1, '15.6\" HELIOS 300 PH315-51-50NL (NH.Q3HER.007)'),
(6, 2, 'MacBook Pro 13 i5 /8/256 Gray Touch Bar MPXV2 2017'),
(7, 2, 'iPhone 11 PRO'),
(8, 3, 'FF190'),
(9, 3, 'LIFE GEO'),
(10, 7, 'KX-TU150RUB'),
(11, 8, 'A31'),
(12, 9, 'G7 fit'),
(13, 10, 'moto E5 Play 16GB Black'),
(14, 11, 'Notebook - 15-db1152ur'),
(15, 2, 'iphone X');

-- --------------------------------------------------------

--
-- Структура таблицы `photo`
--

CREATE TABLE `photo` (
  `IdPhoto` bigint(20) NOT NULL,
  `IdGood` bigint(20) NOT NULL,
  `Name` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `photo`
--

INSERT INTO `photo` (`IdPhoto`, `IdGood`, `Name`) VALUES
(1, 1, 'samsung.jpg'),
(2, 2, 'Nokia_3310.jpg'),
(3, 3, 'alcatelpop4.jpg'),
(4, 4, 'Acer.jpg'),
(5, 5, 'Apple.jpeg'),
(6, 6, 'apple.png'),
(7, 7, 'fly.jpg'),
(8, 8, 'fly1.jpg'),
(9, 9, 'alcatelonetouch.jpg'),
(10, 10, 'panasonic.png'),
(11, 11, 'siemens.jpg'),
(12, 12, 'lg.jpg'),
(13, 13, 'motorolla.png'),
(14, 14, 'hp.png'),
(15, 15, 'apple.png');

-- --------------------------------------------------------

--
-- Структура таблицы `statuses`
--

CREATE TABLE `statuses` (
  `IdStatus` bigint(20) NOT NULL,
  `StatusName` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `statuses`
--

INSERT INTO `statuses` (`IdStatus`, `StatusName`) VALUES
(0, 'В работе'),
(1, 'Отправлен');

-- --------------------------------------------------------

--
-- Структура таблицы `type`
--

CREATE TABLE `type` (
  `IdType` bigint(20) NOT NULL,
  `Name` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `type`
--

INSERT INTO `type` (`IdType`, `Name`) VALUES
(1, 'Сотовый телефон'),
(2, 'Смартфон'),
(3, 'Планшет'),
(4, 'Ноутбук');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `IdUser` bigint(20) NOT NULL,
  `Username` varchar(80) NOT NULL,
  `fio` varchar(80) NOT NULL,
  `phone` varchar(80) NOT NULL,
  `Password` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`IdUser`, `Username`, `fio`, `phone`, `Password`) VALUES
(2, 'admin', 'Иванова Елизавета Петровна', '89529119906', 'admin'),
(3, 'test1', 'Иванов', '34543535', 'test1'),
(4, 'terakor64', 'Петров Петр Перович', '22423424', 'terakor64'),
(5, 'demo1', 'Герасименко Сергей', '3534534535', 'demo1'),
(6, 'andreev', 'Андреев', '3233253', 'andreev');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `basketitem`
--
ALTER TABLE `basketitem`
  ADD PRIMARY KEY (`GoodCount`);

--
-- Индексы таблицы `basketitems_tmp`
--
ALTER TABLE `basketitems_tmp`
  ADD PRIMARY KEY (`GoodCount`);

--
-- Индексы таблицы `baskets`
--
ALTER TABLE `baskets`
  ADD PRIMARY KEY (`IdOrder`);

--
-- Индексы таблицы `good`
--
ALTER TABLE `good`
  ADD PRIMARY KEY (`IdGood`);

--
-- Индексы таблицы `informuserbasket`
--
ALTER TABLE `informuserbasket`
  ADD PRIMARY KEY (`Cnt`);

--
-- Индексы таблицы `mark`
--
ALTER TABLE `mark`
  ADD PRIMARY KEY (`IdMark`);

--
-- Индексы таблицы `model`
--
ALTER TABLE `model`
  ADD PRIMARY KEY (`IdModel`);

--
-- Индексы таблицы `photo`
--
ALTER TABLE `photo`
  ADD PRIMARY KEY (`IdPhoto`);

--
-- Индексы таблицы `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`IdType`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`IdUser`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `basketitem`
--
ALTER TABLE `basketitem`
  MODIFY `GoodCount` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `good`
--
ALTER TABLE `good`
  MODIFY `IdGood` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `informuserbasket`
--
ALTER TABLE `informuserbasket`
  MODIFY `Cnt` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT для таблицы `mark`
--
ALTER TABLE `mark`
  MODIFY `IdMark` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT для таблицы `model`
--
ALTER TABLE `model`
  MODIFY `IdModel` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `photo`
--
ALTER TABLE `photo`
  MODIFY `IdPhoto` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT для таблицы `type`
--
ALTER TABLE `type`
  MODIFY `IdType` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT для таблицы `user`
--
ALTER TABLE `user`
  MODIFY `IdUser` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
