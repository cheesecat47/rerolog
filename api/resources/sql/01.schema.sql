SET GLOBAL time_zone = '+00:00';

USE rerolog;

CREATE TABLE IF NOT EXISTS User
(
    id         int                NOT NULL AUTO_INCREMENT,
    id_str     varchar(20) UNIQUE NOT NULL COMMENT '유저 로그인 아이디',
    pw         varchar(12)        NOT NULL COMMENT '유저 비밀번호',
    name       varchar(20)        NOT NULL COMMENT '유저명',
    content    varchar(200)                DEFAULT null COMMENT '유저 소개 멘트',
    created_at datetime           NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    updated_at datetime                    DEFAULT null COMMENT '업데이트 시각',
    deleted_at datetime                    DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Session
(
    id         int NOT NULL,
    session_id varchar(20) DEFAULT null COMMENT '로그인 되지 않았다면 null',
    PRIMARY KEY (id),
    CONSTRAINT Session_id_fk
        FOREIGN KEY (id)
            REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS ContactType
(
    id   int         NOT NULL AUTO_INCREMENT,
    type varchar(20) NOT NULL COMMENT 'Email, LinkedIn, GitHub, WebSite 등',
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS Contact
(
    id      int           NOT NULL AUTO_INCREMENT,
    user_id int           NOT NULL COMMENT '유저 아이디',
    type_id int           NOT NULL COMMENT '연락 방법',
    value   varchar(2048) NOT NULL COMMENT '값. 만약 연락 방법이 blog이면 url이 올 수 있는데, 크롬 기준. https://zetawiki.com/wiki/URL의_최대_길이',
    PRIMARY KEY (id),
    CONSTRAINT Contact_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES User (id),
    CONSTRAINT Contact_type_id_fk
        FOREIGN KEY (type_id)
            REFERENCES ContactType (id)
);

CREATE TABLE IF NOT EXISTS Blog
(
    id      int         NOT NULL AUTO_INCREMENT,
    name    varchar(20) NOT NULL COMMENT '블로그 이름',
    content varchar(200) DEFAULT null COMMENT '블로그 소개',
    PRIMARY KEY (id),
    CONSTRAINT Blog_id_fk
        FOREIGN KEY (id)
            REFERENCES User (id)
);

CREATE TABLE IF NOT EXISTS Category
(
    id         int         NOT NULL AUTO_INCREMENT,
    blog_id    int         NOT NULL COMMENT '블로그 아이디',
    name       varchar(20) NOT NULL COMMENT '게시판 이름',
    created_at datetime    NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    updated_at datetime             DEFAULT null COMMENT '업데이트 시각',
    deleted_at datetime             DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (id),
    CONSTRAINT Category_blog_id_fk
        FOREIGN KEY (blog_id)
            REFERENCES Blog (id)
);

CREATE TABLE IF NOT EXISTS Post
(
    id          int           NOT NULL AUTO_INCREMENT,
    category_id int           NOT NULL COMMENT '글이 속한 게시판 아이디',
    user_id     int           NOT NULL COMMENT '유저(작성자) 아이디',
    title       varchar(100)  NOT NULL COMMENT '글 제목',
    excerpt     varchar(200) COMMENT '요약. null이면 content 중 앞에서 197자 + ... 문자열으로 채우기',
    content     varchar(2000) NOT NULL COMMENT '글 내용',
    hit         int           NOT NULL DEFAULT 0 COMMENT '조회수',
    created_at  datetime      NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    updated_at  datetime               DEFAULT null COMMENT '업데이트 시각',
    deleted_at  datetime               DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (id),
    CONSTRAINT Post_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES User (id),
    CONSTRAINT Post_category_id_fk
        FOREIGN KEY (category_id)
            REFERENCES Category (id)
);

CREATE TABLE IF NOT EXISTS Comment
(
    id         int          NOT NULL AUTO_INCREMENT,
    user_id    int          NOT NULL COMMENT '유저 아이디 번호',
    post_id    int          NOT NULL COMMENT '블로그 포스트 아이디',
    content    varchar(300) NOT NULL COMMENT '댓글 내용',
    created_at datetime     NOT NULL DEFAULT (now()) COMMENT '생성 시각',
    updated_at datetime              DEFAULT null COMMENT '업데이트 시각',
    deleted_at datetime              DEFAULT null COMMENT '삭제 시각',
    PRIMARY KEY (id),
    CONSTRAINT Comment_user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES User (id),
    CONSTRAINT Comment_post_id_fk
        FOREIGN KEY (post_id)
            REFERENCES Post (id)
);

CREATE TABLE IF NOT EXISTS PostAttachment
(
    id      int NOT NULL AUTO_INCREMENT,
    post_id int NOT NULL,
    path    varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (id),
    CONSTRAINT PostAttachment_post_id_fk
        FOREIGN KEY (post_id)
            REFERENCES Post (id)
);

CREATE TABLE IF NOT EXISTS PostThumbnailImage
(
    id   int NOT NULL,
    path varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (id),
    CONSTRAINT PostThumbnailImage_post_id_fk
        FOREIGN KEY (id)
            REFERENCES Post (id)
);

CREATE TABLE IF NOT EXISTS UserProfileImage
(
    id   int NOT NULL,
    path varchar(250) COMMENT '파일 경로',
    PRIMARY KEY (id),
    CONSTRAINT UserProfileImage_user_id_fk
        FOREIGN KEY (id)
            REFERENCES User (id)
);
