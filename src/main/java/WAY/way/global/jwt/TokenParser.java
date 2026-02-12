package WAY.way.global.jwt;

import WAY.way.global.auth.MemberDetails;
import WAY.way.global.auth.MemberDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenParser {
    private final JwtProvider jwtProvider;

    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if(StringUtils.hasText(bearerToken) &&  bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }

    public String parseRefreshToken(String refreshToken) {
        if (StringUtils.hasText(refreshToken) && refreshToken.startsWith(BEARER_PREFIX)) {
            return refreshToken.substring(BEARER_PREFIX.length());
        }
        return refreshToken;
    }
    public Claims parseClaims(String token) {
        return jwtProvider.getClaims(token);
    }

    public Long getUserId(String token) {
        return jwtProvider.getUserId(token);
    }

    public String getUserEmail(String token) {
        return jwtProvider.getUserEmail(token);
    }
}
