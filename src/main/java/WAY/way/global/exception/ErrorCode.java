package WAY.way.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 사용자 관련 에러
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(409, "이미 존재하는 사용자입니다."),
    USER_UNAUTHORIZED(403, "권한이 없습니다.");

    private final int status;
    private final String message;
}
