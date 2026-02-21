package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.OAuthLoginRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;

public interface OAuthLoginService {
    TokenResponse execute(OAuthLoginRequest request);
}
