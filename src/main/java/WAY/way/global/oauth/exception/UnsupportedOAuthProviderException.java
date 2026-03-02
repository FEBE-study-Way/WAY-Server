package WAY.way.global.oauth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 지원하지 않는 OAuth 제공자로 로그인 시도 시 발생하는 예외.
 *
 * @see ErrorCode#OAUTH_PROVIDER_NOT_SUPPORTED
 */
public class UnsupportedOAuthProviderException extends GlobalException {
    public UnsupportedOAuthProviderException(){
        super(ErrorCode.OAUTH_PROVIDER_NOT_SUPPORTED);
    }
}
