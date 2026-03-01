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

@Component
@RequiredArgsConstructor
public class MemberUtil {

    private final MemberRepository memberRepository;

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