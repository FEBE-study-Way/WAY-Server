package WAY.way.global.oauth.config;

import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.data.ProviderProperties;
import WAY.way.global.oauth.exception.UnsupportedOAuthProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * OAuth 제공자별 설정 프로퍼티를 관리하는 컴포넌트.
 * <p>
 * {@code application.yml}의 {@code spring.security.oauth2.*} 설정을 읽어
 * {@link OAuthType}을 키로 하는 맵에 저장한다.
 * </p>
 */
@Component
public class OAuthProviderConfig {

    private final Map<OAuthType, ProviderProperties> providers;

    /**
     * Google OAuth 설정 프로퍼티로 {@link OAuthProviderConfig}를 초기화한다.
     *
     * @param googleClientId     Google 클라이언트 ID
     * @param googleClientSecret Google 클라이언트 시크릿
     * @param googleTokenUri     Google Access Token 교환 URI
     * @param googleUserInfoUri  Google 사용자 정보 조회 URI
     * @param googleRedirectUri  Google OAuth 리다이렉트 URI
     */
    public OAuthProviderConfig(
            @Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId,
            @Value("${spring.security.oauth2.client.registration.google.client-secret}") String googleClientSecret,
            @Value("${spring.security.oauth2.client.provider.google.token-uri}") String googleTokenUri,
            @Value("${spring.security.oauth2.client.provider.google.user-info-uri}") String googleUserInfoUri,
            @Value("${spring.security.oauth2.client.registration.google.redirect-uri}") String googleRedirectUri
            ){

        providers = new HashMap<>();
        providers.put(OAuthType.GOOGLE, new ProviderProperties(
                googleClientId, googleClientSecret, googleTokenUri, googleUserInfoUri, googleRedirectUri
        ));
    }

    /**
     * 지정된 OAuth 제공자 유형의 설정 프로퍼티를 반환한다.
     *
     * @param oAuthType 조회할 OAuth 제공자 유형
     * @return 해당 제공자의 설정 프로퍼티
     * @throws UnsupportedOAuthProviderException 지원하지 않는 제공자인 경우
     */
    public ProviderProperties get(OAuthType oAuthType){

        ProviderProperties providerProperties = providers.get(oAuthType);

        if(providerProperties == null){
            throw new UnsupportedOAuthProviderException();
        }
        return providerProperties;
    }

}
