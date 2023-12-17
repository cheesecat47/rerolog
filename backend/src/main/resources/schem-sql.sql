CREATE TABLE `User` (
                        `id` int PRIMARY KEY AUTO_INCREMENT,
                        `id_str` varchar(20) NOT NULL UNIQUE,
                        `pw` varchar(12) NOT NULL,
                        `nick_name` varchar(8) NOT NULL,
                        `email_account` varchar(50) NOT NULL,
                        `email_domain` varchar(50) NOT NULL,
                        `content` varchar(200) DEFAULT NULL,
                        `profile_image` varchar(250) DEFAULT NULL,
                        `session_id` varchar(20) DEFAULT NULL,
                        `created_at` datetime NOT NULL DEFAULT NOW(),
                        `updated_at` datetime DEFAULT NULL,
                        `deleted_at` datetime DEFAULT NULL
);

CREATE TABLE `Contact` (
                           `id` int PRIMARY KEY AUTO_INCREMENT,
                           `user_id` int NOT NULL,
                           `type_id` int NOT NULL,
                           `value` varchar(2048) NOT NULL,
                           FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);

CREATE TABLE `ContactType` (
                               `id` int PRIMARY KEY AUTO_INCREMENT,
                               `type` varchar(20) NOT NULL
);

CREATE TABLE `Blog` (
                        `id` int PRIMARY KEY AUTO_INCREMENT,
                        `user_id` int NOT NULL,
                        `name` varchar(20) NOT NULL,
                        `created_at` datetime NOT NULL DEFAULT NOW(),
                        FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);

CREATE TABLE `Category` (
                            `id` int PRIMARY KEY AUTO_INCREMENT,
                            `blog_id` int NOT NULL,
                            `name` varchar(20),
                            `created_at` datetime NOT NULL DEFAULT NOW(),
                            `updated_at` datetime DEFAULT NULL,
                            `deleted_at` datetime DEFAULT NULL,
                            FOREIGN KEY (`blog_id`) REFERENCES `Blog` (`id`)
);

CREATE TABLE `Post` (
                        `id` int PRIMARY KEY AUTO_INCREMENT,
                        `category_id` int NOT NULL,
                        `user_id` int NOT NULL,
                        `title` varchar(100) NOT NULL,
                        `content` varchar(2000),
                        `hit` int NOT NULL DEFAULT 0,
                        `created_at` datetime NOT NULL DEFAULT NOW(),
                        `updated_at` datetime DEFAULT NULL,
                        `deleted_at` datetime DEFAULT NULL,
                        FOREIGN KEY (`category_id`) REFERENCES `Category` (`id`),
                        FOREIGN KEY (`user_id`) REFERENCES `User` (`id`)
);

CREATE TABLE `Comment` (
                           `id` int PRIMARY KEY AUTO_INCREMENT,
                           `user_id` int NOT NULL,
                           `post_id` int NOT NULL,
                           `content` varchar(300) NOT NULL,
                           `created_at` datetime NOT NULL DEFAULT NOW(),
                           `updated_at` datetime DEFAULT NULL,
                           `deleted_at` datetime DEFAULT NULL,
                           FOREIGN KEY (`user_id`) REFERENCES `User` (`id`),
                           FOREIGN KEY (`post_id`) REFERENCES `Post` (`id`)
);

CREATE TABLE `PostAttachment` (
                                  `id` int PRIMARY KEY AUTO_INCREMENT,
                                  `post_id` int NOT NULL,
                                  `path` varchar(250),
                                  FOREIGN KEY (`post_id`) REFERENCES `Post` (`id`)
);

CREATE TABLE `PostThumbnailImage` (
                                      `id` int PRIMARY KEY,
                                      `path` varchar(250),
                                      FOREIGN KEY (`id`) REFERENCES `Post` (`id`)
);

CREATE TABLE `PostImage` (
                             `id` int PRIMARY KEY AUTO_INCREMENT,
                             `post_id` int NOT NULL,
                             `path` varchar(250),
                             FOREIGN KEY (`post_id`) REFERENCES `Post` (`id`)
);