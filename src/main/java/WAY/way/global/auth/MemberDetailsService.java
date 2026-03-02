package WAY.way.global.auth;

import WAY.way.domain.member.exception.NotFoundMemberException;
import WAY.way.domain.member.repository.MemberRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security {@link UserDetailsService} 구현체.
 * <p>
 * 이메일로 회원을 조회하여 {@link MemberDetails}를 반환한다.
 * JWT 필터에서 토큰 내 이메일을 기반으로 인증 객체를 생성할 때 사용된다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * 이메일로 회원을 조회하여 {@link MemberDetails}를 반환한다.
     *
     * @param email 조회할 회원 이메일
     * @return 해당 이메일의 회원 상세 정보
     * @throws NotFoundMemberException 해당 이메일의 회원이 존재하지 않을 경우
     */
    @Override
    public MemberDetails loadUserByUsername(@NotNull String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(MemberDetails::new)
                .orElseThrow(NotFoundMemberException::new);
    }
}
