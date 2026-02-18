package WAY.way.global.oauth.factory;

import WAY.way.global.oauth.data.GoogleOAuth2UserInfo;
import WAY.way.global.oauth.data.OAuth2UserInfo;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(
            Map<String, Object> attributes) {

        return new GoogleOAuth2UserInfo(attributes);
    }
}
