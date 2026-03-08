package WAY.way.domain.auth.presentation.controller;

import WAY.way.domain.auth.presentation.data.request.ApproveTeacherSignUpRequest;
import WAY.way.domain.auth.presentation.data.request.OAuthLoginRequest;
import WAY.way.domain.auth.presentation.data.request.SignUpRequest;
import WAY.way.domain.auth.presentation.data.request.TeacherSignUpRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.service.*;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 인증(Auth) 관련 API 컨트롤러.
 * <p>
 * OAuth 로그인, Access Token 재발급, 회원가입(학번·이름 등록) 엔드포인트를 제공한다.
 * 기본 경로: {@code /api/v1/auth}
 * </p>
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final OAuthLoginService oAuthLoginService;
    private final RefreshTokenService refreshTokenService;
    private final SignUpService singUpService;
    private final TeacherSignUpRequestService teacherSignUpRequestService;
    private final ApproveTeacherSignUpRequestService approveTeacherSignUpRequestService;

    /**
     * Refresh Token으로 Access Token을 재발급한다.
     *
     * @param refreshToken 요청 헤더 {@code RefreshToken}에 담긴 Refresh Token
     * @param response     새 Access Token을 {@code Authorization} 헤더에 설정할 HTTP 응답
     * @return 새로 발급된 토큰 정보
     */
    @PatchMapping("/refresh")
    public ResponseEntity<TokenResponse> reissue(@RequestHeader("Refresh-Token") String refreshToken, HttpServletResponse response) {
        TokenResponse token =
                refreshTokenService.reissueAccessToken(refreshToken);
        response.setHeader(
                "Authorization",
                "Bearer " + token.accessToken()
        );
        return ResponseEntity.ok(token);
    }

    /**
     * OAuth 인가 코드로 로그인하고 JWT 토큰을 반환한다.
     *
     * @param request OAuth 제공자 종류와 인가 코드를 담은 요청 객체
     * @return 발급된 Access Token / Refresh Token 정보
     */
    @PostMapping("/oauth")
    public ResponseEntity<TokenResponse> login(@RequestBody OAuthLoginRequest request) {
        TokenResponse response = oAuthLoginService.execute(request);
        return ResponseEntity.ok(response);
    }

    /**
     * OAuth로 최초 로그인한 미인증 회원의 이름과 학번을 등록하여 회원가입을 완료한다.
     *
     * @param request 이름과 학번을 담은 요청 객체
     * @return 새로 발급된 토큰 정보 (역할이 USER로 갱신됨)
     */
    @PostMapping("/signup")
    public ResponseEntity<TokenResponse> signup(@Valid @RequestBody SignUpRequest request) {
        TokenResponse response = singUpService.execute(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/teacher/signup")
    public ResponseEntity<Void> teacherSignUpRequest(@Valid @RequestBody TeacherSignUpRequest request) {
        teacherSignUpRequestService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/teacher/signup/{teacherSignUpRequestId}")
    public ResponseEntity<Void> approveTeacherSignUpRequest(
            @PathVariable Long teacherSignUpRequestId,
            @Valid @RequestBody ApproveTeacherSignUpRequest request
    ) {
        approveTeacherSignUpRequestService.execute(teacherSignUpRequestId, request);
        return ResponseEntity.ok().build();
    }
}
