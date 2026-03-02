package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 인증되지 않은 사용자가 보호된 리소스에 접근하려 할 때 발생하는 예외.
 *
 * @see ErrorCode#USER_UNAUTHORIZED
 */
public class UnauthorizedUserException extends GlobalException {
    public UnauthorizedUserException() {
        super(ErrorCode.USER_UNAUTHORIZED);
    }
}
