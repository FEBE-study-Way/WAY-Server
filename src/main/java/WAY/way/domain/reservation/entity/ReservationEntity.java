package WAY.way.domain.reservation.entity;

import WAY.way.domain.member.entity.MemberEntity;
import WAY.way.domain.reservation.presentation.data.Status;
import WAY.way.domain.room.entity.RoomEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status" , nullable = false)
    private Status status;

    @Column(name = "period" , nullable = false)
    private Integer period;

    @Column(name = "request_reason" , nullable = true, length = 512)
    private String requestReason;

    @Column(name = "reject_reason", nullable = true, length = 512)
    private String rejectReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private MemberEntity user;  // 실 선생님

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @OneToMany(mappedBy = "reservationId")
    private List<MemberEntity> participants; // 예약한 사람들

}
