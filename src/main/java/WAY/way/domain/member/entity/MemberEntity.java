package WAY.way.domain.member.entity;

import WAY.way.domain.member.presentation.data.Role;
import WAY.way.domain.reservation.entity.ReservationEntity;
import WAY.way.global.oauth.common.OAuthType;
import jakarta.persistence.*;
import lombok.*;


@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@Table(name = "members")
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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "provider")
    private OAuthType provider;

    @Column(nullable = false,name = "provider_id")
    private String providerId;

    @Column(nullable = true, unique = true ,name = "name")
    private String name;

    @Column(nullable = true, unique = true ,name = "student_number", length =4)
    private String studentNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "role")
    private Role role;
}
