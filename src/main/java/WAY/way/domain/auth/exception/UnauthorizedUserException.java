package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class UnauthorizedUserException extends GlobalException {
    public UnauthorizedUserException() {
        super(ErrorCode.USER_UNAUTHORIZED);
    }
}
