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

@Service
@RequiredArgsConstructor
public class SingUpServiceImpl implements SignUpService {

    private final MemberUtil memberUtil;
    private final RefreshTokenService refreshTokenService;

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
