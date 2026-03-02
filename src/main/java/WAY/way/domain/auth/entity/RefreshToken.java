package WAY.way.domain.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

/**
 * Redis에 저장되는 Refresh Token 엔티티.
 * <p>
 * 키는 {@code userId}이며, {@code expiresIn} 초 후 자동으로 만료된다.
 * </p>
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token")
public class RefreshToken {

    /** 토큰 소유자의 회원 ID (Redis Key). */
    @Id
    private Long userId;

    /** JWT Refresh Token 문자열. */
    private String token;

    /** 토큰 만료 시간 (초 단위). Redis TTL에 사용된다. */
    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expiresIn;

}
