CREATE SCHEMA IF NOT EXISTS `sample_file_exchanger` DEFAULT CHARACTER SET utf8 ;
USE `sample_file_exchanger`;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`user` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_name` varchar(11) NOT NULL,
  `user_password` varchar(60) NOT NULL,
  `user_gender` varchar(45) DEFAULT NULL,
  `user_birth_date` date DEFAULT NULL,
  `user_information` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_UNIQUE` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=21073 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`folder` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `user_id` bigint(10) unsigned DEFAULT NULL,
  `parent_id` bigint(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `parent_id` (`parent_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `fk_folder_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`file` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) unsigned NOT NULL,
  `date` date NOT NULL,
  `description` varchar(450) DEFAULT NULL,
  `folder_id` bigint(10) unsigned DEFAULT NULL,
  `real_name` varchar(450) NOT NULL,
  `encode_name` varchar(40) NOT NULL,
  PRIMARY KEY (`id`,`user_id`),
  UNIQUE KEY `encode_name_UNIQUE` (`encode_name`),
  KEY `fk_files_users1_idx` (`user_id`),
  KEY `fk_files_folder1_idx` (`folder_id`),
  CONSTRAINT `fk_files_folder1`
    FOREIGN KEY (`folder_id`)
    REFERENCES `folder` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_files_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=86 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`role`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`category` (
  `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
  `category` varchar(45) NOT NULL,
  PRIMARY KEY (`id`,`category`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`user_role` (
  `user_id` bigint(10) unsigned NOT NULL,
  `role_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `fk_user_role_users_idx` (`user_id`),
  KEY `fk_user_role_roles1_idx` (`role_id`),
  CONSTRAINT `fk_user_role_roles1`
   FOREIGN KEY (`role_id`)
   REFERENCES `role` (`id`)
   ON DELETE CASCADE
   ON UPDATE CASCADE,
  CONSTRAINT `fk_user_role_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`file_category` (
  `category_id` bigint(10) unsigned NOT NULL,
  `file_id` bigint(10) unsigned NOT NULL,
  PRIMARY KEY (`category_id`,`file_id`),
  KEY `fk_file-category_categories1_idx` (`category_id`),
  KEY `fk_file_category_file1_idx` (`file_id`),
  CONSTRAINT `fk_file-category_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_file_category_file1`
    FOREIGN KEY (`file_id`)
    REFERENCES `file` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `sample_file_exchanger`.`user_has_category` (
  `user_id` bigint(10) unsigned NOT NULL,
  `category_id` bigint(10) unsigned NOT NULL,
  PRIMARY KEY (`category_id`,`user_id`),
  KEY `fk_user_has_category_category1_idx` (`category_id`),
  KEY `fk_user_has_category_user1_idx` (`user_id`),
  CONSTRAINT `fk_user_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_user_has_category_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
