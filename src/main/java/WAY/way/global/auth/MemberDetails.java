package WAY.way.global.auth;

import WAY.way.domain.member.entity.MemberEntity;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Spring Security {@link UserDetails} 구현체.
 * <p>
 * {@link MemberEntity}를 래핑하여 인증 컨텍스트에서 회원 정보를 제공한다.
 * 비밀번호 기반 인증을 사용하지 않으므로 {@link #getPassword()}는 {@code null}을 반환한다.
 * </p>
 */
@AllArgsConstructor
public class MemberDetails implements UserDetails {

    private final MemberEntity member;

    /**
     * 회원의 역할을 {@code ROLE_} 접두사가 붙은 권한으로 반환한다.
     *
     * @return 권한 목록
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + member.getRole().name())
        );
    }

    /**
     * 비밀번호 기반 인증을 사용하지 않으므로 {@code null}을 반환한다.
     *
     * @return {@code null}
     */
    @Override
    public @Nullable String getPassword() {
        return null;
    }

    /**
     * 사용자 이름으로 이메일을 반환한다.
     *
     * @return 회원 이메일
     */
    @Override
    public String getUsername() {
        return member.getEmail();
    }

    /** @return 항상 {@code true} (계정 만료 없음) */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /** @return 항상 {@code true} (계정 잠금 없음) */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /** @return 항상 {@code true} (자격 증명 만료 없음) */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /** @return 항상 {@code true} (계정 활성화 상태) */
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 회원 ID를 반환한다.
     *
     * @return 회원 PK
     */
    public Long getUserId() {
        return member.getId();
    }

    /**
     * 회원 이름을 반환한다.
     *
     * @return 회원 이름 (회원가입 전이면 {@code null})
     */
    public String getName() {
        return member.getName();
    }

    /**
     * 회원 학번을 반환한다.
     *
     * @return 학번 4자리 (회원가입 전이면 {@code null})
     */
    public String getStudentNumber() {
        return member.getStudentNumber();
    }

    /**
     * 래핑된 회원 엔티티를 반환한다.
     *
     * @return 회원 엔티티
     */
    public MemberEntity getMember() {
        return member;
    }
}
