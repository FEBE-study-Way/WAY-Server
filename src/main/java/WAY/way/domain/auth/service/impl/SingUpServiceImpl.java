package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.exception.MemberAlreadyExistsException;
import WAY.way.domain.auth.presentation.data.request.SignUpRequest;
import WAY.way.domain.auth.presentation.data.response.TokenResponse;
import WAY.way.domain.auth.service.RefreshTokenService;
import WAY.way.domain.auth.service.SignUpService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link SignUpService} 구현체.
 * <p>
 * OAuth 로그인 후 미인증({@code UNAUTHENTICATED}) 상태인 회원의 이름과 학번을 등록하여
 * {@code USER} 역할로 승격하고 새 JWT 토큰을 발급한다.
 * </p>
 */
@Service
@RequiredArgsConstructor
public class SingUpServiceImpl implements SignUpService {

    private final MemberUtil memberUtil;
    private final RefreshTokenService refreshTokenService;

    /**
     * {@inheritDoc}
     *
     * @throws MemberAlreadyExistsException 이미 {@code USER} 역할인 회원이 재가입을 시도할 경우
     */
    @Override
    @Transactional
    public TokenResponse execute(SignUpRequest request) {
        MemberEntity member = memberUtil.getCurrentMember();

        if(member.getRole()== Role.USER){
            throw new MemberAlreadyExistsException();
        }
        member.completeSignUp(request.name(),request.studentNumber());

        return refreshTokenService.execute(member.getId(),member.getEmail(),member.getRole());
    }
}
