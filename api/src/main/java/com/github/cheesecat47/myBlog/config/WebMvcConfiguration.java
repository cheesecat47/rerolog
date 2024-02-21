package com.github.cheesecat47.myBlog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${allowed-origins}")
    private String ALLOWED_ORIGINS;

    /**
     * CORS 허용 설정. 허용할 Origin을 CORS Registry에 추가.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("ALLOWED_ORIGINS: {}", ALLOWED_ORIGINS);

        registry.addMapping("/**") // 모든 하위 경로
                .allowedOrigins(ALLOWED_ORIGINS) // 허용할 Origin
                .allowedMethods("*") // 모든 메서드 허용
                .maxAge(1800); // 1800초동안 preflight 결과를 캐시에 저장.
    }
}
