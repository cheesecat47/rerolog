# API 명세서

* [API 명세서](#api-명세서)
  * [응답 공통 형식](#응답-공통-형식)
  * [User](#user)
    * [getUserInfo 유저 정보 조회](#getuserinfo-유저-정보-조회)
      * [요청](#요청)
      * [응답](#응답)
        * [응답 본문](#응답-본문)
        * [UserInfo](#userinfo)
        * [Contact](#contact)
        * [예시](#예시)
  * [Blog](#blog)
    * [getBlogInfo 블로그 정보 조회](#getbloginfo-블로그-정보-조회)
      * [요청](#요청-1)
      * [응답](#응답-1)
        * [응답 본문](#응답-본문-1)
        * [BlogInfo](#bloginfo)
        * [예시](#예시-1)
  * [Category](#category)
    * [getCategories 게시판 목록 조회](#getcategories-게시판-목록-조회)
      * [요청](#요청-2)
      * [응답](#응답-2)
        * [응답 본문](#응답-본문-2)
        * [예시](#예시-2)
  * [Post](#post)
    * [getRecentPosts 최근 글 목록 조회](#getrecentposts-최근-글-목록-조회)
      * [요청](#요청-3)
      * [응답](#응답-3)
        * [응답 본문](#응답-본문-3)
        * [Post](#post-1)
        * [예시](#예시-3)
    * [getPopularPosts 인기 글 목록 조회](#getpopularposts-인기-글-목록-조회)
      * [요청](#요청-4)
      * [응답](#응답-4)
        * [응답 본문](#응답-본문-4)
        * [Post](#post-2)
        * [예시](#예시-4)
  * [References](#references)

---

## 응답 공통 형식

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "성공",
  "errors": null,
  "data": {}
}
```

|   Name    |   Data Type   |                       Description                       | 
|:---------:|:-------------:|:-------------------------------------------------------:|
| `message` |   `String`    |                         응답 메시지                          |
| `status`  |   `String`    |                       HTTP 상태 메시지                       |
|  `data`   | `Object\|Map` | 요청 성공시 데이터 객체 포함.<br/>오류 발생 시 `Map` 타입으로 오류 관련 키-값 쌍 포함 |

---

## User

### getUserInfo 유저 정보 조회

- 유저 아이디를 사용해 해당 유저 정보 조회.

```http request
GET /api/user/{id}
```

#### 요청

| Param Type | Name | Data Type | Required |      Description       |  Validation   |
|:----------:|:----:|:---------:|:--------:|:----------------------:|:-------------:|
|    Path    | `id` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 | 존재하는 아이디인지 확인 |

#### 응답

##### 응답 본문

|   Name    |   Data Type   | Description | 
|:---------:|:-------------:|:-----------:|
| `message` |   `String`    |   응답 메시지    |
| `status`  |   `String`    | HTTP 상태 메시지 |
|  `data`   | `UserInfoDto` |  유저 정보 객체   |

##### UserInfo

|    Name     |  Data Type   |          Description          | 
|:-----------:|:------------:|:-----------------------------:|
|     id      |    String    |    유저 아이디. DB의 `id_str` 값     |
|    name     |    String    |              유저명              |
|   content   |    String    |       유저 소개. `null` 가능        |
| created_at  |    String    |  회원 가입일. ISO 8601 형식. UTC 기준  |
| profile_img |    String    |    프로필 이미지 URL. `null` 가능     |
|   contact   | ContactDto[] | 연락처 배열. 등록된 연락처가 없으면 길이 0인 배열 |

##### Contact

|    Name    | Data Type |                  Description                  | 
|:----------:|:---------:|:---------------------------------------------:|
|    type    |  String   | "Email", "GitHub", "LinkedIn", "WebSite" 중 하나 |
|   value    |  String   |                     연락처 값                     |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "유저 정보 조회 성공",
  "status": "OK",
  "data": {
    "id": "cheesecat47",
    "name": "신주용",
    "content": "안녕하세요, 신주용입니다.",
    "created_at": "2023-12-20T09:00:00Z",
    "profile_img": "...",
    "contact": [
      {
        "type": "email",
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
  "message": "유저 정보 조회 실패",
  "status": "BAD_REQUEST",
  "data": {
    "error_related_key": error_related_value,
    // 만약 해당 id가 없어서 못 불러온 경우는 "id": "파라미터로 입력한 id" 포함.
    ...
  }
}
``` 

---

## Blog

### getBlogInfo 블로그 정보 조회

- 블로그 주인 유저 아이디를 사용해 해당 유저의 블로그 정보 조회.

```http request
GET /api/blog/{id}
```

#### 요청

| Param Type | Name | Data Type | Required |      Description       |  Validation   |
|:----------:|:----:|:---------:|:--------:|:----------------------:|:-------------:|
|    Path    | `id` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 | 존재하는 아이디인지 확인 |

#### 응답

##### 응답 본문

|   Name    |   Data Type   | Description | 
|:---------:|:-------------:|:-----------:|
| `message` |   `String`    |   응답 메시지    |
| `status`  |   `String`    | HTTP 상태 메시지 |
|  `data`   | `BlogInfoDto` |  블로그 정보 객체  |

##### BlogInfo

|    Name    | Data Type |          Description          | 
|:----------:|:---------:|:-----------------------------:|
|     id     |  String   | 블로그 주인 유저 아이디. DB의 `id_str` 값 |
|    name    |  String   |            블로그 이름             |
| created_at |  String   | 블로그 개설일. ISO 8601 형식. UTC 기준  |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "블로그 정보 조회 성공",
  "status": "OK",
  "data": {
    "id": "string",
    "name": "string",
    "created_at": "string"
  }
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "블로그 정보 조회 실패",
  "status": "BAD_REQUEST",
  "data": {
    "error_related_key": error_related_value,
    ...
  }
}
``` 

## Category

### getCategories 게시판 목록 조회

- 블로그에 있는 게시판 목록 조회.

```http request
GET /api/blog/{id}/category
```

#### 요청

| Param Type | Name | Data Type | Required |      Description       |  Validation   |
|:----------:|:----:|:---------:|:--------:|:----------------------:|:-------------:|
|    Path    | `id` | `String`  |    O     | 유저 아이디. DB의 `id_str` 값 | 존재하는 아이디인지 확인 |

#### 응답

##### 응답 본문

|   Name    | Data Type  | Description | 
|:---------:|:----------:|:-----------:|
| `message` |  `String`  |   응답 메시지    |
| `status`  |  `String`  | HTTP 상태 메시지 |
|  `data`   | `String[]` |  게시판 이름 배열  |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 목록 조회 성공",
  "status": "OK",
  "data": [ 
    "Java", 
    ...
  ]
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "게시판 목록 조회 실패",
  "status": "BAD_REQUEST",
  "data": {
    "error_related_key": error_related_value,
    ...
  }
}
```

---

## Post

### getRecentPosts 최근 글 목록 조회

- 게시판에 관계 없이 최근에 작성된 글 N개 조회.

```http request
GET /api/post/recent?user_id=&n=4
```

#### 요청

| Param Type |   Name    | Data Type | Required |    Description     |  Validation   |
|:----------:|:---------:|:---------:|:--------:|:------------------:|:-------------:|
|   Query    | `user_id` | `string`  |    O     |       유저 아이디       | 존재하는 아이디인지 확인 |
|   Query    |    `n`    |   `int`   |    -     | N개의 최근 글 조회. 기본값 4 |   n은 1이상 정수   |

#### 응답

##### 응답 본문

|   Name    |    Data Type    |                           Description                            | 
|:---------:|:---------------:|:----------------------------------------------------------------:|
| `message` |    `string`     |                              응답 메시지                              |
| `errors`  |  `Exception[]`  |                           응답 공통 형식과 동일                           |
|  `data`   | `Post[] \| Map` | 최근 글 N개 시간 역순 배열. 최근 글이 없는 경우 길이가 0인 배열 반환. 오류 발생 시 응답 공통 형식과 동일 |

##### Post

|    Name     | Data Type |       Description        | 
|:-----------:|:---------:|:------------------------:|
|   post_id   |    int    |          글 아이디           |
| category_id |    int    |         게시판 아이디          |
|    title    |  string   |            제목            |
|   author    |    int    |         작성자 아이디          |
|  datetime   |  string   | 작성일. ISO 8601 형식. UTC 기준 |
|     hit     |    int    |           조회수            |
|   excerpt   |  string   |            요약            |
|  thumbnail  |  string   |         썸네일 URL          |

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "최근 글 목록 조회 성공",
  "errors": null,
  "data": [
    {
      "post_id": 4,
      "category_id": 1,
      "title": "내 아이디가 왜 cheesecat이냐면",
      "author": "cheesecat47",
      "datetime": "2023-12-02T23:00:00Z",
      "hit": 21,
      "excerpt": "2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.",
      "thumbnail": "..."
    },
    ...
  ]
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "최근 글 목록 조회 실패",
  "errors": [
    error1,
    ...
  ],
  "data": {
    "error_related_key": error_related_value,
    ...
  }
}
```

### getPopularPosts 인기 글 목록 조회

- 게시판에 관계 없이 조회수가 높은 글 N개 조회.

```http request
GET /api/post/popular?user_id=&n=4
```

#### 요청

| Param Type |   Name    | Data Type | Required |    Description     |  Validation   |
|:----------:|:---------:|:---------:|:--------:|:------------------:|:-------------:|
|   Query    | `user_id` | `string`  |    O     |       유저 아이디       | 존재하는 아이디인지 확인 |
|   Query    |    `n`    |   `int`   |    -     | N개의 최근 글 조회. 기본값 4 |   n은 1이상 정수   |

#### 응답

##### 응답 본문

|   Name    |    Data Type    |                                        Description                                        | 
|:---------:|:---------------:|:-----------------------------------------------------------------------------------------:|
| `message` |    `string`     |                                          응답 메시지                                           |
| `errors`  |  `Exception[]`  |                                       응답 공통 형식과 동일                                        |
|  `data`   | `Post[] \| Map` | 인기 글 N개 조회수 역순 배열. DB 조회 결과 중 조회수가 가장 많은 글의 조회수가 0인 경우 길이가 0인 배열 반환. 오류 발생 시 응답 공통 형식과 동일 |

##### Post

- `getRecentPosts`의 [Post](#post-1)와 동일.

##### 예시

```json
// HTTP/1.1 200 OK
// Content-Type: application/json;charset=UTF-8
{
  "message": "인기 글 목록 조회 성공",
  "errors": null,
  "data": [
    {
      "post_id": 4,
      "category_id": 1,
      "title": "내 아이디가 왜 cheesecat이냐면",
      "author": "cheesecat47",
      "datetime": "2023-12-02T23:00:00Z",
      "hit": 21,
      "excerpt": "2018년 10월에 동촌 유원지에 사진 찍으러 나갔다가 만난 아기 고양이. 진짜 예뻤다.",
      "thumbnail": "..."
    },
    ...
  ]
}
```

```json
// HTTP/1.1 400 BAD REQUEST
// Content-Type: application/json;charset=UTF-8
{
  "message": "인기 글 목록 조회 실패",
  "errors": [
    error1,
    ...
  ],
  "data": {
    "error_related_key": error_related_value,
    ...
  }
}
```

---

## References

- <https://wildeveloperetrain.tistory.com/240>
- <http://blog.storyg.co/rest-api-response-body-best-pratics>
