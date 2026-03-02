package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * Redis에 저장된 Refresh Token을 찾을 수 없을 때 발생하는 예외.
 *
 * @see ErrorCode#REFRESH_TOKEN_NOT_FOUND
 */
public class RefreshTokenNotFoundException extends GlobalException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
    }
}
