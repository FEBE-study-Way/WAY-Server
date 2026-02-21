package WAY.way.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // 사용자 관련 에러
    USER_NOT_FOUND(404, "사용자를 찾을 수 없습니다."),
    USER_ALREADY_EXISTS(409, "이미 존재하는 사용자입니다."),
    USER_UNAUTHORIZED(403, "권한이 없습니다."),

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

    // JWT / Token 관련
    ACCESS_TOKEN_EXPIRED(401, "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(401, "유효하지 않은 Access Token입니다."),

    REFRESH_TOKEN_NOT_FOUND(404, "Refresh Token을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(401, "Refresh Token이 만료되었습니다."),
    INVALID_REFRESH_TOKEN(401, "유효하지 않은 Refresh Token입니다."),

    TOKEN_MALFORMED(401, "잘못된 형식의 토큰입니다."),
    TOKEN_SIGNATURE_INVALID(401, "토큰 서명이 유효하지 않습니다."),
    TOKEN_UNSUPPORTED(401, "지원하지 않는 토큰입니다.");


    private final int status;
    private final String message;
}
