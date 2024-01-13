package com.github.cheesecat47.myBlog.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class JWTUtil {

    @Value("${jwt.salt}")
    private String salt;

    @Value("${jwt.access-token.expiretime}")
    private long accessTokenExpireTime;

    @Value("${jwt.refresh-token.expiretime}")
    private long refreshTokenExpireTime;

    /**
     * Access Token 생성
     *
     * @param userId 유저 아이디
     * @return Access Token
     */
    public String createAccessToken(String userId) {
        return create(userId, "access-token", accessTokenExpireTime);
    }

    /**
     * Refresh Token 생성
     *
     * @param userId 유저 아이디
     * @return Refresh Token
     */
    public String createRefreshToken(String userId) {
        return create(userId, "refresh-token", refreshTokenExpireTime);
    }

    /**
     * 입력 받은 정보를 사용하여 토큰 발급
     *
     * @param userId     유저 아이디
     * @param subject    토큰 제목: access-token 또는 refresh-token
     * @param expireTime 유효 기간. 현재 시각 + 유효 기간 => 토큰 만료 시각
     * @return jwt string
     * @see <a href="https://github.com/jwtk/jjwt">https://github.com/jwtk/jjwt</a>
     */
    private String create(String userId, String subject, long expireTime) {
        // 토큰 생성
        String jwt = Jwts.builder()
                .header()
                .type("JWT")
                .and()
                .issuer("cheesecat47@gmail.com") // 발행인
                .subject(subject) // 제목: access-token 또는 refresh-token
                .expiration(new Date(System.currentTimeMillis() + expireTime)) // 만료 시각
                .issuedAt(new Date()) // 발행일
                .claim("userId", userId)
                .signWith(this.generateKey(), Jwts.SIG.HS256) //인코딩
                .compact(); // 직렬화
        log.debug("create: jwt: {}", jwt);

        return jwt;
    }

    private SecretKey generateKey() {
        return Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 토큰이 제대로 생성된 것인지 검증
     *
     * @param token 요청에 포함된 토큰
     * @param userId 현재 로그인 한 유저 아이디
     * @return 유효성 T/F
     */
    public boolean checkToken(String token, String userId) {
        try {
            token = token.replaceAll("^Bearer ", "");
            log.debug("checkToken: token: {}", token);
            log.debug("checkToken: userId: {}", userId);

            Jwts.parser()
                    .verifyWith(generateKey())
                    .require("userId", userId)
                    .build()
                    .parse(token);

            log.info("checkToken: valid token");
            return true;
        } catch (JwtException e) {
            log.error("checkToken: exception: {}", e.getMessage());
            return false;
        }
    }
}
