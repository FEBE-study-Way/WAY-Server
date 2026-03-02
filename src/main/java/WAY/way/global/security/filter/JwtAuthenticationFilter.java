package WAY.way.global.security.filter;

import WAY.way.global.auth.MemberDetails;
import WAY.way.global.auth.MemberDetailsService;
import WAY.way.global.jwt.JwtProvider;
import WAY.way.global.security.config.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT 기반 인증 필터.
 * <p>
 * 요청당 한 번 실행되며({@link OncePerRequestFilter}), Authorization 헤더에서 JWT를 추출·검증하여
 * Spring Security 컨텍스트에 인증 정보를 설정한다.
 * 공개 URL 및 OPTIONS 요청은 필터를 건너뛴다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final MemberDetailsService memberDetailsService;
    private final ObjectMapper objectMapper;

    /**
     * JWT를 추출·검증하고 인증 컨텍스트에 설정한다.
     *
     * @param request     HTTP 요청
     * @param response    HTTP 응답
     * @param filterChain 필터 체인
     * @throws ServletException 서블릿 예외
     * @throws IOException      입출력 예외
     */
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
            log.error("만료된 토큰: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰입니다.");
            return;
        } catch (JwtException e) {
            log.error("JWT 검증 실패: {}", e.getMessage());
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
            return;
        } catch (Exception e) {
            log.error("예상하지 못한 오류: {}", e.getMessage(), e);
            sendErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "인증 처리 중 오류가 발생했습니다.");
            return;
        }
        filterChain.doFilter(request, response);
    }

    /**
     * JWT에서 이메일을 추출하여 {@link Authentication} 객체를 생성한다.
     *
     * @param token 검증된 Access Token
     * @return Spring Security 인증 토큰
     */
    private Authentication createAuthentication(String token) {
        String email = jwtProvider.getUserEmail(token);
        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(email);

        return new UsernamePasswordAuthenticationToken(
                memberDetails,
                null,
                memberDetails.getAuthorities());
    }

    /**
     * OPTIONS 요청이거나 공개 URL이면 필터를 건너뛴다.
     *
     * @param request HTTP 요청
     * @return 필터를 건너뛸 경우 {@code true}
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return request.getMethod().equalsIgnoreCase("OPTIONS") ||
                SecurityConfig.isPublicUrl(uri);
    }

    /**
     * 인증 오류 응답을 JSON 형태로 전송한다.
     *
     * @param response HTTP 응답
     * @param status   HTTP 상태 코드
     * @param message  에러 메시지
     * @throws IOException 응답 작성 중 입출력 예외
     */
    private void sendErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("message", message);

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}