package WAY.way.global.oauth.dto;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;


/**
 * OAuth 제공자로부터 추출한 사용자 정보 응답 DTO.
 *
 * @param providerId OAuth 제공자의 고유 사용자 ID
 * @param type       OAuth 제공자 유형
 * @param email      사용자 이메일
 * @param role       초기 회원 권한
 */
public record OAuthUserResponse(
        String providerId,
        OAuthType type,
        String email,
        Role role
) {
}
