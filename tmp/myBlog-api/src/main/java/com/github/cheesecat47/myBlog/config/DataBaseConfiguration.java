package com.github.cheesecat47.myBlog.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * DB 연결 관련 설정. <br/>
 * Spring Boot JDBC는 HikariCP를 기본 Connection Pool으로 포함.
 *
 * @author cheesecat47
 * @see <a href="https://adjh54.tistory.com/73">adjh54. "[Java/Library] HikariCP 이해하고 적용하기 (with. MyBatis)"</a>
 */
@Configuration
@PropertySource("classpath:/application.properties")
@MapperScan(basePackages = {"com.github.cheesecat47.myBlog.*.model.mapper"})
@RequiredArgsConstructor
public class DataBaseConfiguration {

    private final ApplicationContext applicationContext;

    /**
     * application.properties 파일에서 설정한 hikari 설정값을 불러와 HikariConfig 객체 생성.
     *
     * @return 생성된 HikariConfig 객체.
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    /**
     * DataSource 객체 생성.
     *
     * @return 생성된 DataSource 객체.
     */
    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    /**
     * DataSource 객체를 사용해 SqlSessionFactory 생성.
     *
     * @return SqlSessionFactory의 instance.
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        // 인스턴스 생성
        SqlSessionFactoryBean session = new SqlSessionFactoryBean();
        // DataSource 등록
        session.setDataSource(dataSource());
        session.setMapperLocations(applicationContext.getResources("classpath:mapper/**/*.xml"));
        session.setTypeAliasesPackage("com.github.cheesecat47.myBlog.*.model");

        return session.getObject();
    }

    /**
     * MyBatis Spring 연동 모듈의 핵심. SqlSession을 구현하고 코드에서 SqlSession을 대체하는 역할. SqlSessionTemplate은 Thread-safe하고, Mapper에서 공유 가능.
     *
     * @return
     * @see <a href="https://mybatis.org/spring/ko/sqlsession.html">MyBatis Docs. "SqlSession 사용"</a>
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
    }
}
