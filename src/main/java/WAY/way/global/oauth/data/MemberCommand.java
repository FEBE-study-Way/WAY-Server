package WAY.way.global.oauth.data;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.global.oauth.common.OAuthType;
import WAY.way.global.oauth.dto.OAuthUserResponse;

/**
 * 회원 등록에 필요한 커맨드 객체.
 *
 * @param email      회원 이메일
 * @param provider   OAuth 제공자 유형
 * @param providerId OAuth 제공자에서 부여한 고유 ID
 */
public record MemberCommand(
        String email,
        OAuthType provider,
        String providerId
) {
    /**
     * {@link OAuthUserResponse}로부터 {@link MemberCommand}를 생성한다.
     *
     * @param userInfo OAuth 사용자 응답
     * @return 생성된 커맨드 객체
     */
    public static MemberCommand from(OAuthUserResponse userInfo) {
        return new MemberCommand(
                userInfo.email(),
                userInfo.type(),
                userInfo.providerId()
        );
    }
}
