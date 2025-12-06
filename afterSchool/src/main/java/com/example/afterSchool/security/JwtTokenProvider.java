package com.example.afterSchool.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long validityInMilliseconds;

    // 생성자에서 프로퍼티 값을 가져와 키를 세팅
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey); // Base64로 인코딩된 키라면 decode 필요, 일반 문자열이면 getBytes()
        // 여기서는 그냥 문자열 바이트를 바로 쓴다고 가정 (간단 버전)
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validityInMilliseconds = 31536000000L; // 1년 (초간단 모드)
    }

    // 1. 토큰 생성
    public String createToken(String subject, String role) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setSubject(subject) // 보통 이메일이나 ID 저장
                .claim("role", role) // 사용자 권한 저장 (ROLE_STUDENT 등)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 2. 토큰에서 정보 추출 (이메일/ID)
    public String getSubject(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 3. 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}