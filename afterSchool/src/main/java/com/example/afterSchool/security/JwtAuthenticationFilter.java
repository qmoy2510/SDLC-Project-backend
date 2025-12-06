package com.example.afterSchool.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 1. 헤더에서 토큰 추출
        String token = resolveToken(request);

        // 2. 토큰 유효성 검사
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            // 3. 토큰에서 이메일 추출
            String email = tokenProvider.getSubject(token);

            // 4. (중요) SecurityContext에 저장할 Authentication 객체 생성
            // 원래는 DB에서 UserDetails를 가져와야 하지만, "초간단 모드"이므로 토큰 정보만으로 객체 생성
            // 권한은 비워두거나 토큰에서 꺼내서 넣으면 됨 (여기선 빈 리스트 처리)
            UserDetails principal = User.builder()
                    .username(email)
                    .password("") // 패스워드는 필요 없음
                    .authorities(Collections.emptyList()) // 필요하면 role 파싱해서 넣기
                    .build();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // 5. 시큐리티 컨텍스트에 등록 (이제 이 요청은 로그인 된 것으로 간주)
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}