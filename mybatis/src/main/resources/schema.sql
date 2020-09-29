DROP TABLE IF EXISTS t_person;
DROP TABLE IF EXISTS country;
CREATE TABLE IF NOT EXISTS t_person
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(64) NOT NULL,
    age     INT         NOT NULL,
    address VARCHAR(64) NOT NULL
);
CREATE table IF NOT EXISTS country
(
    id          INT          NOT NULL AUTO_INCREMENT,
    countryName varchar(255) NULL,
    countryCode varchar(255) NULL,
    PRIMARY KEY (id)
);