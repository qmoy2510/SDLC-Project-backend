package com.example.afterSchool.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenResponse {
    private String accessToken; // [Simple JWT] 긴 유효기간 토큰 하나만 반환
}