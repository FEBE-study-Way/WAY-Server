package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class InvalidRefreshToken extends GlobalException {
    public InvalidRefreshToken() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
