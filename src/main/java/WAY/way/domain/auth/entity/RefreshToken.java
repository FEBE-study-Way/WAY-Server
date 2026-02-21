package WAY.way.domain.auth.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@RedisHash(value = "refresh_token")
public class RefreshToken {

    @Id
    private Long id;

    private String token;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long expiresIn;

    public void updateToken(String token) {
        this.token = token;
    }

}
