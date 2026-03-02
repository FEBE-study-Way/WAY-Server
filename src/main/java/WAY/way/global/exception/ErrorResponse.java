package WAY.way.global.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


/**
 * API 에러 응답 DTO.
 * <p>
 * HTTP 상태 코드와 에러 메시지를 JSON으로 반환한다.
 * </p>
 *
 * @see GlobalExceptionHandler
 */
@Getter
@AllArgsConstructor
@Builder
public class ErrorResponse {
    /** HTTP 상태 코드. */
    private final int status;
    /** 에러 메시지. */
    private final String message;
}
