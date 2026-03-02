package WAY.way.domain.reservation.entity;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.reservation.presentation.data.Status;
import WAY.way.domain.room.entity.RoomEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


/**
 * 공간 예약 정보를 나타내는 JPA 엔티티.
 * <p>
 * 담당 교사({@link #user})가 특정 공간({@link #room})을 특정 교시({@link #period})에 예약한다.
 * 예약 상태는 {@link Status}로 관리되며 거부 시 {@link #rejectReason}이 기록된다.
 * </p>
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "reservation")
public class ReservationEntity {

    /** 예약 고유 ID (PK, 자동 생성). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 예약 상태 ({@link Status#PENDING}, {@link Status#ACCEPTED}, {@link Status#REJECTED}). */
    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private Status status;

    /** 예약 교시. */
    @Column(name = "period" , nullable = false)
    private Integer period;

    /** 예약 신청 사유 (최대 512자, nullable). */
    @Column(name = "request_reason" , nullable = true, length = 512)
    private String requestReason;

    /** 예약 거부 사유 (최대 512자, nullable). */
    @Column(name = "reject_reason", nullable = true, length = 512)
    private String rejectReason;

    /** 예약 신청 담당 교사. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MemberEntity user;  // 실 선생님

    /** 예약된 공간. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    /** 예약에 참여하는 회원 목록. */
    @OneToMany(mappedBy = "reservationId")
    private List<MemberEntity> participants; // 예약한 사람들

}
