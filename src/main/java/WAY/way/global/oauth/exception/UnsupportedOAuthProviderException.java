package WAY.way.global.oauth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class UnsupportedOAuthProviderException extends GlobalException {
    public UnsupportedOAuthProviderException(){
        super(ErrorCode.OAUTH_PROVIDER_NOT_SUPPORTED);
    }
}
