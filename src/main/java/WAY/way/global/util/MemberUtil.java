package WAY.way.global.util;

import WAY.way.domain.auth.exception.UnauthorizedUserException;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.exception.NotFoundMemberException;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.global.auth.MemberDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

    public MemberEntity getCurrentMember() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();

        if (principal instanceof MemberDetails memberDetails) {
            String email = memberDetails.getUsername();
            return memberRepository.findByEmail(email)
                    .orElseThrow(NotFoundMemberException::new);
        }
        throw new UnauthorizedUserException();
    }
}
