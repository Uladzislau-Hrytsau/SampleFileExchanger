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



