SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `deel2` ;
CREATE SCHEMA IF NOT EXISTS `deel2` DEFAULT CHARACTER SET utf8 ;
USE `deel2` ;

-- -----------------------------------------------------
-- Table `deel2`.`klant`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`klant` (
  `idKlant` INT(11) NOT NULL AUTO_INCREMENT ,
  `voornaam` VARCHAR(50) NOT NULL ,
  `tussenvoegsel` VARCHAR(10) NULL DEFAULT NULL ,
  `achternaam` VARCHAR(51) NOT NULL ,
  `email` VARCHAR(80) NOT NULL ,
  PRIMARY KEY (`idKlant`) ,
  UNIQUE INDEX `idKlant_UNIQUE` (`idKlant` ASC) ,
  UNIQUE INDEX `Klant_naam_email_unique` (`voornaam` ASC, `achternaam` ASC, `email` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`account`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`account` (
  `idAccount` INT(11) NOT NULL AUTO_INCREMENT ,
  `account_naam` VARCHAR(80) NOT NULL ,
  `creatie_datum` DATE NOT NULL ,
  `klant_idKlant` INT(11) NOT NULL ,
  PRIMARY KEY (`idAccount`) ,
  UNIQUE INDEX `idAccount_UNIQUE` (`idAccount` ASC) ,
  UNIQUE INDEX `Account_naam_UNIQUE` (`account_naam` ASC) ,
  INDEX `fk_Account_Klant1_idx` (`klant_idKlant` ASC) ,
  CONSTRAINT `fk_Account_Klant`
    FOREIGN KEY (`klant_idKlant` )
    REFERENCES `deel2`.`klant` (`idKlant` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`adres`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`adres` (
  `idAdres` INT(11) NOT NULL AUTO_INCREMENT ,
  `straatnaam` VARCHAR(45) NOT NULL ,
  `postcode` VARCHAR(45) NOT NULL ,
  `huisnummer` VARCHAR(45) NOT NULL ,
  `woonplaats` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`idAdres`) ,
  UNIQUE INDEX `idAdres_UNIQUE` (`idAdres` ASC) ,
  UNIQUE INDEX `adres_wp_pc_nr_unique` (`straatnaam` ASC, `postcode` ASC, `huisnummer` ASC, `woonplaats` ASC) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`adres_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`adres_type` (
  `idAdres_type` INT(11) NOT NULL AUTO_INCREMENT ,
  `adres_type` VARCHAR(45) NULL DEFAULT NULL ,
  PRIMARY KEY (`idAdres_type`) )
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`artikel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`artikel` (
  `idArtikel` INT(11) NOT NULL AUTO_INCREMENT ,
  `artikelnaam` VARCHAR(80) NOT NULL ,
  `artikelprijs` DECIMAL(6,2) NOT NULL ,
  `artikelnummer` VARCHAR(45) NOT NULL ,
  `artikelomschrijving` VARCHAR(80) NULL DEFAULT NULL ,
  PRIMARY KEY (`idArtikel`) ,
  UNIQUE INDEX `idArtikel_UNIQUE` (`idArtikel` ASC) ,
  UNIQUE INDEX `artikel_naam_prijs_nummer_unique` (`artikelnaam` ASC, `artikelprijs` ASC, `artikelnummer` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`bestelling`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`bestelling` (
  `idBestelling` INT(11) NOT NULL AUTO_INCREMENT ,
  `Klant_idKlant` INT(11) NOT NULL ,
  PRIMARY KEY (`idBestelling`) ,
  UNIQUE INDEX `idBestelling_UNIQUE` (`idBestelling` ASC) ,
  INDEX `fk_Bestelling_Klant_idx` (`Klant_idKlant` ASC) ,
  CONSTRAINT `fk_Bestelling_Klant`
    FOREIGN KEY (`Klant_idKlant` )
    REFERENCES `deel2`.`klant` (`idKlant` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`bestelling_has_artikel`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`bestelling_has_artikel` (
  `idBestelArtikel` INT(11) NOT NULL AUTO_INCREMENT ,
  `aantal` INT(11) NOT NULL ,
  `artikel_idArtikel` INT(11) NOT NULL ,
  `bestelling_idBestelling` INT(11) NOT NULL ,
  PRIMARY KEY (`idBestelArtikel`) ,
  UNIQUE INDEX `idBestelArtikel_UNIQUE` (`idBestelArtikel` ASC) ,
  UNIQUE INDEX `BestellingId_ArtikelId_unique` (`artikel_idArtikel` ASC, `bestelling_idBestelling` ASC) ,
  INDEX `fk_BestelArtikel_Artikel_idx` (`artikel_idArtikel` ASC) ,
  INDEX `fk_BestelArtikel_Bestelling_idx` (`bestelling_idBestelling` ASC) ,
  CONSTRAINT `fk_BestelArtikel_Artikel`
    FOREIGN KEY (`artikel_idArtikel` )
    REFERENCES `deel2`.`artikel` (`idArtikel` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_BestelArtikel_Bestelling1`
    FOREIGN KEY (`bestelling_idBestelling` )
    REFERENCES `deel2`.`bestelling` (`idBestelling` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`betaalwijze`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`betaalwijze` (
  `idBetaalwijze` INT(11) NOT NULL AUTO_INCREMENT ,
  `betaalwijze` VARCHAR(80) NULL DEFAULT NULL ,
  PRIMARY KEY (`idBetaalwijze`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`factuur`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`factuur` (
  `idFactuur` INT(11) NOT NULL AUTO_INCREMENT ,
  `factuur_datum` DATE NOT NULL ,
  `bestelling_idBestelling` INT(11) NOT NULL ,
  PRIMARY KEY (`idFactuur`) ,
  UNIQUE INDEX `idFactuur_UNIQUE` (`idFactuur` ASC) ,
  INDEX `fk_Factuur_Bestelling1_idx` (`bestelling_idBestelling` ASC) ,
  CONSTRAINT `fk_Factuur_Bestelling`
    FOREIGN KEY (`bestelling_idBestelling` )
    REFERENCES `deel2`.`bestelling` (`idBestelling` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`betaling`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`betaling` (
  `idBetaling` INT(11) NOT NULL AUTO_INCREMENT ,
  `factuur_idFactuur` INT(11) NOT NULL ,
  `betaal_datum` DATE NOT NULL ,
  `betaalwijze_idBetaalwijze` INT(11) NOT NULL ,
  `betalingsGegevens` VARCHAR(80) NULL DEFAULT NULL ,
  `klant_idKlant` INT(11) NOT NULL ,
  PRIMARY KEY (`idBetaling`) ,
  INDEX `fk_Betaling_Factuur1_idx` (`factuur_idFactuur` ASC) ,
  INDEX `fk_Betaling_Betaalwijze_idx` (`betaalwijze_idBetaalwijze` ASC) ,
  INDEX `fk_Betaling_Klant1_idx` (`klant_idKlant` ASC) ,
  CONSTRAINT `fk_Betaling_Betaalwijze1`
    FOREIGN KEY (`betaalwijze_idBetaalwijze` )
    REFERENCES `deel2`.`betaalwijze` (`idBetaalwijze` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Betaling_Factuur1`
    FOREIGN KEY (`factuur_idFactuur` )
    REFERENCES `deel2`.`factuur` (`idFactuur` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Betaling_Klant1`
    FOREIGN KEY (`klant_idKlant` )
    REFERENCES `deel2`.`klant` (`idKlant` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `deel2`.`klant_has_adres`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `deel2`.`klant_has_adres` (
  `idKlant_has_adres` INT(11) NOT NULL AUTO_INCREMENT ,
  `adres_idAdres` INT(11) NOT NULL ,
  `adres_type_idAdres_type` INT(11) NOT NULL ,
  `klant_idKlant` INT(11) NOT NULL ,
  PRIMARY KEY (`idKlant_has_adres`) ,
  UNIQUE INDEX `Klantid_AdresId_unique` (`klant_idKlant` ASC, `adres_idAdres` ASC, `adres_type_idAdres_type` ASC) ,
  UNIQUE INDEX `Idklant_adres_UNIQUE` (`idKlant_has_adres` ASC) ,
  INDEX `fk_Klant_has_Adres_Adres1_idx` (`adres_idAdres` ASC) ,
  INDEX `fk_Klant_has_Adres_Klant1_idx` (`klant_idKlant` ASC) ,
  INDEX `fk_Klant_has_Adres_Adres_type1_idx` (`adres_type_idAdres_type` ASC) ,
  CONSTRAINT `fk_Klant_has_Adres_Adres`
    FOREIGN KEY (`adres_idAdres` )
    REFERENCES `deel2`.`adres` (`idAdres` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Klant_has_Adres_Adres_type`
    FOREIGN KEY (`adres_type_idAdres_type` )
    REFERENCES `deel2`.`adres_type` (`idAdres_type` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Klant_has_Adres_Klant`
    FOREIGN KEY (`klant_idKlant` )
    REFERENCES `deel2`.`klant` (`idKlant` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 2
DEFAULT CHARACTER SET = utf8;

USE `deel2` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
