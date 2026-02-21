package WAY.way.global.oauth.data;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.dto.OAuthUserResponse;

public record MemberCommand(
        String email,
        OAuthType provider,
        String providerId,
        Role role
) {
    public static MemberCommand from(OAuthUserResponse userInfo) {
        return new MemberCommand(
                userInfo.email(),
                userInfo.type(),
                userInfo.providerId(),
                userInfo.role()
        );
    }
}
