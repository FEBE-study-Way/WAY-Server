package WAY.way.global.oauth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * OAuth 인증 처리 중 오류가 발생했을 때 던져지는 예외.
 * <p>
 * 사용자 정보 추출 실패, 토큰 교환 실패 등 OAuth 흐름 전반의 오류에 사용된다.
 * </p>
 *
 * @see ErrorCode#OAUTH_PROCESSING_ERROR
 */
public class OAuth2AuthenticationProcessingException extends GlobalException {
    public OAuth2AuthenticationProcessingException() {
        super(ErrorCode.OAUTH_PROCESSING_ERROR);
    }
}
