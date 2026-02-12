package WAY.way.domain.member.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

public class NotFoundMemberException extends GlobalException {
    public NotFoundMemberException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
