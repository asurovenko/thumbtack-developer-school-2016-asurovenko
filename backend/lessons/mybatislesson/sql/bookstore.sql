DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore;
USE bookstore;

CREATE TABLE authors (
  id INT(11) NOT NULL AUTO_INCREMENT,
  firstname VARCHAR(50) NOT NULL,
  lastname VARCHAR(50) NOT NULL,
  patronymic VARCHAR(50) DEFAULT NULL,
  birthdate DATE NOT NULL,
  PRIMARY KEY (id),
  KEY firstname (firstname),
  KEY lastname (lastname),
  KEY patronymic (patronymic),
  KEY birthdate (birthdate)
);


CREATE TABLE author_address (
  id INT(11) NOT NULL AUTO_INCREMENT,
  authorid INT(11) NOT NULL,
  email VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY author_FK (authorid,email),
  CONSTRAINT author_address FOREIGN KEY (`authorid`) REFERENCES authors (id) ON DELETE CASCADE
) ;


CREATE TABLE books (
  id INT(11) NOT NULL AUTO_INCREMENT,
  title VARCHAR(45) NOT NULL,
  YEAR INT(11) NOT NULL,
  pages INT(11) NOT NULL,
  PRIMARY KEY (id),
  KEY YEAR (YEAR),
  KEY pages (pages)
) ;

CREATE TABLE author_book (
  id INT(11) NOT NULL AUTO_INCREMENT,
  authorid INT(11) NOT NULL,
  bookid INT(11) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY author_book (authorid,bookid),
  KEY bookid (bookid),
  FOREIGN KEY (bookid) REFERENCES books (id) ON DELETE CASCADE,
  FOREIGN KEY (authorid) REFERENCES authors (id) ON DELETE CASCADE
) ;

