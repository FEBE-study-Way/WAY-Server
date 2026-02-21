package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class MemberAlreadyExistsException extends GlobalException {
    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
