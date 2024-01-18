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
  * [Blog](#blog)
    * [getBlogInfo 블로그 정보 조회](#getbloginfo-블로그-정보-조회)
  * [Category](#category)
    * [getCategories 게시판 목록 조회](#getcategories-게시판-목록-조회)
    * [createCategory 게시판 생성](#createcategory-게시판-생성)
    * [updateCategory 게시판 정보 변경](#updatecategory-게시판-정보-변경)
    * [deleteCategory 게시판 삭제](#deletecategory-게시판-삭제)
  * [Post](#post)
    * [getPosts 글 목록 조회](#getposts-글-목록-조회)
    * [getPostById 글 상세 조회](#getpostbyid-글-상세-조회)
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

|     Name     |   Data Type    |               Description               | 
|:------------:|:--------------:|:---------------------------------------:|
|    userId    |    `String`    |         유저 아이디. DB의 `id_str` 값          |
|   nickName   |    `String`    |                  유저 별명                  |
|   content    |    `String`    |   유저 소개. 소개 멘트 부재 시 길이 0인 문자열 `""` 반환   |
|  createdAt   |    `String`    |       회원 가입일. ISO 8601 형식. UTC 기준       |
| profileImage | `String\|null` | 프로필 이미지 URL. `null`이면 임의의 기본 이미지로 대체 필요 |
|   contacts   | `ContactDto[]` |   연락처 배열. 등록된 연락처가 없으면 길이 0인 배열 `[]`    |

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

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
```

### updateUserInfo 유저 정보 변경

- 유저 아이디를 사용해 해당 유저 정보 변경.

```http request
PUT /api/user/:userId
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
```

### deleteUserInfo 회원 탈퇴

- 유저 아이디를 사용해 해당 유저 회원 탈퇴.

```http request
DELETE /api/user/:userId
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
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
    "userId": "cheesecat4"
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

- 블로그 주인 유저 아이디를 사용해 해당 유저의 블로그 정보 조회.

```http request
GET /api/blog/:userId
```

#### 요청

| Param Type |   Name   | Data Type | Required |      Description       | 
|:----------:|:--------:|:---------:|:--------:|:----------------------:|
|    Path    | `userId` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 |

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
|  userId   | `String`  |    블로그 주인 유저 아이디. DB의 `id_str` 값     |
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
    "userId": "cheesecat47",
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
    "userId": "cheesecat$&"
  }
}
``` 

## Category

### getCategories 게시판 목록 조회

- 블로그에 있는 게시판 목록 조회.

```http request
GET /api/blog/:userId/category
```

#### 요청

| Param Type |   Name   | Data Type | Required |      Description       | 
|:----------:|:--------:|:---------:|:--------:|:----------------------:|
|    Path    | `userId` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 | 

#### 응답

##### 응답 본문

|   Name    |      Data Type       |     Description      | 
|:---------:|:--------------------:|:--------------------:|
| `message` |       `String`       |        응답 메시지        |
|  `code`   |       `String`       |        응답 코드         |
|  `data`   | `CategoryDto[]\|Map` | 게시판 정보 배열 또는 오류 정보 맵 |

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
    "userId": "cheesecat$&"
  }
}
```

### createCategory 게시판 생성

- 게시판 생성.

```http request
POST /api/blog/:userId/category
```

#### 요청

| Param Type |      Name       | Data Type | Required |                         Description                         | 
|:----------:|:---------------:|:---------:|:--------:|:-----------------------------------------------------------:|
|    Path    |    `userId`     | `String`  |    O     |                   유저 아이디. DB의 `id_str` 값                    |
|   Header   | `Authorization` | `String`  |    O     | 액세스 토큰. 로그인 유저와 블로그 유저가 동일할 때만 (본인 블로그 수정 시도일 때만) 게시판 생성 가능 |
|    Body    | `categoryName`  | `String`  |    O     |                           게시판 이름                            |

#### 응답

##### 예시

```json
// HTTP/1.1 201 CREATED
// Content-Type: application/json;charset=UTF-8
{
  "message": "NORMAL_SERVICE",
  "code": "00",
  "data": null
}
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 생성은 로그인 상태의 블로그 주인 유저만 가능합니다",
  "code": "12",
  "data": {
    ...
  }
}
```

### updateCategory 게시판 정보 변경

- 게시판 정보 변경.

```http request
PUT /api/blog/:userId/category/:categoryId
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
```

### deleteCategory 게시판 삭제

- 게시판 삭제

```http request
DELETE /api/blog/:userId/category/:categoryId
```

#### 응답

##### 예시

```json
// HTTP/1.1 204 NO CONTENT
// Content-Type: application/json;charset=UTF-8
```

```json
// HTTP/1.1 401 UNAUTHORIZED
// Content-Type: application/json;charset=UTF-8
```

---

## Post

### getPosts 글 목록 조회

- 조건에 일치하는 글 목록 조회

```http request
GET /api/post?userId=&categoryId=&order=recent&offset=0&limit=10
```

#### 요청

| Param Type |     Name     | Data Type | Required |                           Description                            |  
|:----------:|:------------:|:---------:|:--------:|:----------------------------------------------------------------:|
|   Query    |   `userId`   | `String`  |    -     |                              유저 아이디                              |
|   Query    | `categoryId` |   `int`   |    -     |          게시판 아이디. 특정 게시판에 속한 글만 필터링 할 때 사용. 없으면 전체 글 목록          |
|   Query    |   `order`    | `String`  |    -     | `latest(최신순)`,`oldest(오래된순)`,`popular(인기순)` 중 택 1. 기본값은 `latest` |
|   Query    |   `offset`   |   `int`   |    -     |                  정렬된 결과 중 `offset`부터 반환. 기본값 0                   |
|   Query    |   `limit`    |   `int`   |    -     |                  `offset`부터 `limit`개 조회. 기본값 10                  |

#### 응답

##### 응답 본문

|   Name    |    Data Type     |                          Description                           | 
|:---------:|:----------------:|:--------------------------------------------------------------:|
| `message` |     `String`     |                             응답 메시지                             |
|  `code`   |     `String`     |                             응답 코드                              |
|  `data`   | `PostDto[]\|Map` | 조건에 일치하는 글 목록 또는 해당하는 글이 없는 경우 길이가 0인 배열 `[]`. 오류 발생 시 오류 정보 맵 |

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
  "message": "NORMAL_SERVICE",
  "code": "00",
  "data": [
    {
      "postId": 4,
      "categoryId": 1,
      "categoryName": "Java",
      "title": "내 아이디가 왜 cheesecat이냐면",
      "author": {
        "userId": "cheesecat47",
        "nickName": "신주용",
        "profileImage": null
      },
      "createdAt": "2023-12-02T23:00:00Z",
      "hit": 21,
      "excerpt": "2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.",
      "thumbnail": "...",
      "numOfComments": 2
    },
    ...
  ]
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

### getPostById 글 상세 조회

- 글 상세 조회. 이 글과 연결된 댓글 포함.

```http request
GET /api/post/:userId/:postId
```

#### 요청

| Param Type |   Name   | Data Type | Required | Description |
|:----------:|:--------:|:---------:|:--------:|:-----------:|
|    Path    | `userId` | `String`  |    O     |   유저 아이디    |
|    Path    | `postId` |   `int`   |    O     |   게시글 아이디   |

#### 응답

##### 응답 본문

|   Name    |    Data Type    |     Description      | 
|:---------:|:---------------:|:--------------------:|
| `message` |    `String`     |        응답 메시지        |
|  `code`   |    `String`     |        응답 코드         |
|  `data`   | `PostDto\| Map` | 게시글 정보 객체 또는 오류 정보 맵 |

##### PostDto

|     Name      |   Data Type    |              Description              | 
|:-------------:|:--------------:|:-------------------------------------:|
|    postId     |     `int`      |                 글 아이디                 |
|  categoryId   |     `int`      |                게시판 아이디                |
| categoryName  |    `String`    |                게시판 이름                 |
|     title     |    `String`    |                  제목                   |
|    author     | `UserInfoDto`  |                작성자 정보                 |
|   createdAt   |    `String`    |       작성일. ISO 8601 형식. UTC 기준        |
|      hit      |     `int`      |                  조회수                  |
|    excerpt    |    `String`    |                  요약                   |
|   thumbnail   |    `String`    |                썸네일 URL                |
|    content    |    `String`    |                  본문                   |
|   comments    | `CommentDto[]` | 댓글 배열. 최근순 정렬. 댓글이 없으면 길이가 0인 배열 `[]` |
| numOfComments |     `int`      |       댓글 개수 (`comments` 배열 길이)        |

##### UserInfoDto

|     Name     |   Data Type    |               Description               | 
|:------------:|:--------------:|:---------------------------------------:|
|    userId    |    `String`    |         유저 아이디. DB의 `id_str` 값          |
|   nickName   |    `String`    |                  유저 별명                  |
| profileImage | `String\|null` | 프로필 이미지 URL. `null`이면 임의의 기본 이미지로 대체 필요 |

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
  "message": "인기 글 목록 조회 성공",
  "errors": null,
  "data": {
    "postId": 4,
    "categoryId": 1,
    "categoryName": "Java",
    "title": "내 아이디가 왜 cheesecat이냐면",
    "author": {
      "userId": "cheesecat47",
      "nickName": "신주용",
      "profileImage": null
    },
    "createdAt": "2023-12-02T23:00:00Z",
    "hit": 21,
    "excerpt": "2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.",
    "thumbnail": "...",
    "content": "2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다. 사람을 경계는 하면서도 멀리 도망가지는 않고 웅크리고 앉아서 지켜보는데 얼마나 귀엽던지.",
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
