package WAY.way.domain.auth.service;

import WAY.way.domain.auth.presentation.data.request.SignUpRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;

public interface SignUpService {
    TokenResponse execute(SignUpRequest request);
}
