package WAY.way.domain.auth.service;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.global.oauth.data.MemberCommand;

public interface MemberRegistrationService {
    MemberEntity findOrRegister(MemberCommand command);
}
