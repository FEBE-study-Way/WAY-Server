package WAY.way.domain.member.entity;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.reservation.entity.ReservationEntity;
import WAY.way.global.oauth.common.OAuthType;
import jakarta.persistence.*;
import lombok.*;


/**
 * 회원 정보를 나타내는 JPA 엔티티.
 * <p>
 * OAuth 제공자를 통해 가입되며, 초기 역할은 {@link Role#UNAUTHENTICATED}이다.
 * {@link #completeSignUp(String, String)} 호출 시 이름과 학번이 등록되고 역할이 {@link Role#USER}로 승격된다.
 * </p>
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "members")
public class MemberEntity {

    /** 회원 고유 ID (PK, 자동 생성). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 회원이 참여 중인 예약 (nullable). */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = true)
    private ReservationEntity reservationId;

    /** 회원 이메일 (unique, not null). */
    @Column(nullable = false, unique = true ,name = "email")
    private String email;

    /** OAuth 제공자 유형. */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "provider")
    private OAuthType provider;

    /** OAuth 제공자로부터 부여된 고유 식별자. */
    @Column(nullable = false,name = "provider_id")
    private String providerId;

    /** 회원 이름 (회원가입 완료 후 설정). */
    @Column(nullable = true ,name = "name")
    private String name;

    /** 학번 4자리 (회원가입 완료 후 설정, unique). */
    @Column(nullable = true, unique = true ,name = "student_number", length =4)
    private String studentNumber;

    /** 회원 권한 (기본값: {@link Role#UNAUTHENTICATED}). */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    @Builder.Default
    private Role role =Role.UNAUTHENTICATED;

    /**
     * 회원가입을 완료하여 이름과 학번을 저장하고 역할을 {@link Role#USER}로 변경한다.
     *
     * @param name          등록할 이름
     * @param studentNumber 등록할 학번
     */
    public void completeSignUp(String name, String studentNumber) {
        this.name = name;
        this.studentNumber = studentNumber;
        this.role = Role.USER;
    }

    /**
     * 학번을 갱신한다. 스케줄러에 의한 학년 진급 처리에 사용된다.
     *
     * @param studentNumber 새 학번
     */
    public void updateStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public void teacherSignUpRequest(String name) {
        this.name = name;
    }

    public void completeTeacherSignUp() {
        this.role = Role.ADMIN;
    }
}
