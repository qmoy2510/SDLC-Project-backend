package com.example.afterSchool.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        // SecurityScheme 이름 (임의 지정)
        String jwtSchemeName = "JWT Authentication";

        // API 요청 헤더에 "Authorization: Bearer {토큰}"을 넣겠다는 설정
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);

        Components components = new Components()
                .addSecuritySchemes(jwtSchemeName, new SecurityScheme()
                        .name(jwtSchemeName)
                        .type(SecurityScheme.Type.HTTP) // HTTP 방식
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(new Info()
                        .title("방과후 수업 관리 API")
                        .description("방과후 수업 수강신청 및 관리 시스템 API 명세서")
                        .version("1.0.0"))
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}