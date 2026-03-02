package WAY.way.domain.member.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 요청한 회원을 데이터베이스에서 찾을 수 없을 때 발생하는 예외.
 *
 * @see ErrorCode#USER_NOT_FOUND
 */
public class NotFoundMemberException extends GlobalException {
    public NotFoundMemberException(){
        super(ErrorCode.USER_NOT_FOUND);
    }
}
