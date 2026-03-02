package WAY.way.domain.auth.repository;

import WAY.way.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


/**
 * Redis 기반 Refresh Token 저장소.
 * <p>
 * Spring Data Redis의 {@link CrudRepository}를 확장하며, 키 타입은 회원 ID({@code Long})이다.
 * </p>
 */
@Repository("refreshTokenRedisRepository")
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
