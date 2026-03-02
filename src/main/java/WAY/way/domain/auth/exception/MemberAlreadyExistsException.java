package WAY.way.domain.auth.exception;

import WAY.way.global.exception.ErrorCode;
import WAY.way.global.exception.GlobalException;

/**
 * 이미 회원가입이 완료된 사용자가 재가입을 시도할 때 발생하는 예외.
 *
 * @see ErrorCode#MEMBER_ALREADY_EXISTS
 */
public class MemberAlreadyExistsException extends GlobalException {
    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }
}
