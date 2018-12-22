-- create-schema
-- CREATE SCHEMA `lib` DEFAULT CHARACTER SET utf8;

-- create-tables.sql
CREATE TABLE `users` (
  `user_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(45) NOT NULL,
  `user_password` varchar(45) NOT NULL,
  `user_gender` varchar(45) DEFAULT NULL,
  `user_birth_date` date DEFAULT NULL,
  `user_information` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`user_id`,`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;



CREATE TABLE `categories` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `files` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) unsigned NOT NULL,
  `url` varchar(45) NOT NULL,
  `description` varchar(256) DEFAULT 'not set',
  `date` date NOT NULL,
  `category` bigint(10) unsigned DEFAULT '1',
  PRIMARY KEY (`id`,`url`),
  KEY `fk_files_1_idx` (`user_id`),
  KEY `fk_files_2_idx` (`category`),
  CONSTRAINT `fk_files_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;












