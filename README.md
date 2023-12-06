# myBlog

> 블로그 서비스를 만들어보는 프로젝트입니다.

## Tech Stack

- FE: Vue.js
- API: Spring Boot, MyBatis, MySQL

## Run Locally

- 프로젝트 클론 후 디렉토리로 이동

    ```bash
    git clone https://github.com/cheesecat47/myBlog.git
    cd myBlog
    ```

### FE Dev server

- 실행 전 아래 요구사항이 설치되어 있는지 확인이 필요합니다.
- Node v18

1. `myBlog-frontend` 디렉토리로 이동

    ```bash
    cd myBlog-frontend/
    npm install
    npm run dev
    ```

### API server

- 실행 전 아래 요구사항이 설치되어 있는지 확인이 필요합니다.
- Java 17
- Maven 3.9
- MySQL 8.0.34

1. `myBlog-api` 디렉토리로 이동

    ```bash
    cd myBlog-api/
    ```

2. 실행

    ```bash
    # Maven 패키징
    ./mvnw package

    # 실행
    java -jar target/myBlog-0.0.1-SNAPSHOT.jar
    ```

## Documentations

- [기능 명세서](docs/feature-spec.md)
- [ERD](docs/ERD.md)
- [화면 설계서](docs/pages.md)

## Author

- [@cheesecat47](https://github.com/cheesecat47)

## Contributors

- [@yeaaaaahhhhh](https://github.com/yeaaaaahhhhh)
- [@HeewonYoun](https://github.com/HeewonYoun)
- [@rosielsh](https://github.com/rosielsh)
