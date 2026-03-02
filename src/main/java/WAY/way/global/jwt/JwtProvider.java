package WAY.way.global.jwt;

import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.member.presentation.data.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * JWT 생성·파싱·검증을 담당하는 컴포넌트.
 * <p>
 * HS256 알고리즘으로 서명된 Access Token과 Refresh Token을 발급하고,
 * 토큰에서 클레임(userId, email, role, type)을 추출한다.
 * </p>
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    private final JwtProperties jwtProperties;
    private SecretKey secretKey;

    private static final String TOKEN_TYPE = "type";
    private static final String ACCESS_TOKEN = "accessToken";
    private static final String REFRESH_TOKEN = "refreshToken";
    private static final String USER_ID = "userId";
    private static final String ROLE = "role";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * 비밀 키를 초기화한다. 빈 생성 직후 자동으로 호출된다.
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Access Token과 Refresh Token을 함께 발급하여 {@link TokenResponse}로 반환한다.
     *
     * @param userId 회원 ID
     * @param email  회원 이메일
     * @param role   회원 권한
     * @return 발급된 토큰 정보
     */
    public TokenResponse receiveToken(Long userId, String email, Role role) {
        Date accessExpiryDate = calculateExpiryDate(jwtProperties.getAccessTokenExpiration());
        Date refreshExpiryDate = calculateExpiryDate(jwtProperties.getRefreshTokenExpiration());

        String accessToken = createToken(userId, email, role, ACCESS_TOKEN, accessExpiryDate);
        String refreshToken = createToken(userId, email, role, REFRESH_TOKEN, refreshExpiryDate);

        return new TokenResponse(
                accessToken,
                toLocalDateTime(accessExpiryDate),
                refreshToken,
                toLocalDateTime(refreshExpiryDate),
                role
        );
    }

    /**
     * Access Token만 단독으로 생성한다.
     *
     * @param userId 회원 ID
     * @param email  회원 이메일
     * @param role   회원 권한
     * @return 생성된 Access Token 문자열
     */
    public String generateAccessToken(Long userId, String email, Role role) {
        Date expiryDate = calculateExpiryDate(jwtProperties.getAccessTokenExpiration());
        return createToken(userId, email, role, ACCESS_TOKEN, expiryDate);
    }

    /**
     * Refresh Token만 단독으로 생성한다.
     *
     * @param userId 회원 ID
     * @param email  회원 이메일
     * @param role   회원 권한
     * @return 생성된 Refresh Token 문자열
     */
    public String generateRefreshToken(Long userId, String email, Role role) {
        Date expiryDate = calculateExpiryDate(jwtProperties.getRefreshTokenExpiration());
        return createToken(userId, email, role, REFRESH_TOKEN, expiryDate);
    }

    /**
     * JWT를 생성한다.
     *
     * @param userId     회원 ID
     * @param email      회원 이메일 (subject)
     * @param role       회원 권한
     * @param type       토큰 타입 ({@code "accessToken"} 또는 {@code "refreshToken"})
     * @param expiryDate 만료 시각
     * @return 서명된 JWT 문자열
     */
    private String createToken(Long userId, String email, Role role, String type, Date expiryDate) {
        return Jwts.builder()
                .setSubject(email)
                .claim(USER_ID, userId)
                .claim(ROLE, role.name())
                .claim(TOKEN_TYPE, type)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(secretKey, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 해당 토큰이 Access Token인지 검증한다.
     *
     * @param token 검사할 JWT
     * @return Access Token이면 {@code true}
     */
    public boolean isAccessTokenValid(String token) {
        return ACCESS_TOKEN.equals(getClaims(token).get(TOKEN_TYPE, String.class));
    }

    /**
     * 해당 토큰이 Refresh Token인지 검증한다.
     *
     * @param token 검사할 JWT
     * @return Refresh Token이면 {@code true}
     */
    public boolean isRefreshTokenValid(String token) {
        return REFRESH_TOKEN.equals(getClaims(token).get(TOKEN_TYPE, String.class));
    }

    /**
     * JWT 클레임을 파싱하여 반환한다.
     * <p>
     * 만료된 토큰의 경우에도 클레임을 반환한다({@link ExpiredJwtException} 처리).
     * </p>
     *
     * @param token 파싱할 JWT
     * @return 파싱된 클레임
     */
    public Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 토큰에서 회원 이메일을 추출한다.
     *
     * @param token JWT
     * @return 회원 이메일
     */
    public String getUserEmail(String token) {
        return getClaims(token).getSubject();
    }

    /**
     * 토큰에서 회원 ID를 추출한다.
     *
     * @param token JWT
     * @return 회원 ID
     */
    public Long getUserId(String token) {
        return getClaims(token).get(USER_ID, Long.class);
    }

    /**
     * 토큰에서 회원 권한을 추출한다.
     *
     * @param token JWT
     * @return 회원 권한
     */
    public Role getRole(String token) {
        String role = getClaims(token).get(ROLE, String.class);
        return Role.valueOf(role);
    }

    /**
     * 토큰의 서명 및 구조 유효성을 검증한다.
     * <p>
     * 만료 여부는 이 메서드에서 함께 검증된다.
     * </p>
     *
     * @param token 검증할 JWT
     * @return 유효한 토큰이면 {@code true}, 그렇지 않으면 {@code false}
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    /**
     * HTTP 요청의 {@code Authorization} 헤더에서 Bearer 토큰을 추출한다.
     *
     * @param request HTTP 요청
     * @return 추출된 토큰 문자열, 없으면 {@code null}
     */
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    /**
     * Refresh Token 문자열에서 {@code "Bearer "} 접두사를 제거한다.
     *
     * @param refreshToken 파싱할 Refresh Token
     * @return 접두사가 제거된 토큰 문자열
     */
    public String parseRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(BEARER_PREFIX)) {
            return refreshToken.substring(BEARER_PREFIX.length());
        }
        return refreshToken;
    }

    /**
     * {@link Date}를 시스템 기본 시간대 기준의 {@link LocalDateTime}으로 변환한다.
     *
     * @param date 변환할 날짜
     * @return 변환된 LocalDateTime
     */
    private LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    /**
     * 현재 시각으로부터 지정된 초(seconds)만큼 이후의 만료 시각을 계산한다.
     *
     * @param validitySeconds 유효 시간 (초)
     * @return 만료 시각
     */
    private Date calculateExpiryDate(long validitySeconds) {
        return new Date(System.currentTimeMillis() + validitySeconds * 1000L);
    }
}
