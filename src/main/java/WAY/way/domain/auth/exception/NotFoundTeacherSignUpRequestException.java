package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class NotFoundTeacherSignUpRequestException extends GlobalException {
    public NotFoundTeacherSignUpRequestException() {
        super(ErrorCode.NOT_FOUND_TEACHER_SIGNUP_REQUEST);
    }
}
