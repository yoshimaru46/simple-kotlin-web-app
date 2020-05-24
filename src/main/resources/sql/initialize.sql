create database sample;

USE sample;

CREATE TABLE items(
  id INTEGER AUTO_INCREMENT NOT NULL PRIMARY KEY,
  name VARCHAR(32) NOT NULL,
  price INTEGER NOT NULL
  );
