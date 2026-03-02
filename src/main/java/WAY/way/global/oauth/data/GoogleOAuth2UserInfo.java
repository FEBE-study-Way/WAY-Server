package WAY.way.global.oauth.data;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.dto.OAuthUserResponse;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;

import java.util.Map;

/**
 * Google OAuth 사용자 정보를 파싱하는 구현체.
 * <p>
 * Google UserInfo 엔드포인트 응답에서 {@code sub}(고유 ID)와 {@code email}을 추출한다.
 * </p>
 *
 * @param attributes Google UserInfo API로부터 받은 속성 맵
 */
public record GoogleOAuth2UserInfo(Map<String, Object> attributes) implements OAuth2UserInfo {

    private static final String ID = "sub";
    private static final String EMAIL = "email";

    /**
     * {@inheritDoc}
     *
     * @throws OAuth2AuthenticationProcessingException 필수 속성(sub, email)이 없을 경우
     */
    @Override
    public OAuthUserResponse toResponse() {
        return new OAuthUserResponse(
                getRequiredAttribute(ID),
                OAuthType.GOOGLE,
                getRequiredAttribute(EMAIL),
                Role.USER
        );
    }

    /**
     * 필수 속성을 가져온다. 값이 없으면 예외를 발생시킨다.
     *
     * @param key 속성 키
     * @return 속성 값 문자열
     * @throws OAuth2AuthenticationProcessingException 속성이 존재하지 않을 경우
     */
    private String getRequiredAttribute(String key) {
        Object value = attributes.get(key);
        if (value == null) {
            throw new OAuth2AuthenticationProcessingException();
        }
        return value.toString();
    }

    /**
     * 선택적 속성을 가져온다. 값이 없으면 {@code null}을 반환한다.
     *
     * @param key 속성 키
     * @return 속성 값 문자열 또는 {@code null}
     */
    private String getOptionalAttribute(String key) {
        Object value = attributes.get(key);
        return value != null ? value.toString() : null;
    }
}
