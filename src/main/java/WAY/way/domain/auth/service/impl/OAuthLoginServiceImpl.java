package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.exception.InvalidEmailDomainException;
import WAY.way.domain.auth.presentation.data.request.OAuthLoginRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.service.MemberRegistrationService;
import WAY.way.domain.auth.service.OAuthLoginService;
import WAY.way.domain.auth.service.RefreshTokenService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.global.oauth.client.OAuthClient;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.data.MemberCommand;
import WAY.way.global.oauth.dto.OAuthUserResponse;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;
import WAY.way.global.oauth.factory.OAuth2UserInfoFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginServiceImpl implements OAuthLoginService {

    private final OAuthClient oAuthClient;
    private final MemberRegistrationService memberRegistrationService;
    private final RefreshTokenService refreshTokenService;

    private static final String ALLOWED_EMAIL_DOMAIN = "@gsm.hs.kr";

    @Override
    public TokenResponse execute(OAuthLoginRequest request) {

        OAuthType type = OAuthType.from(request.provider());

        String accessToken = oAuthClient.getAccessToken(type, request.code());
        Map<String, Object> attributes = oAuthClient.getUserAttributes(type, accessToken);

        OAuthUserResponse userInfo = extractUserInfo(type, attributes);
        validateEmailDomain(userInfo.email());

        MemberEntity member = memberRegistrationService.findOrRegister(MemberCommand.from(userInfo));

        return refreshTokenService.execute(member.getId(),member.getEmail(), member.getRole());
    }

    private OAuthUserResponse extractUserInfo(OAuthType type, Map<String, Object> attributes) {
        try {
            return OAuth2UserInfoFactory.getOAuth2UserInfo(type.name(), attributes).toResponse();
        } catch (Exception e) {
            log.error("[OAuthLogin] Failed to extract user info - provider: {}, error: {}",
                    type, e.getMessage(), e);
            throw new OAuth2AuthenticationProcessingException();
        }
    }

    private void validateEmailDomain(String email) {
        if (!email.endsWith(ALLOWED_EMAIL_DOMAIN)) {
            log.warn("[OAuthLogin] Invalid email domain: {}", email);
            throw new InvalidEmailDomainException();
        }
    }
}

