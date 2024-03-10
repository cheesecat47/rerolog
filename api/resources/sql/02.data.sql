/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

USE rerolog;

INSERT INTO User (id_str, pw, name, content)
VALUES ('cheesecat47', '1234', '신주용', '안녕하세요, 신주용입니다.'),
       ('rosielsh', '1234', '이수화', '안녕하세요, 이수화입니다.');
SET @user_cheesecat47 = (SELECT id
                         FROM User
                         WHERE `id_str` = 'cheesecat47');
SET @user_rosielsh = (SELECT id
                         FROM User
                         WHERE `id_str` = 'rosielsh');

INSERT INTO ContactType (type)
VALUES ('Email'),
       ('GitHub'),
       ('LinkedIn'),
       ('WebSite');
SET @contacttype_email = (SELECT id
                          FROM ContactType
                          WHERE type = 'Email');
SET @contacttype_github = (SELECT id
                           FROM ContactType
                           WHERE type = 'GitHub');
SET @contacttype_linkedin = (SELECT id
                             FROM ContactType
                             WHERE type = 'LinkedIn');
SET @contacttype_website = (SELECT id
                            FROM ContactType
                            WHERE type = 'WebSite');

INSERT INTO Contact (user_id, type_id, value)
VALUES (@user_cheesecat47, @contacttype_email, 'cheesecat47@gmail.com'),
       (@user_cheesecat47, @contacttype_github, 'https://github.com/cheesecat47'),
       (@user_cheesecat47, @contacttype_linkedin, 'https://www.linkedin.com/in/shinjuyong/'),
       (@user_cheesecat47, @contacttype_website, 'https://cheesecat47.github.io'),
       (@user_rosielsh, @contacttype_github, 'https://github.com/cheesecat47');

INSERT INTO Blog (id, name)
VALUES (@user_cheesecat47, 'La foret rouge'),
       (@user_rosielsh, 'rosie.log');
SET @blogid_cheesecat47 = (SELECT id
                           FROM Blog
                           WHERE id = @user_cheesecat47);
SET @blogid_rosielsh = (SELECT id
                           FROM Blog
                           WHERE id = @user_rosielsh);

INSERT INTO Category (blog_id, name, deleted_at)
VALUES (@blogid_cheesecat47, 'Java', null),
       (@blogid_cheesecat47, 'Spring Boot', null),
       (@blogid_cheesecat47, 'Vue.js', now()), -- 조회 결과에 안 나와야 함
       (@blogid_cheesecat47, '알고리즘 문제', null),
       (@blogid_rosielsh, 'Front-end', null),
       (@blogid_rosielsh, '회고록', null),
       (@blogid_rosielsh, 'CS', now()); -- 조회 결과에 안 나와야 함
SET @categoryid_java = (SELECT id
                        FROM Category
                        WHERE name = 'Java');
SET @categoryid_vue = (SELECT id
                        FROM Category
                        WHERE name = 'Vue.js');
SET @categoryid_algo = (SELECT id
                        FROM Category
                        WHERE name = '알고리즘 문제');
SET @categoryid_fe = (SELECT id
                        FROM Category
                        WHERE name = 'Front-end');
SET @categoryid_cs = (SELECT id
                      FROM Category
                      WHERE name = 'CS');

INSERT INTO Post (category_id, user_id, title, content, hit, created_at, deleted_at)
VALUES (@categoryid_java, @user_cheesecat47, 'Java 너무 재미있어요.',
        'Java 너무 재미있어요. Objective Oriented Programming 익숙해지니 너무 좋아요.', 5,
        date_sub(now(), interval 5 minute), null),
       (@categoryid_java, @user_cheesecat47, 'Java 별로에요.',
        '삭제된 게시글. 보이지 않아야 함.', 2,
        date_sub(now(), interval 4 minute), now()),
       (@categoryid_vue, @user_cheesecat47, 'Vue3 좋은데요?',
        '삭제된 게시판. 보이지 않아야 함.', 1,
        date_sub(now(), interval 3 minute), null),
       (@categoryid_algo, @user_cheesecat47, '알고리즘 쉽지 않네요.', '다익스트라 알고리즘 조금 어려운 것 같아요. 다시 복습해야겠어요.', 5,
        date_sub(now(), interval 2 minute), null),
       (@categoryid_fe, @user_rosielsh, '프론트엔드 짱짱.',
        '리액트로 프론트 공부하기!', 12,
        date_sub(now(), interval 1 minute), null),
       (@categoryid_cs, @user_rosielsh, 'CS 공부 할 거 엄청 많아.',
        '삭제된 게시글. 보이지 않아야 함.', 3,
        now(), now());
SET @postid_java_1 = (SELECT id
                      FROM Post
                      WHERE title = 'Java 너무 재미있어요.');

INSERT INTO Comment (user_id, post_id, content, created_at, deleted_at)
VALUES (@user_cheesecat47, @postid_java_1, '댓글댓글!',
        date_sub(now(), interval 1 minute), null),
       (@user_cheesecat47, @postid_java_1, '삭제된 댓글댓글!',
        now(), now());

INSERT INTO UserProfileImage (id, path)
VALUES (@user_cheesecat47,
        'https://raw.githubusercontent.com/cheesecat47/myBlog/main/myBlog-frontend/src/assets/6B152AA5-CC62-49B6-913A-7C702AF22F1E_1_102_o.jpeg'),
       (@user_rosielsh,
        'https://avatars.githubusercontent.com/u/72565083?v=4');

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
