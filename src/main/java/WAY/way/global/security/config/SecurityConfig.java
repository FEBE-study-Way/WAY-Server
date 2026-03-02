package WAY.way.global.security.config;

import WAY.way.global.security.filter.JwtAuthenticationFilter;
import WAY.way.global.security.handler.JwtAccessDeniedHandler;
import WAY.way.global.security.handler.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Spring Security 설정 클래스.
 * <p>
 * JWT 기반 Stateless 인증을 구성하고, 공개 URL 패턴과 역할별 접근 제어를 정의한다.
 * CORS 설정도 함께 포함한다.
 * </p>
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JwtAccessDeniedHandler accessDeniedHandler;

    /**
     * 인증 없이 접근 가능한 공개 URL 패턴 목록.
     */
    public static final String[] PUBLIC_URLS = {
            "/api/v1/auth/**",
            "/api/oauth2/**",
            "/login/oauth2/**",
            "/oauth2/**",
            "/error"
    };

    /**
     * Spring Security 필터 체인을 구성한다.
     *
     * @param http {@link HttpSecurity} 설정 객체
     * @return 구성된 {@link SecurityFilterChain}
     * @throws Exception 설정 중 오류 발생 시
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PUBLIC_URLS).permitAll()
                        .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/auth/signup").hasRole("UNAUTHENTICATED")
                        .requestMatchers("/api/v1/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    /**
     * CORS 설정 빈을 구성한다.
     * <p>
     * 모든 출처를 허용하며 자격 증명(쿠키 등)을 허용한다.
     * </p>
     *
     * @return 구성된 {@link CorsConfigurationSource}
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization", "Refresh-Token"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * 주어진 URI가 공개 URL 패턴에 해당하는지 확인한다.
     *
     * @param uri 확인할 요청 URI
     * @return 공개 URL이면 {@code true}
     */
    public static boolean isPublicUrl(String uri) {
        for (String pattern : PUBLIC_URLS) {
            if (new org.springframework.util.AntPathMatcher().match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }
}