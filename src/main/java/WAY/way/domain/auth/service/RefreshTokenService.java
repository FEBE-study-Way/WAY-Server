package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.member.presentation.data.Role;

public interface RefreshTokenService {
    TokenResponse execute(Long userId, String email, Role role);
    TokenResponse reissueAccessToken(String refreshToken);
}
