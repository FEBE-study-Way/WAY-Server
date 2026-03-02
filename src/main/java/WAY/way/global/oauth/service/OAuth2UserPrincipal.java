package WAY.way.global.oauth.service;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.global.oauth.data.OAuth2UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * OAuth2 인증 후 생성되는 사용자 주체(Principal) 클래스.
 * <p>
 * Spring Security의 {@link OAuth2User}를 구현하여 OAuth 사용자 정보와
 * 내부 회원 엔티티를 함께 보유한다.
 * </p>
 */
@Getter
public class OAuth2UserPrincipal implements OAuth2User {

    private final OAuth2UserInfo oAuth2UserInfo;
    private final MemberEntity member;
    private final Map<String, Object> attributes;

    /**
     * OAuth2UserPrincipal을 생성한다.
     *
     * @param oAuth2UserInfo OAuth 제공자로부터 추출한 사용자 정보
     * @param member         대응하는 내부 회원 엔티티
     * @param attributes     OAuth 제공자로부터 받은 원본 속성 맵
     */
    public OAuth2UserPrincipal(OAuth2UserInfo oAuth2UserInfo, MemberEntity member, Map<String, Object> attributes) {
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.member = member;
        this.attributes = attributes;
    }

    /**
     * OAuth 사용자 속성 맵을 반환한다.
     *
     * @return 빈 맵 (내부적으로 {@link #attributes} 필드 직접 접근 권장)
     */
    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    /**
     * 회원 역할을 권한으로 반환한다.
     *
     * @return 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    /**
     * 회원 ID를 문자열로 반환한다.
     *
     * @return 회원 PK 문자열
     */
    @Override
    public String getName() {
        return member.getId().toString();
    }
}
