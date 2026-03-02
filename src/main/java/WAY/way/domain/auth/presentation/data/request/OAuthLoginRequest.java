package WAY.way.domain.auth.presentation.data.request;

/**
 * OAuth 로그인 요청 DTO.
 *
 * @param code     OAuth 제공자로부터 받은 인가 코드
 * @param provider OAuth 제공자 이름 (예: "GOOGLE")
 */
public record OAuthLoginRequest(
        String code,
        String provider
) {
}
