package WAY.way.global.oauth.data;

import WAY.way.global.oauth.dto.OAuthUserResponse;

/**
 * OAuth 제공자별 사용자 정보 추출 인터페이스.
 * <p>
 * 각 OAuth 제공자(Google 등)의 속성 맵을 파싱하여 공통 응답 형태({@link OAuthUserResponse})로 변환한다.
 * </p>
 */
public interface OAuth2UserInfo {

    /**
     * 제공자 속성을 {@link OAuthUserResponse}로 변환한다.
     *
     * @return 변환된 사용자 응답 객체
     */
    OAuthUserResponse toResponse();
}
