CREATE SCHEMA IF NOT EXISTS `myBlog` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `myBlog`;

CREATE TABLE IF NOT EXISTS `myBlog`.`User`
(
    `id`            int                NOT NULL AUTO_INCREMENT,
    `id_str`        varchar(20) UNIQUE NOT NULL COMMENT '유저 로그인 아이디',
    `pw`            varchar(12)        NOT NULL COMMENT '유저 비밀번호',
    `email_account` varchar(50)        NOT NULL COMMENT '이메일 계정 부분',
    `email_domain`  varchar(50)        NOT NULL COMMENT '이메일 도메인 부분',
    `content`       varchar(200)                DEFAULT null COMMENT '유저 소개 멘트',
    `session_id`    varchar(20)                 DEFAULT null COMMENT '로그인 되지 않았다면 null',
    `created_at`    datetime           NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    `updated_at`    datetime                    DEFAULT null COMMENT '업데이트 시각',
    `deleted_at`    datetime                    DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`ContactType`
(
    `id`   int         NOT NULL AUTO_INCREMENT,
    `type` varchar(20) NOT NULL COMMENT 'email, linkedin, github, website 등',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`Contact`
(
    `id`      int           NOT NULL AUTO_INCREMENT,
    `user_id` int           NOT NULL COMMENT '유저 아이디',
    `type_id` int           NOT NULL COMMENT '연락 방법',
    `value`   varchar(2048) NOT NULL COMMENT '값. 만약 연락 방법이 blog이면 url이 올 수 있는데, 크롬 기준. https://zetawiki.com/wiki/URL의_최대_길이',
    PRIMARY KEY (`id`),
    CONSTRAINT `Contact_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `myBlog`.`User` (`id`),
    CONSTRAINT `Contact_type_id_fk`
        FOREIGN KEY (`type_id`)
            REFERENCES `myBlog`.`ContactType` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`Blog`
(
    `id`         int         NOT NULL AUTO_INCREMENT,
    `user_id`    int         NOT NULL COMMENT '유저 아이디',
    `name`       varchar(20) NOT NULL COMMENT '블로그 이름',
    `created_at` datetime    NOT NULL DEFAULT (now()) COMMENT '블로그 개설일',
    PRIMARY KEY (`id`),
    CONSTRAINT `Blog_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `myBlog`.`User` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`Category`
(
    `id`         int      NOT NULL AUTO_INCREMENT,
    `blog_id`    int      NOT NULL COMMENT '블로그 아이디',
    `name`       varchar(20) COMMENT '카테고리명',
    `created_at` datetime NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    `updated_at` datetime          DEFAULT null COMMENT '업데이트 시각',
    `deleted_at` datetime          DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (`id`),
    CONSTRAINT `Category_blog_id_fk`
        FOREIGN KEY (`blog_id`)
            REFERENCES `myBlog`.`Blog` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`Post`
(
    `id`          int          NOT NULL AUTO_INCREMENT,
    `category_id` int          NOT NULL COMMENT '포스트가 속한 카테고리 아이디',
    `user_id`     int          NOT NULL COMMENT '유저(작성자) 아이디',
    `title`       varchar(100) NOT NULL COMMENT '포스트 제목',
    `content`     varchar(2000) COMMENT '포스트 내용',
    `hit`         int          NOT NULL DEFAULT 0 COMMENT '조회수',
    `created_at`  datetime     NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    `updated_at`  datetime              DEFAULT null COMMENT '업데이트 시각',
    `deleted_at`  datetime              DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (`id`),
    CONSTRAINT `Post_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `myBlog`.`User` (`id`),
    CONSTRAINT `Post_category_id_fk`
        FOREIGN KEY (`category_id`)
            REFERENCES `myBlog`.`Category` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`Comment`
(
    `id`         int          NOT NULL AUTO_INCREMENT,
    `user_id`    int          NOT NULL COMMENT '유저 아이디 번호',
    `post_id`    int          NOT NULL COMMENT '블로그 포스트 아이디',
    `content`    varchar(300) NOT NULL COMMENT '댓글 내용',
    `created_at` datetime     NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    `updated_at` datetime              DEFAULT null COMMENT '업데이트 시각',
    `deleted_at` datetime              DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (`id`),
    CONSTRAINT `Comment_user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `myBlog`.`User` (`id`),
    CONSTRAINT `Comment_post_id_fk`
        FOREIGN KEY (`post_id`)
            REFERENCES `myBlog`.`Post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`PostAttachment`
(
    `id`      int NOT NULL AUTO_INCREMENT,
    `post_id` int NOT NULL,
    `path`    varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (`id`),
    CONSTRAINT `PostAttachment_post_id_fk`
        FOREIGN KEY (`post_id`)
            REFERENCES `myBlog`.`Post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`PostThumbnailImage`
(
    `id`   int NOT NULL,
    `path` varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (`id`),
    CONSTRAINT `PostThumbnailImage_post_id_fk`
        FOREIGN KEY (`id`)
            REFERENCES `myBlog`.`Post` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `myBlog`.`UserProfileImage`
(
    `id`   int NOT NULL,
    `path` varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (`id`),
    CONSTRAINT `UserProfileImage_user_id_fk`
        FOREIGN KEY (`id`)
            REFERENCES `myBlog`.`User` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci;
