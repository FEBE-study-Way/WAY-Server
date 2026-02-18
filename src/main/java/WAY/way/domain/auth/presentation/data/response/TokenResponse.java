package WAY.way.domain.auth.presentation.data.response;

import WAY.way.domain.member.presentation.data.Role;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

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
