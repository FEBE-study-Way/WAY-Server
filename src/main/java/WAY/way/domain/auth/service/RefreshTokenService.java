package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.member.presentation.data.Role;

/**
 * Refresh Token 발급 및 재발급 서비스 인터페이스.
 */
public interface RefreshTokenService {

    /**
     * Access Token과 Refresh Token을 새로 발급하고 Redis에 저장한다.
     *
     * @param userId 회원 ID
     * @param email  회원 이메일
     * @param role   회원 권한
     * @return 발급된 토큰 정보
     */
    TokenResponse execute(Long userId, String email, Role role);

    /**
     * Refresh Token의 유효성을 검증하고 새로운 Access Token / Refresh Token을 재발급한다.
     *
     * @param refreshToken 클라이언트가 전달한 Refresh Token
     * @return 새로 발급된 토큰 정보
     */
    TokenResponse reissueAccessToken(String refreshToken);
}
