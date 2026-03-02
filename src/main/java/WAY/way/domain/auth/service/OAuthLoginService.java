package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.OAuthLoginRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;

/**
 * OAuth 로그인 처리 서비스 인터페이스.
 */
public interface OAuthLoginService {

    /**
     * OAuth 인가 코드로 사용자를 인증하고 JWT 토큰을 발급한다.
     *
     * @param request OAuth 제공자와 인가 코드를 담은 요청
     * @return 발급된 Access Token / Refresh Token 정보
     */
    TokenResponse execute(OAuthLoginRequest request);
}
