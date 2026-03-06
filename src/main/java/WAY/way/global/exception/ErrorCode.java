package WAY.way.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 애플리케이션 전역 에러 코드 열거형.
 * <p>
 * 각 상수는 HTTP 상태 코드({@link #status})와 사용자에게 노출되는 메시지({@link #message})를 포함한다.
 * {@link GlobalException}과 함께 사용된다.
 * </p>
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 사용자 관련 에러
    TEACHER_ALREADY_AUTHENTICATED(400, "이미 인증된 선생님입니다."),
    USER_UNAUTHORIZED(403, "권한이 없습니다."),
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    NOT_FOUND_TEACHER_SIGNUP_REQUEST(404, "승인 요청을 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(409, "이미 존재하는 사용자입니다."),
    ALREADY_PENDING_TEACHER_SIGN_UP_REQUEST(409, "이미 처리 대기 중인 교사 가입 요청이 존재합니다."),

    // OAuth , Auth
    OAUTH_PROVIDER_NOT_SUPPORTED(400, "지원하지 않는 OAuth 제공자입니다."),
    OAUTH_PROVIDER_MISMATCH(400, "다른 OAuth 제공자로 가입된 계정입니다."),
    OAUTH_AUTHENTICATION_FAILED(401, "OAuth 인증에 실패했습니다."),
    OAUTH_ACCESS_DENIED(403, "OAuth 인증이 거부되었습니다."),
    OAUTH_USER_INFO_NOT_FOUND(400, "OAuth 사용자 정보를 가져올 수 없습니다."),
    OAUTH_EMAIL_NOT_FOUND(400, "OAuth 이메일 정보를 제공받지 못했습니다."),
    OAUTH_PROVIDER_ID_NOT_FOUND(400, "OAuth provider ID를 가져올 수 없습니다."),
    OAUTH_PROCESSING_ERROR(500, "OAuth 처리 중 오류가 발생했습니다."),

    INVALID_EMAIL_DOMAIN(403,"허용되지 않는 이메일 도메인입니다."),
    MEMBER_ALREADY_EXISTS(409, "이미 가입된 사용자입니다."),

    // JWT / Token 관련
    ACCESS_TOKEN_EXPIRED(401, "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(401, "유효하지 않은 Access Token입니다."),

    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(401, "Refresh Token이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 Refresh Token입니다."),

    TOKEN_MALFORMED(401, "잘못된 형식의 토큰입니다."),
    TOKEN_SIGNATURE_INVALID(401, "토큰 서명이 유효하지 않습니다."),
    TOKEN_UNSUPPORTED(401, "지원하지 않는 토큰입니다."),

    // Room 관련
    NOT_FOUND_ROOM(404, "교실을 찾을 수 없습니다.");
    /** HTTP 응답 상태 코드. */
    private final int status;

    /** 클라이언트에 반환되는 에러 메시지. */
    private final String message;
}
