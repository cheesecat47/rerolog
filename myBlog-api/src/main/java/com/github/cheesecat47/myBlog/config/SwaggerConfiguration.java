package com.github.cheesecat47.myBlog.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Swagger 설정
 *
 * @author cheesecat47
 * @see <a href="https://velog.io/@u-nij/Spring-Boot-Swagger-3.0-설정-JWT-인증-설정-Profile로-환경별-설정">Spring Boot + Swagger 3.0 설정 + JWT 인증 설정 + @Profile로 환경별 설정</a>
 * @see <a href="https://velog.io/@dirn0568/Swagger-Jwt-인증access-refresh">Swagger Jwt 인증(access, refresh)</a>
 * @see <a href="https://springdoc.org/#migrating-from-springfox">Migrating from SpringFox</a>
 */
@Configuration
public class SwaggerConfiguration {
    // Swagger-UI 3.x http://localhost:8080/{api-root}/swagger-ui/index.html

    /**
     * 모든 API를 보여주는 페이지
     */
    @Bean
    public GroupedOpenApi allApis() {
        return GroupedOpenApi.builder()
                .group("All APIs")
                .pathsToMatch("/**")
                .build();
    }

    /**
     * 유저 관련 API만 보여주는 페이지
     */
    @Bean
    public GroupedOpenApi userApis() {
        return GroupedOpenApi.builder()
                .group("User APIs")
                .pathsToMatch("/user/**")
                .build();
    }

    /**
     * 블로그 관련 API만 보여주는 페이지
     */
    @Bean
    public GroupedOpenApi blogApis() {
        return GroupedOpenApi.builder()
                .group("Blog APIs")
                .pathsToMatch("/blog/**")
                .build();
    }

    /**
     * Swagger Web UI에서 보여줄 정보
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("MyBlog API Docs")
                        .description("<h3>MyBlog 프로젝트 API입니다.<h3>")
                        .version("1.0")
                        .contact(new Contact()
                                .name("cheesecat47")
                                .email("cheesecat47@gmail.com")
                                .url("https://github.com/cheesecat47")
                        )
                );
    }
}
