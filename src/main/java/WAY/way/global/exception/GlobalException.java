package WAY.way.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 애플리케이션 전역 예외 기본 클래스.
 * <p>
 * 모든 비즈니스 예외는 이 클래스를 상속하며, {@link ErrorCode}를 통해
 * HTTP 상태 코드와 메시지를 전달한다.
 * </p>
 */
@Getter
@RequiredArgsConstructor
public class GlobalException extends RuntimeException {
    /** 이 예외에 대응하는 에러 코드. */
    private final ErrorCode errorCode;
}