USE `myBlog`;

INSERT INTO myBlog.User (id_str, pw, name, content)
VALUES ('cheesecat47', '1234', '신주용', '안녕하세요, 신주용입니다.');
SET @user_cheesecat47 = (SELECT id
                         FROM myBlog.User
                         WHERE `id_str` = 'cheesecat47');

INSERT INTO myBlog.ContactType (type)
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

INSERT INTO myBlog.Contact (user_id, type_id, value)
VALUES (@user_cheesecat47, @contacttype_email, 'cheesecat47@gmail.com'),
       (@user_cheesecat47, @contacttype_github, 'https://github.com/cheesecat47'),
       (@user_cheesecat47, @contacttype_linkedin, 'https://www.linkedin.com/in/shinjuyong/'),
       (@user_cheesecat47, @contacttype_website, 'https://cheesecat47.github.io');

INSERT INTO myBlog.Blog (id, name)
VALUES (@user_cheesecat47, 'La foret rouge');
SET @blogid_cheesecat47 = (SELECT id
                           FROM myBlog.Blog
                           WHERE id = @user_cheesecat47);

INSERT INTO myBlog.Category (blog_id, name)
VALUES (@blogid_cheesecat47, 'Java'),
       (@blogid_cheesecat47, 'Spring Boot'),
       (@blogid_cheesecat47, 'Vue.js'),
       (@blogid_cheesecat47, '알고리즘 문제');
SET @categoryid_java = (SELECT id
                        FROM myBlog.Category
                        WHERE name = 'Java');
SET @categoryid_algo = (SELECT id
                        FROM myBlog.Category
                        WHERE name = '알고리즘 문제');

INSERT INTO myBlog.Post (category_id, user_id, title, content)
VALUES (@categoryid_java, @user_cheesecat47, 'Java 너무 재미있어요.',
        'Java 너무 재미있어요. Objective Oriented Programming 익숙해지니 너무 좋아요.'),
       (@categoryid_algo, @user_cheesecat47, '알고리즘 쉽지 않네요.', '다익스트라 알고리즘 조금 어려운 것 같아요. 다시 복습해야겠어요.');
SET @postid_java_1 = (SELECT id
                      FROM Post
                      WHERE title = 'Java 너무 재미있어요.');

INSERT INTO myBlog.Comment (user_id, post_id, content)
VALUES (@user_cheesecat47, @postid_java_1, '댓글댓글!');

INSERT INTO myBlog.UserProfileImage (id, path)
VALUES (@user_cheesecat47,
        'https://raw.githubusercontent.com/cheesecat47/myBlog/main/myBlog-frontend/src/assets/6B152AA5-CC62-49B6-913A-7C702AF22F1E_1_102_o.jpeg');
