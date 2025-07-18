package com.spring.Medicin_project.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    // قائمة بمسارات الواجهة والتوثيق فقط
    private static final String[] SWAGGER_WHITE_LIST = {
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    @Bean
    @Order(1) // هذه السلسلة للـ API الرئيسي وسيتم تقييمها ثانيًا
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**") // تطبيق هذه السلسلة فقط على المسارات التي تبدأ بـ /api
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // السماح بمسارات المصادقة
                        .requestMatchers("/api/auth/**").permitAll()
                        // تأمين المسارات الأخرى
                        .requestMatchers(HttpMethod.POST, "/api/doctors/**/availability").hasAuthority("ROLE_DOCTOR")
                        .requestMatchers(HttpMethod.POST, "/api/appointments/book").hasAuthority("ROLE_PATIENT")
                        // أي طلب آخر داخل /api/** يتطلب مصادقة
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(0) // هذه السلسلة للتوثيق وسيتم تقييمها أولاً
    public SecurityFilterChain swaggerSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher(SWAGGER_WHITE_LIST) // تطبيق هذه السلسلة فقط على مسارات القائمة البيضاء
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()) // السماح بكل الطلبات المطابقة
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
