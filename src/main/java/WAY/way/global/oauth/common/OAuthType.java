package WAY.way.global.oauth.common;

import WAY.way.global.oauth.exception.UnsupportedOAuthProviderException;

import java.util.Arrays;

/**
 * 지원하는 OAuth 제공자 유형 열거형.
 */
public enum OAuthType {
    /** Google OAuth 제공자. */
    GOOGLE;

    /**
     * 문자열 값으로 {@link OAuthType}을 찾는다 (대소문자 무시).
     *
     * @param value OAuth 제공자 이름 문자열 (예: "google")
     * @return 매칭되는 {@link OAuthType}
     * @throws UnsupportedOAuthProviderException 지원하지 않는 제공자 이름인 경우
     */
    public static OAuthType from(String value) {
            return Arrays.stream(OAuthType.values())
                    .filter(t -> t.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(UnsupportedOAuthProviderException::new);
    }
}
