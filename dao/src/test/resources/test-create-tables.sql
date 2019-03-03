-- CREATE TABLE `role` (
--   `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
--   `role` varchar(10) NOT NULL,
--   PRIMARY KEY (`id`,`role`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `categories` (
--   `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
--   `category` varchar(45) NOT NULL,
--   PRIMARY KEY (`id`,`category`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `users` (
--   `user_id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
--   `user_name` varchar(45) NOT NULL,
--   `user_password` varchar(255) NOT NULL,
--   `user_gender` varchar(256) DEFAULT NULL,
--   `user_birth_date` date DEFAULT NULL,
--   `user_information` varchar(1024) DEFAULT NULL,
--   PRIMARY KEY (`user_id`,`user_name`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=137 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `files` (
--   `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
--   `user_id` bigint(10) unsigned NOT NULL,
--   `url` varchar(45) NOT NULL,
--   `description` varchar(256) DEFAULT NULL,
--   `date` date NOT NULL,
--   `category` bigint(10) unsigned DEFAULT NULL,
--   PRIMARY KEY (`id`,`url`),
--   KEY `fk_files_1_idx` (`user_id`),
--   KEY `fk_files_2_idx` (`category`),
--   CONSTRAINT `fk_files_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
--   CONSTRAINT `fk_files_2` FOREIGN KEY (`category`) REFERENCES `categories` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
-- ) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `user_role` (
--   `user_id` bigint(10) unsigned NOT NULL,
--   `role_id` bigint(10) unsigned NOT NULL,
--   `id` bigint(10) unsigned NOT NULL AUTO_INCREMENT,
--   PRIMARY KEY (`id`),
--   KEY `fk_user_role_1_idx` (`user_id`),
--   KEY `fk_user_role_2_idx` (`role_id`),
--   CONSTRAINT `fk_user_role_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
--   CONSTRAINT `fk_user_role_2` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
-- ) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;


CREATE TABLE `user` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL,
  `user_password` VARCHAR(45) NOT NULL,
  `user_gender` VARCHAR(45) NULL,
  `user_birth_date` DATE NULL,
  `user_information` VARCHAR(45) NULL,
  PRIMARY KEY (`id`, `user_name`))
ENGINE = InnoDB;


CREATE TABLE `folder` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `user_id` BIGINT(10) UNSIGNED NOT NULL,
  `parent_id` BIGINT(10) UNSIGNED NULL,
  PRIMARY KEY (`id`),
  INDEX `parent_id` (`parent_id` ASC),
  INDEX `user_id` (`user_id` ASC),
  CONSTRAINT `fk_folder_1`
    FOREIGN KEY (`parent_id`)
    REFERENCES `folder` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_folder_2`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE `file` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_id` BIGINT(10) UNSIGNED NOT NULL,
  `date` DATE NOT NULL,
  `description` VARCHAR(45) NULL,
  `folder_id` BIGINT(10) UNSIGNED NOT NULL,
  `real_name` VARCHAR(45) NOT NULL,
  `encode_name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `user_id`),
  INDEX `fk_files_users1_idx` (`user_id` ASC),
  INDEX `fk_files_folder1_idx` (`folder_id` ASC),
  CONSTRAINT `fk_files_users1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_files_folder1`
    FOREIGN KEY (`folder_id`)
    REFERENCES `folder` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE `role` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `role` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `role`))
ENGINE = InnoDB;


CREATE TABLE `category` (
  `id` BIGINT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`, `category`))
ENGINE = InnoDB;


CREATE TABLE `user_role` (
  `user_id` BIGINT(10) UNSIGNED NOT NULL,
  `role_id` INT UNSIGNED NOT NULL,
  INDEX `fk_user_role_users_idx` (`user_id` ASC),
  INDEX `fk_user_role_roles1_idx` (`role_id` ASC),
  CONSTRAINT `fk_user_role_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_role_roles1`
    FOREIGN KEY (`role_id`)
    REFERENCES `role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE `file_category` (
  `category_id` BIGINT(10) UNSIGNED NOT NULL,
  `file_id` BIGINT(10) UNSIGNED NOT NULL,
  INDEX `fk_file-category_categories1_idx` (`category_id` ASC),
  INDEX `fk_file_category_file1_idx` (`file_id` ASC),
  CONSTRAINT `fk_file-category_categories1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_file_category_file1`
    FOREIGN KEY (`file_id`)
    REFERENCES `file` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS `user_has_category` (
  `user_id` BIGINT(10) UNSIGNED NOT NULL,
  `category_id` BIGINT(10) UNSIGNED NOT NULL,
  INDEX `fk_user_has_category_category1_idx` (`category_id` ASC),
  INDEX `fk_user_has_category_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_category_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



