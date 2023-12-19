# API 명세서

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
| `errors`  | `Exception[]` |      오류 발생 시 발생한 오류 모두를 배열로 포함.<br/>처리 성공 시 `null`      |
|  `data`   | `Object\|Map` | 요청 성공시 데이터 객체 포함.<br/>오류 발생 시 `Map` 타입으로 오류 관련 키-값 쌍 포함 |

## Blog

### 블로그 정보 얻기

- 블로그 주인 유저 아이디를 사용해 해당 유저의 블로그 정보를 얻어옴.

```http request
GET /api/blog/{user_id}
```

#### 요청

| Param Type |   Name    | Data Type | Required | Description |
|:----------:|:---------:|:---------:|:--------:|:-----------:|
|    Path    | `user_id` | `string`  |    O     |   유저 아이디    |

#### 응답

- 응답 본문

  |   Name    |     Data Type     |            Description             | 
  |:---------:|:-----------------:|:----------------------------------:|
  | `message` |     `string`      |               응답 메시지               |
  | `errors`  |   `Exception[]`   |            응답 공통 형식과 동일            |
  |  `data`   | `BlogInfo \| Map` | 블로그 메타 정보 객체. 오류 발생 시 응답 공통 형식과 동일 |

- BlogInfo

  |    Name    | Data Type |          Description          | 
  |:----------:|:---------:|:-----------------------------:|
  |  user_id   |  string   |  블로그 주인 유저 아이디. `id_str` 값.   |
  |    name    |  string   |            블로그 이름             |
  | created_at |  string   | 블로그 개설일. ISO 8601 형식. UTC 기준. |

- 예시

  ```json
  // HTTP/1.1 200 OK
  // Content-Type: application/json;charset=UTF-8
  {
    "message": "블로그 정보 조회 성공",
    "errors": null,
    "data": {
      "user_id": "string",
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
    "errors": [
      error1,
      ...
    ],
    "data": {
      "error_related_key": error_related_value
      // 만약 해당하는 user_id가 없어서 못 불러온 경우는 "user_id": "cheesecat47" 이 들어올 것.
    }
  }
  ``` 







## References

- <https://wildeveloperetrain.tistory.com/240>
- <http://blog.storyg.co/rest-api-response-body-best-pratics>
