package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class InvalidRefreshTokenException extends GlobalException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
