DROP database if exists DreamAndFlyTest;

CREATE DATABASE DreamAndFlyTest;
DROP USER IF EXISTS 'testClient'@'localhost';
CREATE USER 'testClient'@'localhost' IDENTIFIED BY 'testClient';
GRANT ALL ON *.* TO 'testClient'@'localhost';
FLUSH PRIVILEGES;
USE DreamAndFlyTest;