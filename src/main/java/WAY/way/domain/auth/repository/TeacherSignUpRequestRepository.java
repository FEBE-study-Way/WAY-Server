package WAY.way.domain.auth.repository;

import WAY.way.domain.auth.entity.TeacherSignUpRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherSignUpRequestRepository extends JpaRepository<TeacherSignUpRequestEntity, Long> {
}
