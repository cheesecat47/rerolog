CREATE SCHEMA IF NOT EXISTS `myblog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci ;
USE `myblog` ;

CREATE TABLE `User` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `id_str` VARCHAR(20) NOT NULL UNIQUE,
                        `pw` VARCHAR(12) NOT NULL,
                        `email_account` VARCHAR(50) NOT NULL,
                        `email_domain` VARCHAR(50) NOT NULL,
                        `content` VARCHAR(200) DEFAULT NULL,
                        `session_id` VARCHAR(20) DEFAULT NULL,
                        `created_at` DATETIME NOT NULL DEFAULT NOW(),
                        `updated_at` DATETIME DEFAULT NULL,
                        `deleted_at` DATETIME DEFAULT NULL,
                        PRIMARY KEY (`id`)
);

CREATE TABLE `Contact` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `user_id` INT NOT NULL,
                           `type_id` INT NOT NULL,
                           `value` VARCHAR(2048) NOT NULL,
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `ContactType` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `type` VARCHAR(20) NOT NULL,
                               PRIMARY KEY (`id`)
);

CREATE TABLE `Blog` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `user_id` INT NOT NULL,
                        `name` VARCHAR(20) NOT NULL,
                        `created_at` DATETIME NOT NULL DEFAULT NOW(),
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `Category` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `blog_id` INT NOT NULL,
                            `name` VARCHAR(20),
                            `created_at` DATETIME NOT NULL DEFAULT NOW(),
                            `updated_at` DATETIME DEFAULT NULL,
                            `deleted_at` DATETIME DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            FOREIGN KEY (`blog_id`) REFERENCES `Blog`(`id`)
);

CREATE TABLE `Post` (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `category_id` INT NOT NULL,
                        `user_id` INT NOT NULL,
                        `title` VARCHAR(100) NOT NULL,
                        `content` VARCHAR(2000),
                        `hit` INT NOT NULL DEFAULT 0,
                        `created_at` DATETIME NOT NULL DEFAULT NOW(),
                        `updated_at` DATETIME DEFAULT NULL,
                        `deleted_at` DATETIME DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        FOREIGN KEY (`category_id`) REFERENCES `Category`(`id`),
                        FOREIGN KEY (`user_id`) REFERENCES `User`(`id`)
);

CREATE TABLE `Comment` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `user_id` INT NOT NULL,
                           `post_id` INT NOT NULL,
                           `content` VARCHAR(300) NOT NULL,
                           `created_at` DATETIME NOT NULL DEFAULT NOW(),
                           `updated_at` DATETIME DEFAULT NULL,
                           `deleted_at` DATETIME DEFAULT NULL,
                           PRIMARY KEY (`id`),
                           FOREIGN KEY (`user_id`) REFERENCES `User`(`id`),
                           FOREIGN KEY (`post_id`) REFERENCES `Post`(`id`)
);

CREATE TABLE `PostAttachment` (
                                  `id` INT NOT NULL AUTO_INCREMENT,
                                  `post_id` INT NOT NULL,
                                  `path` VARCHAR(250),
                                  PRIMARY KEY (`id`),
                                  FOREIGN KEY (`post_id`) REFERENCES `Post`(`id`)
);

CREATE TABLE `PostThumbnailImage` (
                                      `id` INT NOT NULL,
                                      `path` VARCHAR(250),
                                      PRIMARY KEY (`id`),
                                      FOREIGN KEY (`id`) REFERENCES `Post`(`id`)
);

CREATE TABLE `UserProfileImage` (
                                    `id` INT NOT NULL,
                                    `path` VARCHAR(250),
                                    PRIMARY KEY (`id`),
                                    FOREIGN KEY (`id`) REFERENCES `User`(`id`)
);