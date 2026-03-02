package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.entity.RefreshToken;
import WAY.way.domain.auth.exception.InvalidRefreshTokenException;
import WAY.way.domain.auth.exception.RefreshTokenNotFoundException;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.repository.RefreshTokenRepository;
import WAY.way.domain.auth.service.RefreshTokenService;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.jwt.JwtProperties;
import WAY.way.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link RefreshTokenService} 구현체.
 * <p>
 * JWT 토큰 발급 및 Refresh Token을 Redis에 저장·갱신한다.
 * 재발급 시 기존 Refresh Token을 삭제하고 새 토큰 쌍을 저장한다(Token Rotation).
 * </p>
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    /**
     * {@inheritDoc}
     * <p>
     * 기존에 저장된 Refresh Token을 삭제하고 새 토큰 쌍을 발급 후 Redis에 저장한다.
     * </p>
     */
    @Override
    @Transactional
    public TokenResponse execute(Long userId, String email, Role role) {
        TokenResponse response = jwtProvider.receiveToken(userId, email, role);

        refreshTokenRepository.deleteById(userId);

        RefreshToken token = RefreshToken.builder()
                .userId(userId)
                .token(response.refreshToken())
                .expiresIn(jwtProperties.getRefreshTokenExpiration())
                .build();

        refreshTokenRepository.save(token);
        return response;
    }

    /**
     * {@inheritDoc}
     * <p>
     * 전달된 Refresh Token의 유효성 및 Redis 저장 값과의 일치 여부를 검증한 뒤
     * 새 토큰 쌍을 발급하고 기존 토큰을 삭제한다(Token Rotation).
     * </p>
     *
     * @throws InvalidRefreshTokenException  토큰이 유효하지 않거나 저장된 값과 다를 경우
     * @throws RefreshTokenNotFoundException Redis에 토큰이 존재하지 않을 경우
     */
    @Override
    @Transactional
    public TokenResponse reissueAccessToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        Long userId = jwtProvider.getUserId(refreshToken);
        String email = jwtProvider.getUserEmail(refreshToken);
        Role role = jwtProvider.getRole(refreshToken);

        RefreshToken stored = refreshTokenRepository.findById(userId)
                .orElseThrow(RefreshTokenNotFoundException::new);

        if (!stored.getToken().equals(refreshToken)) {
            throw new InvalidRefreshTokenException();
        }

        refreshTokenRepository.deleteById(userId);

        TokenResponse response = jwtProvider.receiveToken(userId, email, role);

        long expiresInSeconds = java.time.Duration.between(
                java.time.LocalDateTime.now(),
                response.refreshTokenExpiresAt()
        ).getSeconds();

        RefreshToken newToken = RefreshToken.builder()
                .userId(userId)
                .token(response.refreshToken())
                .expiresIn(expiresInSeconds)
                .build();

        refreshTokenRepository.save(newToken);

        return response;
    }
}