package WAY.way.domain.auth.service.impl;

import WAY.way.domain.auth.service.MemberRegistrationService;
import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.repository.MemberRepository;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.data.MemberCommand;
import WAY.way.global.oauth.exception.OAuth2AuthenticationProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link MemberRegistrationService} 구현체.
 * <p>
 * 이메일로 회원을 조회하고, 없으면 신규 회원을 저장한다.
 * 동시 가입 요청에 의한 {@link DataIntegrityViolationException} 발생 시
 * 재조회하여 기존 회원을 반환한다.
 * </p>
 */
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberRegistrationServiceImpl implements MemberRegistrationService {

    private final MemberRepository memberRepository;

    /**
     * {@inheritDoc}
     * <p>
     * 데이터 무결성 위반(중복 가입 경쟁 조건) 시 기존 회원을 재조회하여 반환한다.
     * </p>
     */
    @Override
    public MemberEntity findOrRegister(MemberCommand command) {
        try{
            return memberRepository.findByEmail(command.email())
                    .orElseGet(()->register(command));
        } catch(DataIntegrityViolationException e){
            log.error("Data integrity violation during member registration: {}", e.getMessage());
            return memberRepository.findByEmail(command.email())
                    .orElseThrow(OAuth2AuthenticationProcessingException::new);
        }
    }

    /**
     * OAuth 정보로 신규 회원을 생성하고 저장한다.
     *
     * @param command OAuth에서 추출한 회원 정보
     * @return 저장된 회원 엔티티
     */
    private MemberEntity register(MemberCommand command) {
        return memberRepository.save(MemberEntity.builder()
                        .email(command.email())
                        .provider(command.provider())
                        .providerId(command.providerId())
                .build());
    }
}
