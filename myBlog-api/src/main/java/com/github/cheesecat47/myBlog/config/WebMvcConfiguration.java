package com.github.cheesecat47.myBlog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * WebMvc 관련 설정.
 *
 * @author cheesecat47
 */
@Configuration
@EnableWebMvc
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {

    /**
     * CORS 허용 설정. 허용할 Origin을 CORS Registry에 추가.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        List<String> origins = List.of("http://localhost", "http://localhost:3000", "http://localhost:5173");
        log.info("CORS 허용 origins: {}", origins);

        registry.addMapping("/**") // 모든 하위 경로
                .allowedOrigins(String.join(",", origins)) // 허용할 Origin
                .allowedMethods("*") // 모든 메서드 허용
                .maxAge(1800); // 1800초동안 preflight 결과를 캐시에 저장.
    }
}
