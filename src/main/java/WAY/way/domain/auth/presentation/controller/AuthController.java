package WAY.way.domain.auth.presentation.controller;

import WAY.way.domain.auth.presentation.data.request.OAuthLoginRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.service.OAuthLoginService;
import WAY.way.domain.auth.service.RefreshTokenService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService  oAuthLoginService;
    private final RefreshTokenService refreshTokenService;

    @PatchMapping("/refresh")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader("RefreshToken") String refreshToken, HttpServletResponse response) {
        TokenResponse token =
                refreshTokenService.reissueAccessToken(refreshToken);
        response.setHeader(
                "Authorization",
                "Bearer " + token.accessToken()
        );
        return ResponseEntity.ok(token);
    }


    @PostMapping("/oauth")
    public ResponseEntity<TokenResponse> login(@RequestBody OAuthLoginRequest request) {
        TokenResponse response = oAuthLoginService.execute(request);
        return ResponseEntity.ok(response);
    }
}
