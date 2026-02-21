package WAY.way.global.oauth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class OAuth2AuthenticationProcessingException extends GlobalException {
    public OAuth2AuthenticationProcessingException() {
        super(ErrorCode.OAUTH_PROCESSING_ERROR);
    }
}
