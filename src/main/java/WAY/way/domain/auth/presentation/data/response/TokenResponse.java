package WAY.way.domain.auth.presentation.data.response;

import WAY.way.domain.member.presentation.data.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * JWT 토큰 발급 응답 DTO.
 *
 * @param accessToken           발급된 Access Token
 * @param accessTokenExpiresAt  Access Token 만료 시각 (yyyy-MM-dd'T'HH:mm:ss)
 * @param refreshToken          발급된 Refresh Token
 * @param refreshTokenExpiresAt Refresh Token 만료 시각 (yyyy-MM-dd'T'HH:mm:ss)
 * @param role                  회원 권한
 */
public record TokenResponse(
        String accessToken,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime accessTokenExpiresAt,
        String refreshToken,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime refreshTokenExpiresAt,
        Role role
) {
}
