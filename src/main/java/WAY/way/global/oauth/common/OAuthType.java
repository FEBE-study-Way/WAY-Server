package WAY.way.global.oauth.common;

import WAY.way.global.oauth.exception.UnsupportedOAuthProviderException;

import java.util.Arrays;

public enum OAuthType {
    GOOGLE;

    public static OAuthType from(String value) {
            return Arrays.stream(OAuthType.values())
                    .filter(t -> t.name().equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(UnsupportedOAuthProviderException::new);
    }
}
