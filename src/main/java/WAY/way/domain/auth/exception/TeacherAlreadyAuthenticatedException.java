package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class TeacherAlreadyAuthenticatedException extends GlobalException {
    public TeacherAlreadyAuthenticatedException() {
        super(ErrorCode.TEACHER_ALREADY_AUTHENTICATED);
    }
}
