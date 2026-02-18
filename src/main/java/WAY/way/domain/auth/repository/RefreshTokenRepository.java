package WAY.way.domain.auth.repository;

import WAY.way.domain.auth.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository("refreshTokenRedisRepository")
public interface RefreshTokenRepository extends CrudRepository<RefreshToken, Long> {
}
