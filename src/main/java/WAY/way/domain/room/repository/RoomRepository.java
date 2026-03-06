package WAY.way.domain.room.repository;

import WAY.way.domain.room.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
    Optional<RoomEntity> findByName(String name);
    boolean existsByName(String name);
}
