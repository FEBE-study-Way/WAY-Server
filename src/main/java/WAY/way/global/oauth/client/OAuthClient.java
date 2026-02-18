package WAY.way.global.oauth.client;

import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.config.OAuthProviderConfig;
import WAY.way.global.oauth.data.ProviderProperties;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthClient {

    private final RestClient restClient;
    private final OAuthProviderConfig providerConfig;

    private static final ParameterizedTypeReference<Map<String, Object>> MAP_TYPE =
            new ParameterizedTypeReference<>() {};

    public String getAccessToken(OAuthType type, String code) {
        ProviderProperties provider = providerConfig.get(type);

        log.info("[OAuthClient] Provider: {}, Redirect URI: {}", type, provider.redirectUri());

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("redirect_uri", provider.redirectUri());
        formData.add("client_id", provider.clientId());
        formData.add("client_secret", provider.clientSecret());

        try {
            Map<String, Object> response = restClient.post()
                    .uri(provider.tokenUri())
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(MAP_TYPE);

            return Optional.ofNullable(response)
                    .map(r -> (String) r.get("access_token"))
                    .filter(token -> token != null && !token.isBlank())
                    .orElseThrow(() -> {
                        log.error("[OAuthClient] Access Token is null or empty in response");
                        return new OAuth2AuthenticationProcessingException();
                    });

        } catch (RestClientResponseException e) {
            log.error("[OAuthClient] Token exchange failed - Status: {}, Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new OAuth2AuthenticationProcessingException();
        }
    }

    public Map<String, Object> getUserAttributes(OAuthType type, String accessToken) {
        ProviderProperties props = providerConfig.get(type);

        try {
            Map<String, Object> attributes = restClient.get()
                    .uri(props.userInfoUri())
                    .headers(h -> h.setBearerAuth(accessToken))
                    .retrieve()
                    .body(MAP_TYPE);

            return Optional.ofNullable(attributes)
                    .orElseThrow(() -> {
                        log.error("[OAuthClient] User attributes are null");
                        return new OAuth2AuthenticationProcessingException();
                    });

        } catch (RestClientResponseException e) {
            log.error("[OAuthClient] User info fetch failed - Status: {}, Body: {}",
                    e.getStatusCode(), e.getResponseBodyAsString());
            throw new OAuth2AuthenticationProcessingException();
        }
    }
}