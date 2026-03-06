package WAY.way.domain.auth.repository;

import WAY.way.domain.auth.entity.TeacherSignUpRequestEntity;
import WAY.way.domain.auth.presentation.data.ApproveType;
import WAY.way.domain.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherSignUpRequestRepository extends JpaRepository<TeacherSignUpRequestEntity, Long> {
    boolean existsByTeacherAndApproveType(MemberEntity teacher, ApproveType approveType);
}
