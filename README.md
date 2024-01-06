# myBlog

> 블로그 서비스를 만들어보는 프로젝트입니다.

![main page](docs/resources/pages-main.gif)

## Tech Stack

- FE: Vue.js
- API: Spring Boot, MyBatis, MySQL
- Deploy: NGINX, Docker, Docker Compose

## Run

- 프로젝트 클론 후 디렉토리로 이동

    ```bash
    git clone https://github.com/cheesecat47/myBlog.git
    cd myBlog
    ```

- `.env` 파일의 환경 변수 수정

    ```bash
    cp .env.template .env
    ```

### Docker

```bash
docker compose up -d --build && docker compose logs -f --tail=1000
```

### Local

#### Database

- Prerequisites
  - MySQL 8.0.34

1. `myBlog-api/resources/sql` 디렉토리로 이동

    ```bash
   cd myBlog-api/resources/sql
    ```

2. MySQL Workbench, CLI 등에서 `01.schema.sql`, `02.data.sql` 순서대로 실행.

#### API server

- Prerequisites
  - Java 17
  - Maven 3.9

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

#### FE Dev server

- Prerequisites
  - Node v18

1. `myBlog-frontend` 디렉토리로 이동

    ```bash
    cd myBlog-frontend/
    npm install
    npm run dev
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
