package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class InvalidEmailDomainException extends GlobalException {
    public InvalidEmailDomainException() {
        super(ErrorCode.INVALID_EMAIL_DOMAIN);
    }
}
