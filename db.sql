DROP DATABASE IF EXISTS `JDBC_AM_26_04`;
CREATE DATABASE `JDBC_AM_26_04`;
USE `JDBC_AM_26_04`;

CREATE TABLE article (
                         id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                         regDate DATETIME NOT NULL,
                         updateDate DATETIME NOT NULL,
                         title CHAR(100) NOT NULL,
                         `body` TEXT NOT NULL
);

DESC article;

SELECT *
FROM article;

INSERT INTO article
SET regDate = NOW(),
updateDate = NOW(),
title = CONCAT('제목', SUBSTRING(RAND() * 1000 FROM 1 FOR 2)),
`body` = CONCAT('내용', SUBSTRING(RAND() * 1000 FROM 1 FOR 2));

SELECT NOW();

SELECT '제목1';

SELECT CONCAT('제목2', '2');

SELECT SUBSTRING(RAND() * 1000 FROM 1 FOR 2);

DELETE FROM article;

USE a5;

UPDATE article
SET title = '새제목30',
    `body` = '새내용30',
    updateDate = NOW()
WHERE id = 2;

CREATE TABLE `memeber` (
                           id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                           memberId VARCHAR(100) NOT NULL UNIQUE,
                           memberPw TEXT NOT NULL,
                           `name` VARCHAR(100) NOT NULL,
                           regDate DATETIME NOT NULL
);