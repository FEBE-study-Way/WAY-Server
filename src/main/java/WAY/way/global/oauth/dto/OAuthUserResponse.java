package WAY.way.global.oauth.dto;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;


public record OAuthUserResponse(
        String providerId,
        OAuthType type,
        String email,
        Role role
) {
}
