# ERD

## 2023.12.09.

![ERD](resources/ERD-231209.png)

### 변경 내역

- Footer에서 이메일, 깃허브, 링크드인 같은 연락처 정보를 보여주려 했는데, 이전 버전의 User 테이블에는 해당 정보를 담는 컬럼이 없음.
    - 참고: [화면 설계서](https://github.com/cheesecat47/myBlog/blob/main/docs/pages.md)
- `Contact` 테이블 추가
    - `user_id`를 FK로 가짐.
    - name은 연락 방법. `email`, `github`, `linkedin`, `website` 등.
    - value는 웹사이트 URL이 들어올 수 있음. 크롬 기준 32779자까지도 입력 가능하다고 하나, 권장은 2천 자 이하라고 함.
        - https://stackoverflow.com/questions/417142/what-is-the-maximum-length-of-a-url-in-different-browsers
- `Category.name` 2000자 -> 20자로 변경.
    - 카테고리명은 너무 길게 잡을 필요가 없음.

## 2023.12.04.

![ERD](resources/ERD-231206.png)

### 변경 내역

- 테이블 설계 초기 버전
