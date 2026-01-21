package WAY.way.domain.member.entity;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.reservation.entity.ReservationEntity;
import WAY.way.domain.room.entity.RoomEntity;
import jakarta.persistence.*;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = true)
    private ReservationEntity reservationId;

    @Column(nullable = false, unique = true ,name = "email")
    private String email;

    @Column(nullable = false, unique = true ,name = "name")
    private String name;

    @Column(nullable = false, unique = true ,name = "student_number", length =4)
    private String studentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;
}
