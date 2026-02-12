package WAY.way.global.auth;

import WAY.way.domain.member.exception.NotFoundMemberException;
import WAY.way.domain.member.repository.MemberRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public MemberDetails loadUserByUsername(@NotNull String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(MemberDetails::new)
                .orElseThrow(NotFoundMemberException::new);
    }
}
