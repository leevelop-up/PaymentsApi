package com.example.paymentsapi.config;

import com.example.paymentsapi.web.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .headers().frameOptions().sameOrigin()
                .and()
                .formLogin().disable()
                .csrf().disable() // 쿠키 기반이 아닌 JWT 기반이므로 사용하지 않음
                .cors().configurationSource(corsConfigurationSource()).and()
                .httpBasic().disable() // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .rememberMe().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// Spring Security 세션 정책 : 세션을 생성 및 사용하지 않음
                .and()
                .authorizeRequests()// 조건별로 요청 허용/제한 설정
                .antMatchers("/api/login", "/api/register","/home","/swagger-resources/**").permitAll() // 로그인 및 회원가입은 모든 사용자에게 허용
                .antMatchers("/api/manage/**").hasRole("ADMIN") // /api/manage는 ADMIN 권한 필요
                .antMatchers("/api/service/**").hasAnyRole("USER", "ADMIN") // /api/service로 시작하는 모든 URL은 USER 및 ADMIN 권한 필요
                //.anyRequest().authenticated() // 나머지 요청은 인증 필요
                .and()
                .exceptionHandling()    // 에러 핸들링
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler())
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 적용
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8080"));
        configuration.setAllowCredentials(true); // token 주고 받을 때,
        configuration.addExposedHeader("Authorization"); // token
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
