package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.service.MemberRegistrationService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.data.MemberCommand;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberRegistrationServiceImpl implements MemberRegistrationService {

    private final MemberRepository memberRepository;

    @Override
    public MemberEntity findOrRegister(MemberCommand command) {
        try{
            return memberRepository.findByEmail(command.email())
                    .orElseGet(()->register(command));
        } catch(DataIntegrityViolationException e){
            return memberRepository.findByEmail(command.email())
                    .orElseThrow(OAuth2AuthenticationProcessingException::new);
        }
    }

    private MemberEntity register(MemberCommand command) {
        return memberRepository.save(MemberEntity.builder()
                        .email(command.email())
                        .provider(OAuthType.GOOGLE)
                        .providerId(command.providerId())
                        .role(command.role())
                .build());
    }
}
