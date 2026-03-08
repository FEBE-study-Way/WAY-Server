package WAY.way.domain.auth.entity;

import WAY.way.domain.auth.presentation.data.ApproveType;
import WAY.way.domain.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Table(name = "teacher_signup_request")
public class TeacherSignUpRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_signup_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher")
    private MemberEntity teacher;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Enumerated(EnumType.STRING)
    @Column(name = "approve_type", nullable = false)
    private ApproveType approveType = ApproveType.PENDING;

    @Builder
    public TeacherSignUpRequestEntity(MemberEntity teacher, String roomName) {
        this.teacher = teacher;
        this.roomName = roomName;
    }

    public void approveRequest(ApproveType approveType) {
        this.approveType = approveType;
    }
}
