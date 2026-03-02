package WAY.way.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리 핸들러.
 * <p>
 * {@link GlobalException} 및 처리되지 않은 {@link Exception}을 캐치하여
 * {@link ErrorResponse} 형태의 JSON 응답으로 변환한다.
 * </p>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 예외({@link GlobalException})를 처리한다.
     *
     * @param e 발생한 GlobalException
     * @return {@link ErrorCode}에 정의된 상태 코드와 메시지를 담은 응답
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(GlobalException e) {
        ErrorResponse response = ErrorResponse.builder()
                .status(e.getErrorCode().getStatus())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(response.getStatus()).body(response);
    }

    /**
     * 처리되지 않은 예외를 500 Internal Server Error로 반환한다.
     *
     * @param e 발생한 예외
     * @return 500 상태 코드와 일반 오류 메시지를 담은 응답
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        log.error("처리되지 않은 예외", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(500, "서버 내부 오류가 발생했습니다."));
    }
}   