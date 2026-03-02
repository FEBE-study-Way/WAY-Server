package WAY.way.global.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;

/**
 * JWT 설정 프로퍼티.
 * <p>
 * {@code application.yml}의 {@code jwt.*} 프로퍼티를 바인딩한다.
 * </p>
 */
@Getter
@AllArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    /** JWT 서명에 사용되는 비밀 키 문자열. */
    private final String secret;
    /** Access Token 유효 시간 (초 단위). */
    private final long accessTokenExpiration;
    /** Refresh Token 유효 시간 (초 단위). */
    private final long refreshTokenExpiration;
}
