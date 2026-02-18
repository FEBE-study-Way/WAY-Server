package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.entity.RefreshToken;
import WAY.way.domain.auth.exception.InvalidRefreshToken;
import WAY.way.domain.auth.exception.RefreshTokenNotFound;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.repository.RefreshTokenRepository;
import WAY.way.domain.auth.service.RefreshTokenService;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.jwt.JwtProperties;
import WAY.way.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtProvider jwtProvider;
    private final JwtProperties jwtProperties;

    @Override
    @Transactional
    public TokenResponse execute(Long userId, String email, Role role) {
        TokenResponse response = jwtProvider.receiveToken(userId, email, role);

        refreshTokenRepository.deleteById(userId);

        RefreshToken token = RefreshToken.builder()
                .id(userId)
                .token(response.refreshToken())
                .expiresIn(jwtProperties.getRefreshTokenExpiration())
                .build();

        refreshTokenRepository.save(token);
        return response;
    }

    @Override
    @Transactional
    public TokenResponse reissueAccessToken(String refreshToken) {
        jwtProvider.validateToken(refreshToken);

        Long userId = jwtProvider.getUserId(refreshToken);
        String email = jwtProvider.getUserEmail(refreshToken);
        Role role = jwtProvider.getRole(refreshToken);



        RefreshToken stored = refreshTokenRepository.findById(userId)
                .orElseThrow(RefreshTokenNotFound::new);
        // 로그 추가: DB 값과 들어온 값 비교
        System.out.println("DB 토큰: " + stored.getToken());
        System.out.println("보낸 토큰: " + refreshToken);

        if(!stored.getToken().equals(refreshToken)) {
            throw new InvalidRefreshToken();
        }

        TokenResponse response = jwtProvider.receiveToken(userId, email, role);

        stored.updateToken(response.refreshToken());

        return response;
    }
}