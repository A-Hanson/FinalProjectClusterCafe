-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema clustercafedb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `clustercafedb` ;

-- -----------------------------------------------------
-- Schema clustercafedb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `clustercafedb` DEFAULT CHARACTER SET utf8 ;
USE `clustercafedb` ;

-- -----------------------------------------------------
-- Table `store`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `store` ;

CREATE TABLE IF NOT EXISTS `store` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(100) NULL,
  `address` VARCHAR(100) NULL,
  `city` VARCHAR(45) NULL,
  `state` VARCHAR(2) NULL,
  `postal_code` VARCHAR(10) NULL,
  `latitude` FLOAT(10,6) NULL,
  `longitude` FLOAT(10,6) NULL,
  `category` VARCHAR(45) NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `email` VARCHAR(45) NULL,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `img_url` VARCHAR(100) NULL,
  `pronouns` VARCHAR(45) NULL,
  `dob` DATE NULL,
  `enabled` TINYINT NOT NULL DEFAULT 1,
  `role` VARCHAR(45) NULL,
  `gender` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `store_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_user_store1_idx` (`store_id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  CONSTRAINT `fk_user_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `category` ;

CREATE TABLE IF NOT EXISTS `category` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `meeting`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `meeting` ;

CREATE TABLE IF NOT EXISTS `meeting` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `category_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  `flagged` TINYINT NULL DEFAULT 0,
  `store_id` INT NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `meeting_date` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_event_category_idx` (`category_id` ASC),
  INDEX `fk_event_user1_idx` (`user_id` ASC),
  INDEX `fk_event_store1_idx` (`store_id` ASC),
  CONSTRAINT `fk_event_category`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_event_store1`
    FOREIGN KEY (`store_id`)
    REFERENCES `store` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `user_cluster`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_cluster` ;

CREATE TABLE IF NOT EXISTS `user_cluster` (
  `user_id` INT NOT NULL,
  `meeting_id` INT NOT NULL,
  INDEX `fk_user_has_event_event1_idx` (`meeting_id` ASC),
  INDEX `fk_user_has_event_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_user_has_event_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_has_event_event1`
    FOREIGN KEY (`meeting_id`)
    REFERENCES `meeting` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post` ;

CREATE TABLE IF NOT EXISTS `post` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NULL,
  `content` TEXT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `enabled` TINYINT NULL,
  `flagged` TINYINT NULL,
  `user_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_post_user1_idx` (`user_id` ASC),
  INDEX `fk_post_category1_idx` (`category_id` ASC),
  CONSTRAINT `fk_post_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_post_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `post_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `post_comment` ;

CREATE TABLE IF NOT EXISTS `post_comment` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NULL,
  `post_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  `flagged` TINYINT NULL DEFAULT 0,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_comment_post1_idx` (`post_id` ASC),
  INDEX `fk_comment_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_comment_post1`
    FOREIGN KEY (`post_id`)
    REFERENCES `post` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_comment_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `cluster_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `cluster_group` ;

CREATE TABLE IF NOT EXISTS `cluster_group` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `description` TEXT NULL,
  `created_at` DATETIME NULL,
  `enabled` TINYINT NULL DEFAULT 1,
  `img_url` VARCHAR(5000) NULL,
  `moderator_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_cluster_group_user1_idx` (`moderator_id` ASC),
  CONSTRAINT `fk_cluster_group_user1`
    FOREIGN KEY (`moderator_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `group_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `group_user` ;

CREATE TABLE IF NOT EXISTS `group_user` (
  `cluster_group_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `fk_group_has_user_user1_idx` (`user_id` ASC),
  INDEX `fk_group_has_user_group1_idx` (`cluster_group_id` ASC),
  CONSTRAINT `fk_group_has_user_group1`
    FOREIGN KEY (`cluster_group_id`)
    REFERENCES `cluster_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_has_user_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `message` ;

CREATE TABLE IF NOT EXISTS `message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `content` TEXT NULL,
  `creator_id` INT NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `enabled` TINYINT NULL,
  `parent_message_id` INT NULL,
  `seen` TINYINT NULL,
  `recipient_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`creator_id` ASC),
  INDEX `fk_message_message1_idx` (`parent_message_id` ASC),
  INDEX `fk_message_user2_idx` (`recipient_id` ASC),
  CONSTRAINT `fk_message_user1`
    FOREIGN KEY (`creator_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_message1`
    FOREIGN KEY (`parent_message_id`)
    REFERENCES `message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_user2`
    FOREIGN KEY (`recipient_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `group_message`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `group_message` ;

CREATE TABLE IF NOT EXISTS `group_message` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(100) NULL,
  `content` TEXT NULL,
  `creator_id` INT NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `enabled` TINYINT NULL,
  `parent_message_id` INT NULL,
  `cluster_group_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_message_user1_idx` (`creator_id` ASC),
  INDEX `fk_message_message1_idx` (`parent_message_id` ASC),
  INDEX `fk_group_message_cluster_group1_idx` (`cluster_group_id` ASC),
  CONSTRAINT `fk_message_user10`
    FOREIGN KEY (`creator_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_message_message10`
    FOREIGN KEY (`parent_message_id`)
    REFERENCES `group_message` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_group_message_cluster_group1`
    FOREIGN KEY (`cluster_group_id`)
    REFERENCES `cluster_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS clustercafeuser@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'clustercafeuser'@'localhost' IDENTIFIED BY 'clustercafepassword';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'clustercafeuser'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `store`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `store` (`id`, `name`, `address`, `city`, `state`, `postal_code`, `latitude`, `longitude`, `category`, `enabled`) VALUES (1, 'Volcano Tea House Aurora', '2781 S Parker Rd', 'Aurora', 'CO', '80014', 39.666400, -104.861160, 'Coffee', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `user` (`id`, `username`, `password`, `email`, `first_name`, `last_name`, `img_url`, `pronouns`, `dob`, `enabled`, `role`, `gender`, `created_at`, `store_id`) VALUES (1, 'test', '$2a$10$l0e1qFzGwkUQRpNAj15Tau064aiixnS9pLi5VlYvwJMzHxwlTaWUu', 'test@test.com', 'Thor', 'Bird', NULL, 'He / His', '2007-05-15', 1, 'standard', 'male', '2021-04-20', 1);
INSERT INTO `user` (`id`, `username`, `password`, `email`, `first_name`, `last_name`, `img_url`, `pronouns`, `dob`, `enabled`, `role`, `gender`, `created_at`, `store_id`) VALUES (2, 'admin', '$2a$10$/qvYRJCxHUa4Qi2.WL0LqOsvUIpSxfxvdkNYrwX/UgrpenaOg/ie6', 'admin@admin.com', 'Adam', 'Min', NULL, 'He / His', '1995-10-10', 1, 'admin', 'male', '2021-4-22', 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `category`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `category` (`id`, `name`) VALUES (1, 'Running');
INSERT INTO `category` (`id`, `name`) VALUES (2, 'Walking');
INSERT INTO `category` (`id`, `name`) VALUES (3, 'Cycling');
INSERT INTO `category` (`id`, `name`) VALUES (4, 'Gaming');
INSERT INTO `category` (`id`, `name`) VALUES (5, 'Team Sports');
INSERT INTO `category` (`id`, `name`) VALUES (6, 'Swimming');
INSERT INTO `category` (`id`, `name`) VALUES (7, 'Hiking');
INSERT INTO `category` (`id`, `name`) VALUES (8, 'Fishing');
INSERT INTO `category` (`id`, `name`) VALUES (9, 'Boating');
INSERT INTO `category` (`id`, `name`) VALUES (10, 'Snow Sports');
INSERT INTO `category` (`id`, `name`) VALUES (11, 'Diving');
INSERT INTO `category` (`id`, `name`) VALUES (12, 'Programming');
INSERT INTO `category` (`id`, `name`) VALUES (13, 'Moms');
INSERT INTO `category` (`id`, `name`) VALUES (14, 'Dads');
INSERT INTO `category` (`id`, `name`) VALUES (15, 'Crafting');
INSERT INTO `category` (`id`, `name`) VALUES (16, 'Photography');

COMMIT;


-- -----------------------------------------------------
-- Data for table `meeting`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `meeting` (`id`, `name`, `description`, `category_id`, `user_id`, `enabled`, `flagged`, `store_id`, `created_at`, `updated_at`, `meeting_date`) VALUES (1, 'Jogging', 'Let\'s go flying!!!', 1, 1, 1, 0, 1, '2021-04-20 00:00:00.00', NULL, '2021-04-24 00:00:00.00');

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_cluster`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `user_cluster` (`user_id`, `meeting_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `post`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `post` (`id`, `title`, `content`, `created_at`, `updated_at`, `enabled`, `flagged`, `user_id`, `category_id`) VALUES (1, 'Looking for other runners', 'Entry level runner seeks other entry level runners to run with.', '2021-04-20', NULL, 1, 0, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `post_comment`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `post_comment` (`id`, `content`, `post_id`, `user_id`, `enabled`, `flagged`, `created_at`, `updated_at`) VALUES (1, 'No silly people.', 1, 1, 1, 0, '2021-04-20 00:00:00.00', NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `cluster_group`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `cluster_group` (`id`, `name`, `description`, `created_at`, `enabled`, `img_url`, `moderator_id`) VALUES (1, 'Aurora Runners', 'Low-key casual running!', '2021-04-20 00:00:00.00', 1, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `group_user`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `group_user` (`cluster_group_id`, `user_id`) VALUES (1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `message`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `message` (`id`, `title`, `content`, `creator_id`, `created_at`, `updated_at`, `enabled`, `parent_message_id`, `seen`, `recipient_id`) VALUES (1, 'Test Message', 'I love apples and bananas', 1, '2021-04-20 00:00:00.00', NULL, 1, NULL, 0, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `group_message`
-- -----------------------------------------------------
START TRANSACTION;
USE `clustercafedb`;
INSERT INTO `group_message` (`id`, `title`, `content`, `creator_id`, `created_at`, `updated_at`, `enabled`, `parent_message_id`, `cluster_group_id`) VALUES (1, 'Test Group Message', 'Lalalalala', 1, '2021-04-20 00:00:00.00', NULL, 1, NULL, 1);

COMMIT;

