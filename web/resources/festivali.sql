-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 12, 2018 at 10:49 PM
-- Server version: 5.7.14
-- PHP Version: 5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `festivali`
--

-- --------------------------------------------------------

--
-- Table structure for table `crnalista`
--

CREATE TABLE `crnalista` (
  `IdCrnaLista` int(11) NOT NULL,
  `IdKorisnik` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `festival`
--

CREATE TABLE `festival` (
  `IdFestival` int(11) NOT NULL,
  `Naziv` varchar(50) DEFAULT NULL,
  `Mesto` varchar(20) DEFAULT NULL,
  `StatusFestival` varchar(20) DEFAULT NULL,
  `DatumPocetka` timestamp NULL DEFAULT NULL,
  `DatumKraja` timestamp NULL DEFAULT NULL,
  `Cena` decimal(10,2) DEFAULT NULL,
  `KratakOpis` text
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `festival`
--

INSERT INTO `festival` (`IdFestival`, `Naziv`, `Mesto`, `StatusFestival`, `DatumPocetka`, `DatumKraja`, `Cena`, `KratakOpis`) VALUES
(36, 'Fest', 'Beograd', 'u toku', '2018-02-09 23:00:00', '2018-02-19 23:00:00', '1000.00', 'Me?unarodni filmski festival - FEST održava se svake godine u Beogradu. Prvi FEST je održan izme?u 8. i 16. januara 1971. pod sloganom „Hrabri novi svet“, u Domu sindikata. Festival je otvoren filmom „Meš“ Roberta Altmana.'),
(35, 'Bitef', 'Beograd', 'nije poceo', '2018-02-14 23:00:00', '2018-02-20 23:00:00', '1000.00', 'BITEF (Beogradski internacionalni teatarski festival) je jedan od pozorišnih festivala koji se organizuje u Beogradu svake godine.\r\n'),
(34, 'Hercegnovski filmski festival', 'Herceg Novi', 'nije poceo', '2018-09-01 01:54:19', '2018-08-01 01:54:19', NULL, 'Jugoslovenski filmski festival osnovan je 1986. godine, a danasnji naziv nosi od 2002. godine'),
(33, '46. Fest', 'Beograd', 'nije poceo', '2018-03-23 02:54:19', '2018-02-23 02:54:19', NULL, 'Prvi FEST  pod naslovom Hrabri novi svet otvoren je  9. januara 1971. u Domu Sindikata');

-- --------------------------------------------------------

--
-- Table structure for table `film`
--

CREATE TABLE `film` (
  `IdFilm` int(11) NOT NULL,
  `Naziv` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `OriginalniNaziv` varchar(30) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `SlikaPoster` varchar(50) DEFAULT NULL,
  `Reziser` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `KratakOpis` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `Godina` varchar(10) DEFAULT NULL,
  `GlavniGlumci` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `Trajanje` varchar(20) DEFAULT NULL,
  `ZemljaPorekla` varchar(20) DEFAULT NULL,
  `LinkIMDB` mediumtext,
  `LinkRotten` mediumtext,
  `LinkTrailer` mediumtext,
  `Rejting` double DEFAULT NULL,
  `BrojOcena` int(11) DEFAULT '0'
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `film`
--

INSERT INTO `film` (`IdFilm`, `Naziv`, `OriginalniNaziv`, `SlikaPoster`, `Reziser`, `KratakOpis`, `Godina`, `GlavniGlumci`, `Trajanje`, `ZemljaPorekla`, `LinkIMDB`, `LinkRotten`, `LinkTrailer`, `Rejting`, `BrojOcena`) VALUES
(1, 'Placenici', 'The Expendables', 'theexpendables.jpg', 'Sylvester Stallone', 'A CIA operative hires a team of mercenaries to eliminate a Latin dictator and a renegade CIA agent.', '2010', 'Sylvester Stallone, Jason Statham', '103', 'US', 'http://www.imdb.com/title/tt1320253/', 'https://www.rottentomatoes.com/m/the_expendables/', 'https://www.youtube.com/embed/sIPt8aUvz7U', 6.2, 10),
(3, 'Lepa sela lepo gore', 'Lepa sela lepo gore', 'lepasela.jpg', 'Srdjan Dragojevic', 'Dobar film', NULL, 'Dragan Bjelogrlic, Dragan Petrovic, Nikola Kojo', '127', 'Srbija', NULL, NULL, NULL, 0, 0),
(4, 'Betmen', 'Batman Begins', 'batman.jpg', 'Nolan', 'Dobar betmen', NULL, 'Bejl, Kejn', '142', 'USA', NULL, NULL, NULL, 0, 0),
(247, 'Borilacki klub', 'Fight Club', 'fightclubPoster', 'David Fincher', 'An insomniac office worker, looking for a way to change his life, crosses paths with a devil-may-care soapmaker, forming an underground fight club that evolves into something much, much more.', '1999', 'Brad Pitt, Edward Norton', '139', 'US', 'http://www.imdb.com/title/tt0137523/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=e31d89dd-322d-4646-8962-327b42fe94b1&pf_rd_r=1RV2W789S0JHA6X233XH&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_tt_10', 'https://www.rottentomatoes.com/m/fight_club/', 'https://www.youtube.com/watch?v=SUXWAEX2jlg', 0, 0),
(246, '12 gnevnih ljudi', '12 Angry Men', '12angrymen.jpg', 'Sydney Lumet', 'A jury holdout attempts to prevent a miscarriage of justice by forcing his colleagues to reconsider the evidence.', '1957', 'Henry Fonda, Lee J. Cobb, Martin Balsam', '96', 'US', 'http://www.imdb.com/title/tt0050083/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=e31d89dd-322d-4646-8962-327b42fe94b1&pf_rd_r=1RV2W789S0JHA6X233XH&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_tt_5', 'https://www.rottentomatoes.com/m/1000013_12_angry_men?', 'https://www.youtube.com/watch?v=A7CBKT0PWFA', 0, 0),
(248, 'Dobri momci', 'Goodfellas', 'goodfellasPoster.jpg', 'Martin Scorsese', 'The story of Henry Hill and his life in the mob, covering his relationship with his wife Karen Hill and his mob partners Jimmy Conway and Tommy DeVito in the Italian-American crime syndicate.', '1990', 'Robert De Niro, Ray Liotta, Joe Pesci', '146', 'US', 'http://www.imdb.com/title/tt0099685/?pf_rd_m=A2FGELUUNOQJNL&pf_rd_p=e31d89dd-322d-4646-8962-327b42fe94b1&pf_rd_r=1RV2W789S0JHA6X233XH&pf_rd_s=center-1&pf_rd_t=15506&pf_rd_i=top&ref_=chttp_tt_17', 'https://www.rottentomatoes.com/m/goodfellas', 'https://www.youtube.com/watch?v=qo5jJpHtI1Y', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `komentar`
--

CREATE TABLE `komentar` (
  `IdKomentar` int(11) NOT NULL,
  `Sadrzaj` text,
  `IdFilm` int(11) DEFAULT NULL,
  `IdKorisnik` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `IdKorisnik` int(11) NOT NULL,
  `Username` varchar(20) DEFAULT NULL,
  `Lozinka` varchar(20) DEFAULT NULL,
  `StatusKorisnik` varchar(20) DEFAULT NULL,
  `Ime` varchar(20) DEFAULT NULL,
  `Prezime` varchar(20) DEFAULT NULL,
  `Kontakt` varchar(20) DEFAULT NULL,
  `Email` varchar(40) DEFAULT NULL,
  `Poruka` text,
  `Blokiran` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`IdKorisnik`, `Username`, `Lozinka`, `StatusKorisnik`, `Ime`, `Prezime`, `Kontakt`, `Email`, `Poruka`, `Blokiran`) VALUES
(1, 'aleksa', 'leksa123', 'regkorisnik', 'Aleksa', 'Lazarevic', '064/4800605', 'aleksa.lazarevicc@gmail.com', '', 0),
(2, 'admin', 'admin', 'admin', 'Admir', 'Admirovic', '065/1121123', 'admin.adminovic@gmail.com', NULL, 0),
(3, 'dekicar', 'deki123', 'regkorisnik', 'Dejan', 'Milicevic', '065/7851231', 'deki@gmail.com', NULL, 0),
(4, 'acalukas', 'lukas123', 'regkorisnik', 'Aleksandar', 'Vuksanovic', '065/1231232', 'lukas@gmail.com', NULL, 0),
(6, 'goca', 'goca123', 'regkorisnik', 'Goca', 'Trzan', '93129312', 'goca@gmai.com', NULL, 0),
(9, 'pera', 'pera123', 'prodavac', 'Petar', 'Peric', '064/9885002', 'pera@gmail.com', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `mestoodrzavanja`
--

CREATE TABLE `mestoodrzavanja` (
  `IdMesto` int(11) NOT NULL,
  `NazivMesta` varchar(50) DEFAULT NULL,
  `Lokacija` varchar(50) DEFAULT NULL,
  `IdFestival` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mestoodrzavanja`
--

INSERT INTO `mestoodrzavanja` (`IdMesto`, `NazivMesta`, `Lokacija`, `IdFestival`) VALUES
(33, 'Herceg Novi', 'Forte mare', 0),
(32, 'Herceg Novi', 'Kanli kula', 0),
(31, 'Beograd', 'Centar za kulturu Vlada Divljan', 0),
(30, 'Beograd', 'Dom omladine', 0),
(29, 'Beograd', 'Dvorana kulturnog centra BG', 0),
(28, 'Beograd', 'Dom sindikata', 0),
(27, 'Beograd', 'Sava Centar', 0),
(34, 'Herceg Novi', 'Amfiteatar hercegnovskog pozoriÅ¡ta', 0),
(35, 'Herceg Novi', 'Atrijum kuÄ‡e Ive AndriÄ‡a', 0);

-- --------------------------------------------------------

--
-- Table structure for table `ocena`
--

CREATE TABLE `ocena` (
  `IdOcena` int(11) NOT NULL,
  `Ocena` int(11) DEFAULT NULL,
  `IdFilm` int(11) DEFAULT NULL,
  `IdKorisnik` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ocena`
--

INSERT INTO `ocena` (`IdOcena`, `Ocena`, `IdFilm`, `IdKorisnik`) VALUES
(3, 8, 1, 1),
(4, 10, 1, 1),
(5, 1, 1, 1),
(6, 10, 1, 1),
(7, 3, 1, 1),
(8, 4, 1, 1),
(9, 9, 1, 1),
(10, 5, 1, 1),
(11, 8, 1, 1),
(12, 4, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `poruka`
--

CREATE TABLE `poruka` (
  `IdPoruka` int(10) NOT NULL,
  `Sadrzaj` varchar(100) DEFAULT NULL,
  `IdKorisnik` int(10) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `programfestivala`
--

CREATE TABLE `programfestivala` (
  `IdProgram` int(11) NOT NULL,
  `NazivPrograma` varchar(20) DEFAULT NULL,
  `IdFestival` int(11) DEFAULT NULL,
  `IdProjekcija` int(11) DEFAULT NULL,
  `IdFilm` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `programfestivala`
--

INSERT INTO `programfestivala` (`IdProgram`, `NazivPrograma`, `IdFestival`, `IdProjekcija`, `IdFilm`) VALUES
(18, 'p', 33, 12, 1),
(17, 'p1', 33, 11, 247),
(16, 'p1', 35, 13, 1),
(15, 'p1', 34, 14, 246),
(14, 'p1', 34, 15, 246),
(13, 'p1', 35, 16, 248);

-- --------------------------------------------------------

--
-- Table structure for table `projekcija`
--

CREATE TABLE `projekcija` (
  `IdProjekcija` int(11) NOT NULL,
  `Mesto` varchar(20) DEFAULT NULL,
  `Satnica` varchar(20) DEFAULT NULL,
  `Sala` varchar(20) DEFAULT NULL,
  `Datum` timestamp NULL DEFAULT NULL,
  `Cena` int(11) DEFAULT NULL,
  `IdFilm` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `projekcija`
--

INSERT INTO `projekcija` (`IdProjekcija`, `Mesto`, `Satnica`, `Sala`, `Datum`, `Cena`, `IdFilm`) VALUES
(16, 'Sava Centar', '20:30', 'Velika sala', '2018-02-28 19:30:00', 480, 248),
(15, 'Forte mare', '19:00', NULL, '2018-08-18 17:00:00', 300, 246),
(14, 'Kanli kula', '17:45', '/', '2018-08-16 15:45:00', 250, 246),
(13, 'Dom omladine', '20:00', 'Velika sala', '2018-03-26 19:00:00', 450, 1),
(11, 'Dom omladine', '21:00', 'Amerikana', '2018-03-24 20:00:00', 500, 247),
(12, 'Dom omladine', '19:00', 'Amerikana', '2018-03-26 17:00:00', 600, 1);

-- --------------------------------------------------------

--
-- Table structure for table `rezervacija`
--

CREATE TABLE `rezervacija` (
  `IdRezervacija` int(11) NOT NULL,
  `JedinstveniKod` varchar(20) DEFAULT NULL,
  `DatumRezervacije` timestamp NULL DEFAULT NULL,
  `StatusRezervacije` varchar(20) DEFAULT NULL,
  `BrojRezervacija` int(11) DEFAULT NULL,
  `IdKorisnik` int(11) DEFAULT NULL,
  `IdProjekcija` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `sala`
--

CREATE TABLE `sala` (
  `IdSala` int(11) NOT NULL,
  `Naziv` varchar(50) DEFAULT NULL,
  `IdMesto` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `sala`
--

INSERT INTO `sala` (`IdSala`, `Naziv`, `IdMesto`) VALUES
(1, '56', 28),
(2, 'Sala 6', 28),
(19, 'Amerikana', 30),
(18, 'Velika sala', 30),
(17, 'Velika sala', 27);

-- --------------------------------------------------------

--
-- Table structure for table `slikafilm`
--

CREATE TABLE `slikafilm` (
  `IdSlika` int(11) NOT NULL,
  `Naziv` varchar(20) DEFAULT NULL,
  `Slika` varchar(50) DEFAULT NULL,
  `IdFilm` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `slikafilm`
--

INSERT INTO `slikafilm` (`IdSlika`, `Naziv`, `Slika`, `IdFilm`) VALUES
(1, 'exp1', 'theexpendables1.jpg', 1),
(2, 'exp2', 'theexpendables2.jpg', 1),
(3, 'exp3', 'theexpendables3.jpg', 1),
(12, 'fightclub', 'fightclub2.jpg', 247),
(11, 'fightclub', 'fightclub1.jpg', 247),
(10, 'slika', '12angrymen2.jpg', 246),
(9, 'slika', '12angrymen1.jpg', 246),
(13, 'goodfellas', 'goodfellas1.jpg', 248),
(14, 'goodfellas', 'goodfellas2.jpg', 248);

-- --------------------------------------------------------

--
-- Table structure for table `ulaznica`
--

CREATE TABLE `ulaznica` (
  `IdUlaznica` int(11) NOT NULL,
  `StatusUlaznice` varchar(20) DEFAULT NULL,
  `IdKorisnik` int(11) DEFAULT NULL,
  `IdProjekcija` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `zahtevregistracija`
--

CREATE TABLE `zahtevregistracija` (
  `IdZahtev` int(11) NOT NULL,
  `Username` varchar(20) DEFAULT NULL,
  `Lozinka` varchar(20) DEFAULT NULL,
  `Ime` varchar(20) DEFAULT NULL,
  `Prezime` varchar(20) DEFAULT NULL,
  `Kontakt` varchar(20) DEFAULT NULL,
  `Email` varchar(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `crnalista`
--
ALTER TABLE `crnalista`
  ADD PRIMARY KEY (`IdCrnaLista`),
  ADD KEY `R_4` (`IdKorisnik`);

--
-- Indexes for table `festival`
--
ALTER TABLE `festival`
  ADD PRIMARY KEY (`IdFestival`);

--
-- Indexes for table `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`IdFilm`);

--
-- Indexes for table `komentar`
--
ALTER TABLE `komentar`
  ADD PRIMARY KEY (`IdKomentar`),
  ADD KEY `R_15` (`IdFilm`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`IdKorisnik`);

--
-- Indexes for table `mestoodrzavanja`
--
ALTER TABLE `mestoodrzavanja`
  ADD PRIMARY KEY (`IdMesto`),
  ADD KEY `R_5` (`IdFestival`);

--
-- Indexes for table `ocena`
--
ALTER TABLE `ocena`
  ADD PRIMARY KEY (`IdOcena`),
  ADD KEY `R_14` (`IdFilm`);

--
-- Indexes for table `poruka`
--
ALTER TABLE `poruka`
  ADD PRIMARY KEY (`IdPoruka`);

--
-- Indexes for table `programfestivala`
--
ALTER TABLE `programfestivala`
  ADD PRIMARY KEY (`IdProgram`),
  ADD KEY `R_9` (`IdFestival`),
  ADD KEY `R_11` (`IdProjekcija`),
  ADD KEY `R_12` (`IdFilm`);

--
-- Indexes for table `projekcija`
--
ALTER TABLE `projekcija`
  ADD PRIMARY KEY (`IdProjekcija`),
  ADD KEY `R_18` (`IdFilm`);

--
-- Indexes for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD PRIMARY KEY (`IdRezervacija`),
  ADD KEY `R_16` (`IdKorisnik`),
  ADD KEY `R_17` (`IdProjekcija`);

--
-- Indexes for table `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`IdSala`),
  ADD KEY `R_7` (`IdMesto`);

--
-- Indexes for table `slikafilm`
--
ALTER TABLE `slikafilm`
  ADD PRIMARY KEY (`IdSlika`),
  ADD KEY `R_13` (`IdFilm`);

--
-- Indexes for table `ulaznica`
--
ALTER TABLE `ulaznica`
  ADD PRIMARY KEY (`IdUlaznica`),
  ADD KEY `R_2` (`IdKorisnik`),
  ADD KEY `R_19` (`IdProjekcija`);

--
-- Indexes for table `zahtevregistracija`
--
ALTER TABLE `zahtevregistracija`
  ADD PRIMARY KEY (`IdZahtev`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `crnalista`
--
ALTER TABLE `crnalista`
  MODIFY `IdCrnaLista` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `festival`
--
ALTER TABLE `festival`
  MODIFY `IdFestival` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=37;
--
-- AUTO_INCREMENT for table `film`
--
ALTER TABLE `film`
  MODIFY `IdFilm` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=249;
--
-- AUTO_INCREMENT for table `komentar`
--
ALTER TABLE `komentar`
  MODIFY `IdKomentar` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `IdKorisnik` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;
--
-- AUTO_INCREMENT for table `mestoodrzavanja`
--
ALTER TABLE `mestoodrzavanja`
  MODIFY `IdMesto` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;
--
-- AUTO_INCREMENT for table `ocena`
--
ALTER TABLE `ocena`
  MODIFY `IdOcena` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `poruka`
--
ALTER TABLE `poruka`
  MODIFY `IdPoruka` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `programfestivala`
--
ALTER TABLE `programfestivala`
  MODIFY `IdProgram` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;
--
-- AUTO_INCREMENT for table `projekcija`
--
ALTER TABLE `projekcija`
  MODIFY `IdProjekcija` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
--
-- AUTO_INCREMENT for table `rezervacija`
--
ALTER TABLE `rezervacija`
  MODIFY `IdRezervacija` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;
--
-- AUTO_INCREMENT for table `sala`
--
ALTER TABLE `sala`
  MODIFY `IdSala` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;
--
-- AUTO_INCREMENT for table `slikafilm`
--
ALTER TABLE `slikafilm`
  MODIFY `IdSlika` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `ulaznica`
--
ALTER TABLE `ulaznica`
  MODIFY `IdUlaznica` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
--
-- AUTO_INCREMENT for table `zahtevregistracija`
--
ALTER TABLE `zahtevregistracija`
  MODIFY `IdZahtev` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
