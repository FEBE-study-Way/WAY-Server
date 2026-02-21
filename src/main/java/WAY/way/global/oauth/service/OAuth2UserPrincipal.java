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

@Getter
public class OAuth2UserPrincipal implements OAuth2User {

    private final OAuth2UserInfo oAuth2UserInfo;
    private final MemberEntity member;
    private final Map<String, Object> attributes;

    public OAuth2UserPrincipal(OAuth2UserInfo oAuth2UserInfo, MemberEntity member, Map<String, Object> attributes) {
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.member = member;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return Map.of();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
    }

    @Override
    public String getName() {
        return member.getId().toString();
    }
}
