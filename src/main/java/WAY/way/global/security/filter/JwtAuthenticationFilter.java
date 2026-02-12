package WAY.way.global.security.filter;

import WAY.way.global.auth.MemberDetails;
import WAY.way.global.auth.MemberDetailsService;
import WAY.way.global.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final AntPathMatcher matcher = new AntPathMatcher();
    private final MemberDetailsService memberDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = jwtProvider.resolveToken(request);
            if (StringUtils.hasText(token) && jwtProvider.validateToken(token)) {

                if (jwtProvider.isAccessTokenValid(token)) {

                    Authentication authentication = createAuthentication(token);

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (JwtException e) {
            log.error("JWT 검증 실패", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        } catch (Exception e) {
            log.error("예상하지 못한 오류", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(String token) {
        String email = jwtProvider.getUserEmail(token);
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities());
    }


    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String uri = request.getRequestURI();

        return request.getMethod().equalsIgnoreCase("OPTIONS")
                || matcher.match("/api/v1/auth/**", uri)
                || matcher.match("/api/oauth2/**", uri)
                || matcher.match("/login/oauth2/**", uri)
                || matcher.match("/oauth2/**", uri)
                || matcher.match("/error", uri);
    }
}