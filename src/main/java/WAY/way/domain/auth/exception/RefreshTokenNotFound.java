package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class RefreshTokenNotFound extends GlobalException {
    public RefreshTokenNotFound() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
