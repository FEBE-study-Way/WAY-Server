package WAY.way.global.oauth.factory;

import WAY.way.global.oauth.data.GoogleOAuth2UserInfo;
import WAY.way.global.oauth.data.OAuth2UserInfo;

import java.util.Map;

/**
 * OAuth 제공자 ID에 따라 적절한 {@link OAuth2UserInfo} 구현체를 생성하는 팩토리 클래스.
 */
public class OAuth2UserInfoFactory {

    /**
     * 제공자 ID에 맞는 {@link OAuth2UserInfo} 구현체를 반환한다.
     * <p>
     * 현재는 Google만 지원한다.
     * </p>
     *
     * @param registrationId OAuth 제공자 등록 ID (예: "GOOGLE")
     * @param attributes     제공자로부터 받은 사용자 속성 맵
     * @return 제공자에 대응하는 {@link OAuth2UserInfo} 구현체
     */
    public static OAuth2UserInfo getOAuth2UserInfo(
            String registrationId,
            Map<String, Object> attributes) {

        return new GoogleOAuth2UserInfo(attributes);
    }
}
