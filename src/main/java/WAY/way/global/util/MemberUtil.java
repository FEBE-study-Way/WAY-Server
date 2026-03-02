package WAY.way.global.util;

import WAY.way.domain.auth.exception.UnauthorizedUserException;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.exception.NotFoundMemberException;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.global.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Spring Security 컨텍스트에서 현재 인증된 회원 정보를 조회하는 유틸리티 컴포넌트.
 */
@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    /**
     * 현재 인증된 회원 엔티티를 반환한다.
     * <p>
     * SecurityContextHolder에서 인증 정보를 꺼내 이메일로 회원을 조회한다.
     * </p>
     *
     * @return 현재 로그인된 회원 엔티티
     * @throws UnauthorizedUserException 인증 정보가 없거나 회원을 찾을 수 없는 경우
     */
    public MemberEntity getCurrentMember() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(principal -> principal instanceof MemberDetails)
                .map(principal -> (MemberDetails) principal)
                .map(MemberDetails::getUsername)
                .flatMap(memberRepository::findByEmail)
                .orElseThrow(UnauthorizedUserException::new);
    }
}