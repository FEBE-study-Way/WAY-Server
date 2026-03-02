package WAY.way.domain.auth.service;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.global.oauth.data.MemberCommand;

/**
 * OAuth 로그인 시 회원 조회 또는 신규 등록을 처리하는 서비스 인터페이스.
 */
public interface MemberRegistrationService {

    /**
     * 이메일로 회원을 조회하고, 존재하지 않으면 새로 등록한다.
     *
     * @param command OAuth에서 추출한 회원 정보 커맨드
     * @return 조회되거나 새로 저장된 회원 엔티티
     */
    MemberEntity findOrRegister(MemberCommand command);
}
