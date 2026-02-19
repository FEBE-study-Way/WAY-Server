package WAY.way.global.oauth.config;

import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.data.ProviderProperties;
import WAY.way.global.oauth.exception.UnsupportedOAuthProviderException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OAuthProviderConfig {

    private final Map<OAuthType, ProviderProperties> providers;

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

    public ProviderProperties get(OAuthType oAuthType){

        ProviderProperties providerProperties = providers.get(oAuthType);

        if(providerProperties == null){
            throw new UnsupportedOAuthProviderException();
        }
        return providerProperties;
    }

}
