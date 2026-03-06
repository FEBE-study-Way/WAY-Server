package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class AlreadyPendingTeacherSignUpRequestException extends GlobalException {
    public AlreadyPendingTeacherSignUpRequestException() {
        super(ErrorCode.ALREADY_PENDING_TEACHER_SIGN_UP_REQUEST);
    }
}
