// JWT 관련 유틸리티 클래스: 토큰 생성, 검증, 정보 추출 등을 담당
package com.training.mealplanner;

import io.jsonwebtoken.Claims;  // JWT의 페이로드(claims) 정보를 다룰 때 사용
import io.jsonwebtoken.JwtException; // JWT 예외 처리용
import io.jsonwebtoken.Jwts; // JWT 토큰을 만들거나 파싱할 때 사용하는 클래스
import io.jsonwebtoken.SignatureAlgorithm; // 어떤 알고리즘으로 서명할지 설정
import io.jsonwebtoken.security.Keys; // 암호화 키를 만들기 위한 도구
import org.springframework.stereotype.Component;

import java.security.Key; // JWT 서명용 키 (비밀키)
import java.util.Date;

@Component
public class JwtUtil {

    // 비밀 키 문자열 (반드시 길이가 충분히 길어야 함 - 최소 256bit)
    private final String secret = "vnrrhrwhk7wncorqkrkwl11ekfehdanfdnjs8dnvkfnvk11";

    // 토큰 유효 기간 (밀리초 단위) = 1일 (24 * 60 * 60 * 1000)
    private final long expirationMs = 1000 * 60 * 60 * 24;

    //  비밀 키 생성 (HS256 알고리즘에 맞게 byte[]로 변환하여 Key 객체 생성)
    private final Key key = Keys.hmacShaKeyFor(secret.getBytes());

    // [토큰 생성 메서드]
    public String generateToken(String username) {
        return Jwts.builder() // JWT 생성 빌더 시작
                .setSubject(username) // 토큰의 주제(subject)에 username을 설정
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs)) // 토큰 만료 시간
                .signWith(key, SignatureAlgorithm.HS256) // 비밀키로 서명 (HS256 알고리즘 사용)
                .compact(); // 토큰을 문자열로 생성해서 반환
    }

    // [토큰 검증 메서드]
    public boolean validateToken(String token) {
        try {
            // 토큰을 검증하려고 파싱 → 유효하지 않으면 예외 발생
            Jwts.parserBuilder()
                    .setSigningKey(key) // 비밀키 설정
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false; // 예외 발생 시 유효하지 않은 토큰
        }
    }

    // [토큰에서 username(주제) 추출]
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key) // 비밀키 설정
                .build()
                .parseClaimsJws(token) // 유효한 토큰을 파싱해서 claims(정보) 추출
                .getBody(); // JWT 페이로드 반환
        return claims.getSubject(); // 페이로드의 subject가 username
    }
}
