package WAY.way.domain.auth.presentation.data.request;

public record OAuthLoginRequest(
        String code,
        String provider
) {
}
