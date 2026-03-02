package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 유효하지 않은 Refresh Token으로 재발급 요청 시 발생하는 예외.
 *
 * @see ErrorCode#INVALID_REFRESH_TOKEN
 */
public class InvalidRefreshTokenException extends GlobalException {
    public InvalidRefreshTokenException() {
        super(ErrorCode.INVALID_REFRESH_TOKEN);
    }
}
