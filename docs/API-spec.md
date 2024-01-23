# API 명세서

* [API 명세서](#api-명세서)
  * [응답 공통 형식](#응답-공통-형식)
  * [User](#user)
    * [getUserInfo 유저 정보 조회](#getuserinfo-유저-정보-조회)
    * [signUp 회원 가입](#signup-회원-가입)
    * [updateUserInfo 유저 정보 변경](#updateuserinfo-유저-정보-변경)
    * [deleteUserInfo 회원 탈퇴](#deleteuserinfo-회원-탈퇴)
    * [logIn 로그인](#login-로그인)
    * [logOut 로그 아웃](#logout-로그-아웃)
    * [refresh 액세스 토큰 재발급](#refresh-액세스-토큰-재발급)
  * [Blog](#blog)
    * [getBlogInfo 블로그 정보 조회](#getbloginfo-블로그-정보-조회)
    * [updateBlogInfo 블로그 정보 변경](#updatebloginfo-블로그-정보-변경)
  * [Category](#category)
    * [getCategories 게시판 목록 조회](#getcategories-게시판-목록-조회)
    * [createCategory 게시판 생성](#createcategory-게시판-생성)
    * [updateCategory 게시판 정보 변경](#updatecategory-게시판-정보-변경)
    * [deleteCategory 게시판 삭제](#deletecategory-게시판-삭제)
  * [Post](#post)
    * [getPosts 글 목록 조회](#getposts-글-목록-조회)
    * [getPostById 글 상세 조회](#getpostbyid-글-상세-조회)
    * [createPost 글 작성](#createpost-글-작성)
    * [updatePost 글 수정](#updatepost-글-수정)
    * [deletePost 글 삭제](#deletepost-글-삭제)
  * [Comment](#comment)
    * [getCommentsByPostId 특정 글에 달린 댓글 목록 조회](#getcommentsbypostid-특정-글에-달린-댓글-목록-조회)
    * [createComment 댓글 작성](#createcomment-댓글-작성)
    * [updateComment 댓글 수정](#updatecomment-댓글-수정)
    * [deleteComment 댓글 삭제](#deletecomment-댓글-삭제)
  * [에러 코드 정리](#에러-코드-정리)
  * [References](#references)

---

## 응답 공통 형식

|   Name    |   Data Type   |                   Description                    | 
|:---------:|:-------------:|:------------------------------------------------:|
| `message` |   `String`    |                      응답 메시지                      |
|  `code`   |   `String`    |                      응답 코드                       |
|  `data`   | `Object\|Map` | 요청 성공시 데이터 객체 포함, 실패 시 실패와 관련된 정보를 `Map` 타입으로 포함 |

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "NORMAL_SERVICE",
  "code": "00",
  "data": {}
}
```

---

## User

### getUserInfo 유저 정보 조회

- 유저 아이디를 사용해 해당 유저 정보 조회.

```http request
GET /api/user/:userId
```

#### 요청

| Param Type |   Name   | Data Type | Required |      Description       |
|:----------:|:--------:|:---------:|:--------:|:----------------------:|
|    Path    | `userId` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 |

#### 응답

##### 응답 본문

|   Name    |     Data Type      |     Description     | 
|:---------:|:------------------:|:-------------------:|
| `message` |      `String`      |       응답 메시지        |
|  `code`   |      `String`      |        응답 코드        |
|  `data`   | `UserInfoDto\|Map` | 유저 정보 객체 또는 오류 정보 맵 |

##### UserInfoDto

|     Name     |     Data Type      |               Description               | 
|:------------:|:------------------:|:---------------------------------------:|
|    userId    |      `String`      |         유저 아이디. DB의 `id_str` 값          |
|   nickName   |      `String`      |                  유저 별명                  |
|   content    |      `String`      |   유저 소개. 소개 멘트 부재 시 길이 0인 문자열 `""` 반환   |
|  createdAt   |      `String`      |       회원 가입일. ISO 8601 형식. UTC 기준       |
| profileImage |   `String\|null`   | 프로필 이미지 URL. `null`이면 임의의 기본 이미지로 대체 필요 |
|   contacts   | `List<ContactDto>` |   연락처 배열. 등록된 연락처가 없으면 길이 0인 배열 `[]`    |

##### ContactDto

| Name  | Data Type |                  Description                  | 
|:-----:|:---------:|:---------------------------------------------:|
| type  | `String`  | "Email", "GitHub", "LinkedIn", "WebSite" 중 하나 |
| value | `String`  |                     연락처 값                     |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "유저 정보 조회 성공",
  "code": "00",
  "data": {
    "userId": "cheesecat47",
    "nickName": "신주용",
    "content": "안녕하세요, 신주용입니다.",
    "createdAt": "2023-12-20T09:00:00Z",
    "profileImage": null,
    "contacts": [
      {
        "type": "Email",
        "value": "cheesecat47@gmail.com"
      },
      ...
    ]
  }
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "code": "13",
  "data": {
    "userId": "cheesecat$&"
  },
  "message": "입력한 아이디에 해당하는 유저가 없습니다."
}
``` 

### signUp 회원 가입

- 회원 가입.

```http request
POST /api/user
```

#### 요청

| Param Type |        Name        |     Data Type      | Required |         Description         |
|:----------:|:------------------:|:------------------:|:--------:|:---------------------------:|
|    Body    |      `userId`      |      `String`      |    O     |   유저 아이디. DB의 `id_str` 값    |
|    Body    |      `userPw`      |      `String`      |    O     |           유저 비밀번호           |
|    Body    |     `nickName`     |      `String`      |    O     |     유저 별명. DB의 `name` 값     |
|    Body    |     `content`      |      `String`      |    -     | 유저 소개 글. 최대 200자. 기본 값 `""` |
|    Body    |     `contacts`     | `List<ContactDto>` |    -     |          연락처 객체 배열          |
|  ~~Body~~  | ~~`profileImage`~~ |     ~~`File`~~     |    -     |       ~~프로필 이미지~~ 준비중       |

##### ContactDto

| Name  | Data Type |                  Description                  | 
|:-----:|:---------:|:---------------------------------------------:|
| type  | `String`  | "Email", "GitHub", "LinkedIn", "WebSite" 중 하나 |
| value | `String`  |                     연락처 값                     |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/user' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "userPw": "1234",
  "nickName": "refo",
  "content": "안녕하세요!",
  "contacts": [
    {
      "type": "Email",
      "value": "cheesecat47@gmail.com"
    },
    ...
  ]
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
{
  "message": "회원 가입 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력 값 형식이 유효하지 않습니다",
  "code": "11",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

### updateUserInfo 유저 정보 변경

- 유저 아이디를 사용해 해당 유저 정보 변경.

```http request
PATCH /api/user/:userId
```

#### 요청

| Param Type |        Name        |     Data Type      | Required |                   Description                    |
|:----------:|:------------------:|:------------------:|:--------:|:------------------------------------------------:|
|   Header   |  `Authorization`   |      `String`      |    O     | `Bearer` + (공백 하나 포함) + `로그인할 때 받은 Access Token` |
|    Path    |      `userId`      |      `String`      |    O     |              유저 아이디. DB의 `id_str` 값              |
|    Body    |      `userPw`      |      `String`      |    -     |                     유저 비밀번호                      |
|    Body    |     `nickName`     |      `String`      |    -     |               유저 별명. DB의 `name` 값                |
|    Body    |     `content`      |      `String`      |    -     |           유저 소개 글. 최대 200자. 기본 값 `""`            |
|    Body    |     `contacts`     | `List<ContactDto>` |    -     |                    연락처 객체 배열                     |
|  ~~Body~~  | ~~`profileImage`~~ |     ~~`File`~~     |    -     |                 ~~프로필 이미지~~ 준비중                  |

##### ContactDto

| Name  | Data Type |                  Description                  | 
|:-----:|:---------:|:---------------------------------------------:|
| type  | `String`  | "Email", "GitHub", "LinkedIn", "WebSite" 중 하나 |
| value | `String`  |                     연락처 값                     |

##### 예시

```bash
curl -X 'PATCH' \
  'http://localhost:8080/api/user/cheesecat47' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Content-Type: application/json' \
  -d '{
  "userPw": "1235",
  "content": "안녕하세요!!",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "유저 정보 변경 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력 값 형식이 유효하지 않습니다",
  "code": "11",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "로그인 후 이용 바랍니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "cheesecat47"
  }
}
```

### deleteUserInfo 회원 탈퇴

- 유저 아이디를 사용해 해당 유저 회원 탈퇴.

```http request
DELETE /api/user/:userId
```

#### 요청

| Param Type |        Name        |     Data Type      | Required |                   Description                    |
|:----------:|:------------------:|:------------------:|:--------:|:------------------------------------------------:|
|   Header   |  `Authorization`   |      `String`      |    O     | `Bearer` + (공백 하나 포함) + `로그인할 때 받은 Access Token` |
|    Path    |      `userId`      |      `String`      |    O     |              유저 아이디. DB의 `id_str` 값              |

##### 예시

```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/user/cheesecat47' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Content-Type: application/json'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "회원 탈퇴 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력 값 형식이 유효하지 않습니다",
  "code": "11",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "로그인 후 이용 바랍니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "cheesecat47"
  }
}
```

### logIn 로그인

- 로그인.

```http request
POST /api/user/login
```

#### 요청

| Param Type |   Name   | Data Type | Required |      Description       |
|:----------:|:--------:|:---------:|:--------:|:----------------------:|
|    Body    | `userId` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 |
|    Body    | `userPw` | `String`  |    O     |        유저 비밀번호         |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/user/login' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "userPw": "1234"
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "code": "00",
  "message": "로그인 성공",
  "data": {
    "userId": "cheesecat47",
    "accessToken": "eyJ0eXAiOi...",
    "refreshToken": "eyJ0eXAiOi..."
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "code": "12",
  "data": {
    "userPw": "123!",
    "userId": "cheesecat47"
  },
  "message": "로그인에 실패했습니다"
}
```

### logOut 로그 아웃

- 로그 아웃.

```http request
POST /api/user/logout
```

#### 요청

| Param Type |      Name       | Data Type | Required |                   Description                    |
|:----------:|:---------------:|:---------:|:--------:|:------------------------------------------------:|
|   Header   | `Authorization` | `String`  |    O     | `Bearer` + (공백 하나 포함) + `로그인할 때 받은 Access Token` |
|    Body    |    `userId`     | `String`  |    O     |         로그 아웃 하려는 유저 아이디. DB의 `id_str` 값         |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/user/logout' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47"
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "code": "00",
  "message": "로그 아웃 성공",
  "data": null
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "cheesecat47"
  },
  "message": "로그인 후 이용 바랍니다"
}
```

### refresh 액세스 토큰 재발급

- 만료된 액세스 토큰을 재발급.

```http request
POST /api/user/refresh
```

#### 요청

| Param Type |      Name      | Data Type | Required |                    Description                    |
|:----------:|:--------------:|:---------:|:--------:|:-------------------------------------------------:|
|    Body    |    `userId`    | `String`  |    O     |         토큰을 재발급하려는 유저 아이디. DB의 `id_str` 값         |
|    Body    | `refreshToken` | `String`  |    O     | `Bearer` + (공백 하나 포함) + `로그인할 때 받은 refresh Token` |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/user/refresh' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "refreshToken": "Bearer eyJ0eXAiOi..."
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "code": "00",
  "message": "액세스 토큰 재발급 성공",
  "data": {
    "userId": "cheesecat47",
    "accessToken": "eyJ0eXAiOi...",
    "refreshToken": null
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "code": "12",
  "data": {
    "refreshToken": "Bearer eyJ0eXAiOi..."
  },
  "message": "유효하지 않은 리프레시 토큰입니다"
}
```

---

## Blog

### getBlogInfo 블로그 정보 조회

- 블로그 아이디를 사용해 해당 블로그 정보 조회.

```http request
GET /api/blog/:blogId
```

#### 요청

| Param Type |   Name   | Data Type | Required |          Description          | 
|:----------:|:--------:|:---------:|:--------:|:-----------------------------:|
|    Path    | `blogId` | `String`  |    O     | 블로그 아이디. 유저 아이디(`userId`)와 동일 |

#### 응답

##### 응답 본문

|   Name    |     Data Type      |     Description      | 
|:---------:|:------------------:|:--------------------:|
| `message` |      `String`      |        응답 메시지        |
|  `code`   |      `String`      |        응답 코드         |
|  `data`   | `BlogInfoDto\|Map` | 블로그 정보 객체 또는 오류 정보 맵 |

##### BlogInfoDto

|   Name    | Data Type |             Description              | 
|:---------:|:---------:|:------------------------------------:|
|  blogId   | `String`  |    블로그 아이디. 유저 아이디(`userId`)와 동일     |
| blogName  | `String`  |                블로그 이름                |
|  content  | `String`  | 블로그 소개. 소개 멘트 부재 시 길이 0인 문자열 `""` 반환 |
| createdAt | `String`  |     블로그 개설일. ISO 8601 형식. UTC 기준     |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "블로그 정보 조회 성공",
  "code": "00",
  "data": {
    "blogId": "cheesecat47",
    "blogName": "La foret rouge",
    "content": "",
    "createdAt": "2023-12-20T09:00:00Z"
  }
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력한 아이디에 해당하는 블로그가 없습니다.",
  "code": "13",
  "data": {
    "blogId": "cheesecat$&"
  }
}
``` 

### updateBlogInfo 블로그 정보 변경

- 블로그 정보 변경.

```http request
PATCH /api/blog/:blogId
```

#### 요청

| Param Type |   Name    | Data Type | Required |             Description              | 
|:----------:|:---------:|:---------:|:--------:|:------------------------------------:|
|    Path    | `blogId`  | `String`  |    O     |    블로그 아이디. 유저 아이디(`userId`)와 동일     |
|    Body    | `content` | `String`  |    -     | 블로그 소개. 소개 멘트 부재 시 길이 0인 문자열 `""` 반환 |

#### 응답

##### 응답 본문

|   Name    | Data Type | Description | 
|:---------:|:---------:|:-----------:|
| `message` | `String`  |   응답 메시지    |
|  `code`   | `String`  |    응답 코드    |
|  `data`   |   `Map`   |   오류 정보 맵   |

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "블로그 정보 변경 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력한 아이디에 해당하는 블로그가 없습니다.",
  "code": "13",
  "data": {
    "blogId": "cheesecat$&"
  }
}
``` 

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "블로그 정보 변경은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

## Category

### getCategories 게시판 목록 조회

- 블로그에 있는 게시판 목록 조회.

```http request
GET /api/blog/:blogId/category
```

#### 요청

| Param Type |   Name   | Data Type | Required |          Description          | 
|:----------:|:--------:|:---------:|:--------:|:-----------------------------:|
|    Path    | `blogId` | `String`  |    O     | 블로그 아이디. 유저 아이디(`userId`)와 동일 |

#### 응답

##### 응답 본문

|   Name    |        Data Type         |     Description      | 
|:---------:|:------------------------:|:--------------------:|
| `message` |         `String`         |        응답 메시지        |
|  `code`   |         `String`         |        응답 코드         |
|  `data`   | `List<CategoryDto>\|Map` | 게시판 정보 배열 또는 오류 정보 맵 |

##### CategoryDto

|     Name     | Data Type | Description | 
|:------------:|:---------:|:-----------:|
|  categoryId  |   `int`   |   게시판 아이디   |
| categoryName | `String`  |   게시판 이름    |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 목록 조회 성공",
  "code": "00",
  "data": [
    {
      "categoryId": 1,
      "categoryName": "Java"
    },
    ...
  ]
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력한 아이디에 해당하는 블로그가 없습니다.",
  "code": "13",
  "data": {
    "blogId": "cheesecat$&"
  }
}
```

### createCategory 게시판 생성

- 게시판 생성.

```http request
POST /api/blog/:blogId/category
```

#### 요청

| Param Type |      Name       | Data Type | Required |          Description          | 
|:----------:|:---------------:|:---------:|:--------:|:-----------------------------:|
|    Path    |    `blogId`     | `String`  |    O     | 블로그 아이디. 유저 아이디(`userId`)와 동일 |
|   Header   | `Authorization` | `String`  |    O     |   액세스 토큰. 본인 블로그 게시판 생성 가능    |
|    Body    | `categoryName`  | `String`  |    O     |            게시판 이름             |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/blog/cheesecat47/category' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "categoryName": "Spring 스터디",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 생성 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 이름 형식에 맞지 않습니다",
  "code": "11",
  "data": {
    "categoryName": "!Spring!"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 생성은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

### updateCategory 게시판 정보 변경

- 게시판 정보 변경.

```http request
PATCH /api/blog/:blogId/category/:categoryName
```

#### 요청

| Param Type |       Name        | Data Type | Required |          Description          |
|:----------:|:-----------------:|:---------:|:--------:|:-----------------------------:|
|    Path    |     `blogId`      | `String`  |    O     | 블로그 아이디. 유저 아이디(`userId`)와 동일 |
|    Path    |  `categoryName`   | `String`  |    O     |       정보를 변경하려는 게시판 이름        |
|   Header   |  `Authorization`  | `String`  |    O     |  액세스 토큰. 본인 블로그 게시판 정보 변경 가능  |
|    Body    | `newCategoryName` | `String`  |    O     |           새 게시판 이름            |

##### 예시

```bash
curl -X 'PATCH' \
  'http://localhost:8080/api/blog/cheesecat47/category/Spring-스터디' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "newCategoryName": "Spring Boot 스터디",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 정보 변경 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 이름 형식에 맞지 않습니다",
  "code": "11",
  "data": {
    "categoryName": "!Spring!"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 정보 변경은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

### deleteCategory 게시판 삭제

- 게시판 삭제

```http request
DELETE /api/blog/:blogId/category/:categoryName
```

#### 요청

| Param Type |      Name       | Data Type | Required |          Description          |
|:----------:|:---------------:|:---------:|:--------:|:-----------------------------:|
|    Path    |    `blogId`     | `String`  |    O     | 블로그 아이디. 유저 아이디(`userId`)와 동일 |
|    Path    | `categoryName`  | `String`  |    O     |         삭제하려는 게시판 이름          |
|   Header   | `Authorization` | `String`  |    O     |   액세스 토큰. 본인 블로그 게시판 삭제 가능    |

##### 예시

```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/blog/cheesecat47/category/Spring-스터디' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 삭제 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 정보 변경은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

---

## Post

### getPosts 글 목록 조회

- 조건에 일치하는 글 목록 조회

```http request
GET /api/post?userId=&categoryName=&order=recent&offset=0&limit=10
```

#### 요청

| Param Type |      Name      | Data Type | Required |                               Description                                |  
|:----------:|:--------------:|:---------:|:--------:|:------------------------------------------------------------------------:|
|   Query    |    `userId`    | `String`  |    -     |                                  유저 아이디                                  |
|   Query    | `categoryName` | `String`  |    -     |             게시판 이름<br/>특정 게시판에 속한 글만 필터링 할 때 사용. 없으면 전체 글 목록             |
|   Query    |    `order`     | `String`  |    -     | 정렬 방법. `latest(최신순)`,`oldest(오래된순)`,`popular(인기순)` 중 택 1. 기본 값은 `latest` |
|   Query    |    `offset`    |   `int`   |    -     |                      정렬된 결과 중 `offset`부터 반환. 기본값 0                       |
|   Query    |    `limit`     |   `int`   |    -     |                      `offset`부터 `limit`개 조회. 기본값 10                      |

##### 예시

```bash
curl -X 'GET' \
  'http://localhost:8080/api/post?categoryName=알고리즘-문제&order=popular' \
  -H 'accept: application/json;charset=utf-8'

# 본 예시에서는 `알고리즘-문제`로 보이나 실제 URL은 인코딩으로 인해 다음과 같음:
# http://localhost:8080/api/post?categoryName=%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%EB%AC%B8%EC%A0%9C&order=popular
```

#### 응답

##### 응답 본문

|   Name    |      Data Type       |                          Description                           | 
|:---------:|:--------------------:|:--------------------------------------------------------------:|
| `message` |       `String`       |                             응답 메시지                             |
|  `code`   |       `String`       |                             응답 코드                              |
|  `data`   | `List<PostDto>\|Map` | 조건에 일치하는 글 목록 또는 해당하는 글이 없는 경우 길이가 0인 배열 `[]`. 오류 발생 시 오류 정보 맵 |

##### PostDto

|     Name      |   Data Type   |       Description        |
|:-------------:|:-------------:|:------------------------:|
|    postId     |     `int`     |          글 아이디           |
|  categoryId   |     `int`     |         게시판 아이디          |
| categoryName  |   `String`    |          게시판 이름          |
|     title     |   `String`    |            제목            |
|    author     | `UserInfoDto` |          작성자 정보          |
|   createdAt   |   `String`    | 작성일. ISO 8601 형식. UTC 기준 |
|      hit      |     `int`     |           조회수            |
|    excerpt    |   `String`    |            요약            |
|   thumbnail   |   `String`    |         썸네일 URL          |
| numOfComments |     `int`     |          댓글 개수           |

##### UserInfoDto

|     Name     |   Data Type    |               Description               | 
|:------------:|:--------------:|:---------------------------------------:|
|    userId    |    `String`    |         유저 아이디. DB의 `id_str` 값          |
|   nickName   |    `String`    |                  유저 별명                  |
| profileImage | `String\|null` | 프로필 이미지 URL. `null`이면 임의의 기본 이미지로 대체 필요 |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 목록 조회 성공",
  "code": "00",
  "data": [
    {
      "postId": 4,
      "categoryId": 4,
      "categoryName": "알고리즘 문제",
      "title": "알고리즘 쉽지 않네요.",
      "author": {
        "userId": "cheesecat47",
        "nickName": "신주용",
        "profileImage": null
      },
      "createdAt": "2024-01-17T11:00:15Z",
      "hit": 5,
      "excerpt": "...",
      "thumbnail": null,
      "content": null,
      "comments": null,
      "numOfComments": 0
    }
  ]
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "입력한 아이디에 해당하는 유저가 없습니다",
  "code": "13",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

### getPostByTitle 글 상세 조회

- 글 상세 조회. 이 글과 연결된 댓글은 [getCommentsByPostId 특정 글에 달린 댓글 목록 조회](#getcommentsbypostid-특정-글에-달린-댓글-목록-조회) API 사용.

```http request
GET /api/post/:postTitle
```

#### 요청

| Param Type |    Name     | Data Type | Required | Description |
|:----------:|:-----------:|:---------:|:--------:|:-----------:|
|    Path    | `postTitle` | `String`  |    O     |    글 제목     |

##### 예시

```bash
curl -X 'GET' \
  'http://localhost:8080/api/post/Java-너무-재미있어요.' \
  -H 'accept: application/json;charset=utf-8'

# 본 예시에서는 `Java-너무-재미있어요.`로 보이나 실제 URL은 인코딩으로 인해 다음과 같음:
# http://localhost:8080/api/post/Java-%EB%84%88%EB%AC%B4-%EC%9E%AC%EB%AF%B8%EC%9E%88%EC%96%B4%EC%9A%94.
```

#### 응답

##### 응답 본문

|   Name    |    Data Type    |     Description      | 
|:---------:|:---------------:|:--------------------:|
| `message` |    `String`     |        응답 메시지        |
|  `code`   |    `String`     |        응답 코드         |
|  `data`   | `PostDto\| Map` | 게시글 정보 객체 또는 오류 정보 맵 |

##### PostDto

|     Name      |     Data Type      |              Description              | 
|:-------------:|:------------------:|:-------------------------------------:|
|    postId     |       `int`        |                 글 아이디                 |
|  categoryId   |       `int`        |                게시판 아이디                |
| categoryName  |      `String`      |                게시판 이름                 |
|     title     |      `String`      |                  제목                   |
|    author     |   `UserInfoDto`    |                작성자 정보                 |
|   createdAt   |      `String`      |       작성일. ISO 8601 형식. UTC 기준        |
|      hit      |       `int`        |                  조회수                  |
|    excerpt    |      `String`      |                  요약                   |
|   thumbnail   |      `String`      |                썸네일 URL                |
|    content    |      `String`      |                  본문                   |

##### UserInfoDto

|     Name     |   Data Type    |               Description               | 
|:------------:|:--------------:|:---------------------------------------:|
|    userId    |    `String`    |         유저 아이디. DB의 `id_str` 값          |
|   nickName   |    `String`    |                  유저 별명                  |
| profileImage | `String\|null` | 프로필 이미지 URL. `null`이면 임의의 기본 이미지로 대체 필요 |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 상세 조회 성공",
  "code": "00",
  "data": {
    "postId": 1,
    "categoryId": 1,
    "categoryName": "Java",
    "title": "Java 너무 재미있어요.",
    "author": {
      "userId": "cheesecat47",
      "nickName": "신주용",
      "profileImage": null
    },
    "createdAt": "2024-01-17T10:57:15Z",
    "hit": 5,
    "excerpt": "...",
    "thumbnail": null,
    "content": null
  }
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "INVALID_REQUEST_PARAMETER",
  "code": "11",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

### createPost 글 작성

- 본인 블로그에 글 작성.

```http request
POST /api/post
```

#### 요청

| Param Type |      Name       | Data Type | Required |       Description       |
|:----------:|:---------------:|:---------:|:--------:|:-----------------------:|
|   Header   | `Authorization` | `String`  |    O     | 액세스 토큰. 본인 블로그 글 작성 가능  |
|    Body    |    `userId`     | `String`  |    O     | 유저 아이디. DB의 `id_str` 값  |
|    Body    | `categoryName`  | `String`  |    O     |         게시판 이름          |
|    Body    |     `title`     | `String`  |    O     |          글 제목           |
|    Body    |    `excerpt`    | `String`  |    -     | 글 요약. 최대 200자. 기본값 `""` |
|    Body    |    `content`    | `String`  |    -     |     글 본문. 최대 2000자      |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/post' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "categoryName": "Spring 스터디",
  "title": "Spring 공부 중",
  "content": "스프링 공부 중인데 재밌다. 이케이케 하면 서버가 실행된다."
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 작성 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "필수 파라미터를 확인하세요",
  "code": "10",
  "data": {
    "postTitle": null,
    "userId": "cheesecat47",
    "categoryName": "알고리즘 문제"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 생성은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

### updatePost 글 수정

- 글 수정.

```http request
PATCH /api/post/:postTitle
```

#### 요청

| Param Type |      Name       | Data Type | Required |       Description       |
|:----------:|:---------------:|:---------:|:--------:|:-----------------------:|
|   Header   | `Authorization` | `String`  |    O     | 액세스 토큰. 본인 블로그 글 수정 가능  |
|    Path    |   `postTitle`   | `String`  |    O     |        수정할 글 제목         |
|    Body    |    `userId`     | `String`  |    O     | 유저 아이디. DB의 `id_str` 값  |
|    Body    | `categoryName`  | `String`  |    -     |         게시판 이름          |
|    Body    |     `title`     | `String`  |    -     |          글 제목           |
|    Body    |    `excerpt`    | `String`  |    -     | 글 요약. 최대 200자. 기본값 `""` |
|    Body    |    `content`    | `String`  |    -     |     글 본문. 최대 2000자      |

##### 예시

```bash
curl -X 'PATCH' \
  'http://localhost:8080/api/post/Spring-공부-중' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "title": "Spring Boot 공부 중",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 수정 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 제목 형식에 맞지 않습니다",
  "code": "11",
  "data": {
    "categoryName": "!Spring!"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 수정은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

### deletePost 글 삭제

- 글 삭제.

```http request
DELETE /api/post/:postTitle
```

#### 요청

| Param Type |      Name       | Data Type | Required |      Description       |
|:----------:|:---------------:|:---------:|:--------:|:----------------------:|
|   Header   | `Authorization` | `String`  |    O     | 액세스 토큰. 본인 블로그 글 수정 가능 |
|    Path    |   `postTitle`   | `String`  |    O     |        삭제할 글 제목        |
|    Body    |    `userId`     | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 |

##### 예시

```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/post/Spring-공부-중' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 삭제 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "글 삭제는 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

## Comment

### getCommentsByPostId 특정 글에 달린 댓글 목록 조회

- 특정 글에 달린 댓글 목록 조회.

```http request
GET /api/post/:postId/comment
```

#### 요청

| Param Type |   Name   | Data Type | Required | Description |
|:----------:|:--------:|:---------:|:--------:|:-----------:|
|    Path    | `postId` |   `int`   |    O     |   게시글 아이디   |

#### 응답

##### 응답 본문

|   Name    | Data Type |    Description    | 
|:---------:|:---------:|:-----------------:|
| `message` | `String`  |      응답 메시지       |
|  `code`   | `String`  |       응답 코드       |
|  `data`   |   `Map`   | 응답 데이터 또는 오류 정보 맵 |

##### data(Map)

|     Name      |     Data Type      |              Description              | 
|:-------------:|:------------------:|:-------------------------------------:|
|   comments    | `List<CommentDto>` | 댓글 배열. 최근순 정렬. 댓글이 없으면 길이가 0인 배열 `[]` |
| numOfComments |       `int`        |       댓글 개수 (`comments` 배열 길이)        |

##### CommentDto

|   Name    | Data Type |         Description         | 
|:---------:|:---------:|:---------------------------:|
| commentId |   `int`   |           댓글 아이디            |
|  userId   | `String`  |         댓글 작성자 아이디          |
|  content  | `String`  |            댓글 내용            |
| createdAt | `String`  | 댓글 작성일. ISO 8601 형식. UTC 기준 |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 목록 조회 성공",
  "code": "00",
  "data": {
    "comments": [
      {
        "commentId": 2,
        "userId": "cheesecat47",
        "content": "그치? ㅋㅋㅋ",
        "createdAt": "2023-12-02T23:02:00Z"
      },
      {
        "commentId": 1,
        "userId": "rosielsh",
        "content": "짱 귀여워!",
        "createdAt": "2023-12-02T23:01:00Z"
      }
    ],
    "numOfComments": 2
  }
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "INVALID_REQUEST_PARAMETER",
  "code": "11",
  "data": {
    "userId": "cheesecat$&"
  }
}
```

### createComment 댓글 작성

- 특정 글에 댓글 작성.

```http request
POST /api/post/:postId/comment
```

#### 요청

| Param Type |      Name       | Data Type | Required |                    Description                    |
|:----------:|:---------------:|:---------:|:--------:|:-------------------------------------------------:|
|    Path    |    `postId`     |   `int`   |    O     |                       글 아이디                       |
|    Body    |    `userId`     | `String`  |    -     |              유저 아이디. DB의 `id_str` 값               |
|   Header   | `Authorization` | `String`  |    -     |         액세스 토큰. `userId`를 사용해 댓글 작성 시 필요          |
|    Body    |    `content`    | `String`  |    -     |                  댓글 본문. 최대 300자                   |
|    Body    |   `tmpUserId`   | `String`  |    -     | 비회원 댓글 시 유저 아이디. `userId` 또는 이 값 중 하나 필수. 둘 다는 불가 |
|    Body    |   `tmpUserPw`   | `String`  |    -     |       비회원 댓글 시 유저 비밀번호. `tmpUserId` 사용 시 필요       |

##### 예시

```bash
curl -X 'POST' \
  'http://localhost:8080/api/post/1/comment' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "content": "글 잘 보고 갑니다^^",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 생성 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "유저 아이디 형식에 맞지 않습니다",
  "code": "11",
  "data": {
    "tmpUserId": "cheesecat$@"
  }
}
```

### updateComment 댓글 수정

- 댓글 수정.

```http request
PATCH /api/post/:postId/comment/:commentId
```

#### 요청

| Param Type |      Name       | Data Type | Required |                          Description                           |
|:----------:|:---------------:|:---------:|:--------:|:--------------------------------------------------------------:|
|    Path    |    `postId`     |   `int`   |    O     |                             글 아이디                              |
|    Path    |   `commentId`   |   `int`   |    O     |                             댓글 아이디                             |
|    Body    |    `userId`     | `String`  |    -     |                     유저 아이디. DB의 `id_str` 값                     |
|   Header   | `Authorization` | `String`  |    -     |              액세스 토큰. `userId`를 사용해 작성한 댓글 수정 시 필요              |
|    Body    |    `content`    | `String`  |    -     |                      수정할 새 댓글 본문. 최대 300자                      |
|    Body    |   `tmpUserId`   | `String`  |    -     | 비회원으로 작성한 댓글 수정 시 필요한 유저 아이디. `userId` 또는 이 값 중 하나 필수. 둘 다는 불가 |
|    Body    |   `tmpUserPw`   | `String`  |    -     |            비회원 댓글 수정 시 유저 비밀번호. `tmpUserId` 사용 시 필요            |

##### 예시

```bash
curl -X 'PATCH' \
  'http://localhost:8080/api/post/1/comment/1' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
  "content": "글 잘 보고 갑니다!!",
}'
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 수정 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "파라미터 형식에 맞지 않습니다",
  "code": "11",
  "data": {
    "userId": "cheesecat$@"
  }
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 수정은 작성자만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

### deleteComment 댓글 삭제

- 댓글 삭제.

```http request
DELETE /api/post/:postId/comment/:commentId
```

#### 요청

| Param Type |      Name       | Data Type | Required |                          Description                           |
|:----------:|:---------------:|:---------:|:--------:|:--------------------------------------------------------------:|
|    Path    |    `postId`     |   `int`   |    O     |                             글 아이디                              |
|    Path    |   `commentId`   |   `int`   |    O     |                             댓글 아이디                             |
|    Body    |    `userId`     | `String`  |    -     |                     유저 아이디. DB의 `id_str` 값                     |
|   Header   | `Authorization` | `String`  |    -     |              액세스 토큰. `userId`를 사용해 작성한 댓글 삭제 시 필요              |
|    Body    |   `tmpUserId`   | `String`  |    -     | 비회원으로 작성한 댓글 삭제 시 필요한 유저 아이디. `userId` 또는 이 값 중 하나 필수. 둘 다는 불가 |
|    Body    |   `tmpUserPw`   | `String`  |    -     |            비회원 댓글 삭제 시 유저 비밀번호. `tmpUserId` 사용 시 필요            |

##### 예시

```bash
curl -X 'DELETE' \
  'http://localhost:8080/api/post/1/comment/1' \
  -H 'accept: application/json;charset=utf-8' \
  -H 'Authorization: Bearer eyJ0eXAiOi...' \
  -H 'Content-Type: application/json' \
  -d '{
  "userId": "cheesecat47",
}'
```


#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 삭제 성공",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "댓글 삭제는 작성자만 가능합니다",
  "code": "12",
  "data": {
    "Authorization": "Bearer eyJ0eXAiOi...",
    "userId": "rosielsh"
  }
}
```

---

## 에러 코드 정리

| HTTP 응답 코드 | 응답 코드 |            응답 메시지             |            설명             |
|:----------:|:-----:|:-----------------------------:|:-------------------------:|
|    200     |  00   |        NORMAL_SERVICE         |            정상             |
|    400     |  10   | NO_REQUIRED_REQUEST_PARAMETER |       필수 요청 파라미터 없음       |
|    400     |  11   |   INVALID_REQUEST_PARAMETER   |        파라미터 값이 잘못됨        |
|    401     |  12   |         UNAUTHORIZED          | 미인증 상태에서 요청 또는 유효하지 않은 토큰 |
|    404     |  13   |           NO_RESULT           |       결과 값이 존재하지 않음       |
|    404     |  14   |          NO_RESOURCE          |       URI가 존재하지 않음        |
|    500     |  20   |     INTERNAL_SERVER_ERROR     |         서버 내부 오류          |
|    500     |  21   |           SQL_ERROR           |           DB 오류           |

---

## References

- <https://wildeveloperetrain.tistory.com/240>
- <http://blog.storyg.co/rest-api-response-body-best-pratics>
- <https://www.data.go.kr/tcs/dss/selectApiDataDetailView.do?publicDataPk=15084084>
