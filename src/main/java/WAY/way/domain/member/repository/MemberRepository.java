package WAY.way.domain.member.repository;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.member.presentation.data.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * 회원 엔티티에 대한 JPA 리포지토리.
 */
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    /**
     * 이메일로 회원을 조회한다.
     *
     * @param email 조회할 이메일
     * @return 해당 이메일을 가진 회원 (없으면 {@link java.util.Optional#empty()})
     */
    Optional<MemberEntity> findByEmail(String email);

    /**
     * 특정 역할을 가진 모든 회원을 조회한다.
     *
     * @param role 조회할 역할
     * @return 해당 역할을 가진 회원 목록
     */
    List<MemberEntity> findAllByRole(Role role);
}
