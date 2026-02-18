package WAY.way.global.oauth.data;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.dto.OAuthUserResponse;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

public record GoogleOAuth2UserInfo(Map<String, Object> attributes) implements OAuth2UserInfo {

    private static final String ID = "sub";
    private static final String EMAIL = "email";

    @Override
    public OAuthUserResponse toResponse() {
        return new OAuthUserResponse(
                getRequiredAttribute(ID),
                OAuthType.GOOGLE,
                getRequiredAttribute(EMAIL),
                Role.USER
        );
    }

    private String getRequiredAttribute(String key) {
        Object value = attributes.get(key);
        if (value == null) {
            throw new OAuth2AuthenticationProcessingException();
        }
        return value.toString();
    }

    private String getOptionalAttribute(String key) {
        Object value = attributes.get(key);
        return value != null ? value.toString() : null;
    }
}
