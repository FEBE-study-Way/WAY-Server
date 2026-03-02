package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 허용되지 않는 이메일 도메인으로 로그인 시도 시 발생하는 예외.
 *
 * @see ErrorCode#INVALID_EMAIL_DOMAIN
 */
public class InvalidEmailDomainException extends GlobalException {
    public InvalidEmailDomainException() {
        super(ErrorCode.INVALID_EMAIL_DOMAIN);
    }
}
