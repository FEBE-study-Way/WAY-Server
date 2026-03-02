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

/**
 * {@link OAuthLoginService} 구현체.
 * <p>
 * OAuth 인가 코드로 Access Token을 교환하고, 사용자 정보를 조회하여 회원을 등록 또는 조회한 뒤
 * JWT 토큰을 발급한다. 허용 이메일 도메인은 {@code @gsm.hs.kr}로 제한된다.
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthLoginServiceImpl implements OAuthLoginService {

    private final OAuthClient oAuthClient;
    private final MemberRegistrationService memberRegistrationService;
    private final RefreshTokenService refreshTokenService;

    /** 허용된 이메일 도메인 (광주소프트웨어마이스터고). */
    private static final String ALLOWED_EMAIL_DOMAIN = "@gsm.hs.kr";

    /** {@inheritDoc} */
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

    /**
     * OAuth 제공자 속성 맵에서 사용자 정보를 추출한다.
     *
     * @param type       OAuth 제공자 유형
     * @param attributes OAuth 제공자로부터 받은 사용자 속성 맵
     * @return 추출된 사용자 응답 객체
     * @throws OAuth2AuthenticationProcessingException 사용자 정보 추출 실패 시
     */
    private OAuthUserResponse extractUserInfo(OAuthType type, Map<String, Object> attributes) {
        try {
            return OAuth2UserInfoFactory.getOAuth2UserInfo(type.name(), attributes).toResponse();
        } catch (Exception e) {
            log.error("[OAuthLogin] Failed to extract user info - provider: {}, error: {}",
                    type, e.getMessage(), e);
            throw new OAuth2AuthenticationProcessingException();
        }
    }

    /**
     * 이메일 도메인이 허용된 도메인인지 검증한다.
     *
     * @param email 검증할 이메일 주소
     * @throws InvalidEmailDomainException 허용되지 않는 도메인인 경우
     */
    private void validateEmailDomain(String email) {
        if (!email.endsWith(ALLOWED_EMAIL_DOMAIN)) {
            log.warn("[OAuthLogin] Invalid email domain: {}", email);
            throw new InvalidEmailDomainException();
        }
    }
}

